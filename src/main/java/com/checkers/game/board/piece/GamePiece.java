package com.checkers.game.board.piece;

import com.checkers.game.board.actions.GamePieceAction;
import com.checkers.game.board.actions.Jump;
import com.checkers.game.board.actions.Move;
import java.awt.Point;
import java.util.ArrayList;

import com.checkers.game.algorithms.jump.BlackPieceJumpAlgorithm;
import com.checkers.game.algorithms.move.BlackPieceMoveAlgorithm;
import com.checkers.game.algorithms.jump.JumpAlgorithm;
import com.checkers.game.algorithms.jump.KingJumpAlgorithm;
import com.checkers.game.algorithms.move.KingMoveAlgorithm;
import com.checkers.game.algorithms.move.MoveAlgorithm;
import com.checkers.game.algorithms.jump.RedPieceJumpAlgorithm;
import com.checkers.game.algorithms.move.RedPieceMoveAlgorithm;

/**
 * The GamePiece abstract class contains the basic outline of a game piece:
 * 1) Type and color
 * 2) Move and jump algorithms
 * 3) Current possible moves and jumps
 * 4) Getters and setters for different variables.
 * 
 * 
 * @author maass
 */
public abstract class GamePiece {
	private Type pieceID;
	private Color color;
	private Point position;
	
	private ArrayList<Move> moves;
	private ArrayList<Move> unusedMoves;
	private ArrayList<Jump> jumps;
	private ArrayList<Jump> unusedJumps;
	
	
	protected MoveAlgorithm menMoveAlgorithm;
	protected JumpAlgorithm menJumpAlgorithm;
	protected MoveAlgorithm kingMoveAlgorithm;
	protected JumpAlgorithm kingJumpAlgorithm;
	private JumpAlgorithm jumpAlgorithm;
	private MoveAlgorithm moveAlgorithm;
	
	public GamePiece(Color color, Type pieceID, int xPos, int yPos, GamePiece[][] board){
		this.color=color;
		this.pieceID=pieceID;
		
		//assign correct algorithms for men
		if(Color.BLACK==color){
			menMoveAlgorithm = new BlackPieceMoveAlgorithm(board);
			menJumpAlgorithm = new BlackPieceJumpAlgorithm(board);
		}
		else{
			menMoveAlgorithm = new RedPieceMoveAlgorithm(board);
			menJumpAlgorithm = new RedPieceJumpAlgorithm(board);
		}
		
		//create king movement algorithms
		kingMoveAlgorithm = new KingMoveAlgorithm(board);
		kingJumpAlgorithm = new KingJumpAlgorithm(board, color);
		
		//assign default movement algorithm depending on type.
		if(Type.MAN==pieceID){
			jumpAlgorithm=menJumpAlgorithm;
			moveAlgorithm=menMoveAlgorithm;
		}
		else{
			jumpAlgorithm=kingJumpAlgorithm;
			moveAlgorithm=kingMoveAlgorithm;
		}
		
		position=new Point(xPos,yPos);
		moves = new ArrayList<>(12);
		unusedMoves = new ArrayList<>(12);
		jumps = new ArrayList<>(12);
		unusedJumps = new ArrayList<>(12);
		
		for(int i = 0; i<12;i++){
			unusedJumps.add(new Jump(color));
			unusedMoves.add(new Move(color));
		}
	}
	


	private GamePiece() {
		// TODO Auto-generated constructor stub
	}

        /**
         * This method returns current type of the piece.
         * 
         * @return 
         */
	public Type getType() {
		return pieceID;
	}
	
        /**
         * This method returns an arraylist of type Move containing all of the
         * current moves avaliable to the piece.
         * 
         * @return 
         */
	public ArrayList<Move> getMoves() {
		return moves;
	}

        /**
         * This method returns an arraylist of type Jump containing all of the 
         * current jumps avaliable to the piece. 
         * 
         * @return 
         */
	public ArrayList<Jump> getJumps() {
		return jumps;
	}
	
        /**
         * This method returns the color of the object.
         * 
         * @return Color value of object.
         */
	public Color getColor() {
		return color;
	}

        /**
         * This method changes the pieces type to KING.
         */
	private void upgradeToKing(){
		pieceID=Type.KING;
		moveAlgorithm=kingMoveAlgorithm;
		jumpAlgorithm=kingJumpAlgorithm;
	}
	
        /**
         * This method changes the pieces type to MAN.
         * 
         */
	private void downgradeToMan(){
		pieceID=Type.MAN;
		moveAlgorithm=menMoveAlgorithm;
		jumpAlgorithm=menJumpAlgorithm;
	}
	
        /**
         * This method checks if the current action will upgrade the piece to a 
         * king.  If so the action is preform.
         * 
         * @param action 
         */
	public void checkIfShouldUpgradeToKing(GamePieceAction action){
		if(action.willPromote())
			upgradeToKing();
	}
	
        /**
         * This method checks if the piece should be downgraded to a man.  If so
         * the action is preformed.
         * 
         * @param action 
         */
	public void checkIfShouldDowngradeToMan(GamePieceAction action){
		if(action.willPromote())
			downgradeToMan();
	}
	
        /**
         * This method checks if the Color parameter passed into to it is the
         * oppisite color.
         * 
         * @param color Color to compare to.
         * @return True if the same, false otherwise.
         */
	public boolean isOppositecolor(Color color){
		return !(color==this.color);
	}
	
        /**
         * This method returns the current position of the [piece on the board.
         * 
         * @return Point containing the current position.
         */
	public Point getPosition(){
		return position;
	}
	
        /**
         * This method sets the current type of the piece.
         * 
         * @param t Type to set it to.
         */
	public void setType(Type t){
		pieceID=t;
	}
	
        /**
         * This method sets the current point of the piece.
         * 
         * @param xPos int x position
         * @param yPos int y position
         */
	public void setPosition(int xPos, int yPos){
		position.setLocation(xPos,yPos);
	}
	
        /**
         * this method resets the jumps arraylist.  All used jumps are saved
         * to be used again.
         */
	private void resetJumps(){
		unusedJumps.addAll(jumps);
		jumps.clear();
	}
	
        /**
         * This method resets the moves arraylist.  All used moves are saved
         * to be used again.
         * 
         */
	private void resetMoves(){
		unusedMoves.addAll(moves);
		moves.clear();
	}
	
        /**
         * This method sets the type of piece and ensures that it uses the 
         * correct move and jump algorithms.
         * 
         * @param t Type to set piece to.
         */
	public void setPiece(Type t){
		if(t==Type.KING){
			moveAlgorithm=kingMoveAlgorithm;
			jumpAlgorithm=kingJumpAlgorithm;
			pieceID=t;
		}
		else{
			moveAlgorithm=menMoveAlgorithm;
			jumpAlgorithm = menJumpAlgorithm;
			pieceID=t;
		}
	}
	
	/**
         * This method finds all moves for the piece and returns an arraylist
         * containing the moves.
         * 
         * @return  Arraylist that contains the moves.
         */
	public ArrayList<Move> findMoves(){
		resetMoves();
		moveAlgorithm.findMoves(position, moves, unusedMoves);
		return moves;
	}
	
        /**
         * This method finds all jumps for the piece and returns an arraylist
         * containing the jumps.
         * 
         * @return Arraylist that contains the jumps.
         */
	public ArrayList<Jump> findJumps(){
		resetJumps();
		jumpAlgorithm.findJumps(position, jumps, unusedJumps);
		return jumps;
	}
	
}
