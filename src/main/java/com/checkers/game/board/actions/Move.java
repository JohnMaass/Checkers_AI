package com.checkers.game.board.actions;

import com.checkers.game.board.piece.Color;
import com.checkers.game.board.piece.Type;

/**
 * The Move class extends GamePieceAction class and add a toString method.
 * 
 * @author maass
 */
public class Move extends GamePieceAction{
	
	public Move(Color color) {
		super(color);
	}

	public Move(GamePieceAction m){
		super(m);
	}
	
	public void setMove(int startX, int startY, int finalX, int finalY, Type id){
		setAction(startX, startY, finalX, finalY, id);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getColor()+" "+getPieceID()+" ("+getStartPosition().getX()+", "+getStartPosition().getY()+") to ("
				+getEndPosition().getX()+", "+getEndPosition().getY()+")";
	}
        
        @Override
        public String getKey(){
            return getNotation()+" "+toString();
        }
}
