package com.checkers.game.ai;

import com.checkers.game.board.Board;
import com.checkers.game.board.piece.Color;
import com.checkers.game.board.piece.GamePiece;
import com.checkers.game.board.actions.Jump;
import com.checkers.game.board.actions.Move;
import com.checkers.game.board.piece.Type;

public class BasicFunction extends AbstractFunction{
	
	
	public BasicFunction(Color color){
		super(color);
	}
	
        @Override
	public double findBoardValue(Board b){
		double pieceWeightWeight=1,
                        manValueMoveWeight=0.25,
                        kingValuemoveWeight=0.05,
                        jumpValueWeight=0.4,
                        trappedPieceWeight=0.4;

		return pieceWeightWeight*pieceWeight(b)+
                        manValueMoveWeight*manValueMove(b)+
                        kingValuemoveWeight*kingValueMove(b)+
                        jumpValueWeight*jumpValue(b)+
                        trappedPieceWeight*trappedPiece(b);
	}
	
	
	@Override
	protected double jumpValue(Board b){
		redValue=0.0;
		blackValue=0.0;
//		System.out.println("Jumps");
		for(Jump j: b.getBlackJumps()){
			blackValue+=j.getTotalMenJumped()+(((1-j.getTotalMenJumped())*j.getTotalMenJumped())*0.25);
		}
		
		for(Jump j: b.getRedJumps()){
			redValue+=j.getTotalMenJumped()+(((1-j.getTotalMenJumped())*j.getTotalMenJumped())*0.25);
		}
		
		return color==Color.RED?redValue-blackValue:blackValue-redValue;
	}
	
	
	

	@Override
	protected double manValueMove(Board b) {
		redValue=0.0;
		blackValue=0.0;
//		System.out.println("man moves");
		for(Move m: b.getBlackMoves()){
			if(m.getPieceID()==Type.MAN){
				blackValue+=blackMoveValues[m.getEndPosition().x][m.getEndPosition().y];
			}
		}
		
		for(Move m: b.getRedMoves()){
			if(m.getPieceID()==Type.MAN){
				redValue+=redMoveValues[m.getEndPosition().x][m.getEndPosition().y];
			}
		}
		
		return color==Color.RED?redValue-blackValue:blackValue-redValue;
	}

	

	
	@Override
	protected double kingValueMove(Board b) {
		redValue=0.0;
		blackValue=0.0;
//		System.out.println("king moves");
//		System.out.println(b);
		if (b.getNumBlackKing() > 0) {
			generateMapBlack(b);
			for (Move m : b.getBlackMoves()) {
				if (m.getPieceID() == Type.KING) {
					blackValue += oppMapBlack[m.getEndPosition().x][m.getEndPosition().y];
				}
			}
		}
		if (b.getNumRedKing() > 0) {
			generateMapRed(b);
			for (Move m : b.getRedMoves()) {
				if (m.getPieceID() == Type.KING) {
					redValue += oppMapRed[m.getEndPosition().x][m.getEndPosition().y];
				}
			}
		}
		return color==Color.RED?redValue-blackValue:blackValue-redValue;
	}
	
	protected double trappedPiece(Board b){
		redValue=0.0;
		blackValue=0.0;
		
		for(GamePiece[] p: b.getBoard()){
			for(GamePiece piece: p){
				if(piece!=null){
					if(piece.getType()==Type.KING || piece.getColor()==Color.RED){
						//check bound
						if(piece.getPosition().x>1 && piece.getPosition().y>1 && piece.getPosition().y<6){
							//check left bound
							if(b.getBoard()[piece.getPosition().x-1][piece.getPosition().y-1]==null 
									&& b.getBoard()[piece.getPosition().x-2][piece.getPosition().y-2]!=null
									&& b.getBoard()[piece.getPosition().x-2][piece.getPosition().y-2].isOppositecolor(piece.getColor())){
								if(piece.getColor()==Color.RED){
									redValue++;
								}
								else{
									blackValue++;
								}
							}
							//check right bound
							if(b.getBoard()[piece.getPosition().x-1][piece.getPosition().y+1]==null 
									&& b.getBoard()[piece.getPosition().x-2][piece.getPosition().y+2]!=null
									&& b.getBoard()[piece.getPosition().x-2][piece.getPosition().y+2].isOppositecolor(piece.getColor())){
								if(piece.getColor()==Color.RED){
									redValue++;
								}
								else{
									blackValue++;
								}
							}
						}
					}
					if(piece.getType()==Type.KING || piece.getColor()==Color.BLACK){
						if(piece.getPosition().x<6 && piece.getPosition().y>1 && piece.getPosition().y<6){
							//check left bound
							if(b.getBoard()[piece.getPosition().x+1][piece.getPosition().y-1]==null 
									&& b.getBoard()[piece.getPosition().x+2][piece.getPosition().y-2]!=null
									&& b.getBoard()[piece.getPosition().x+2][piece.getPosition().y-2].isOppositecolor(piece.getColor())){
								if(piece.getColor()==Color.RED){
									redValue++;
								}
								else{
									blackValue++;
								}
							}
							//check right bound
							if(b.getBoard()[piece.getPosition().x+1][piece.getPosition().y+1]==null 
									&& b.getBoard()[piece.getPosition().x+2][piece.getPosition().y+2]!=null
									&& b.getBoard()[piece.getPosition().x+2][piece.getPosition().y+2].isOppositecolor(piece.getColor())){
								if(piece.getColor()==Color.RED){
									redValue++;
								}
								else{
									blackValue++;
								}
							}
						}
					}
				}
			}
		}
		
		return color==Color.BLACK?redValue-blackValue:blackValue-redValue;
	}
}
