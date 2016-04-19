package com.checkers.game.board.actions;

import com.checkers.game.board.piece.Color;
import com.checkers.game.board.piece.Type;
import java.awt.Point;


/**
 * The GamePieceAction abstract class contains the basic outline for moves and
 * jumps:
 * 1) Start and end positions
 * 2) Type and color
 * 3) Getter and Setters
 * 
 * @author maass
 */
public abstract class GamePieceAction {
	private Point startPosition;
	private Point endPosition;
	private Type pieceID;
	private Color color;
	
	private int[][] boardNotation={
			{0,1,0,2,0,3,0,4},
			{5,0,6,0,7,0,8,0},
			{0,9,0,10,0,11,0,12},
			{13,0,14,0,15,0,16,0},
			{0,17,0,18,0,19,0,20},
			{21,0,22,0,23,0,24,0},
			{0,25,0,26,0,27,0,28},
			{29,0,30,0,31,0,32,0}};
	
	
	public GamePieceAction(Color color){
		startPosition=new Point();
		endPosition=new Point();
		pieceID=Type.MAN;
		this.color=color;
	}
	
	public GamePieceAction(GamePieceAction g){
		startPosition=new Point(g.startPosition);
		endPosition=new Point(g.endPosition);
		pieceID=g.pieceID;
		this.color=g.color;
	}
	
        /**
         * This method makes a shallow copy of the game piece action passed into 
         * it and returns it.
         * 
         * @param g GamePieceAction to copy.
         * @return The new game piece.
         */
	public GamePieceAction copyAction(GamePieceAction g){
		startPosition.setLocation(g.startPosition);
		endPosition.setLocation(g.endPosition);
		pieceID=g.pieceID;
		this.color=g.color;
		return this;
	}
	
        /**
         * This method sets the starting and final position of the action as well
         * as its type.
         * 
         * @param startX
         * @param startY
         * @param finalX
         * @param finalY
         * @param id 
         */
	public void setAction(int startX,int startY,int finalX, int finalY, Type id){
		startPosition.setLocation(startX, startY);;
		endPosition.setLocation(finalX,finalY);
		pieceID=id;
	}
	
	/**
         * This method returns the start position.
         * @return 
         */
	public Point getStartPosition() {
		return startPosition;
	}

        /**
         * This method returns the end postion.
         * @return 
         */
	public Point getEndPosition() {
		return endPosition;
	}

        /**
         * This method returns the piece Type.
         * @return 
         */
	public Type getPieceID() {
		return pieceID;
	}
	
        /**
         * This method returns the color of the piece.
         * @return 
         */
	public Color getColor(){
		return color;
	}
	
        /**
         * This method determines whether the action will promote the piece.
         * 
         * @return 
         */
	public boolean willPromote(){
		if((color==Color.BLACK && endPosition.x==7 && pieceID==Type.MAN) 
				|| (color==Color.RED && endPosition.x==0 && pieceID==Type.MAN)){
			return true;
		}
		return false;
	}
	
        /**
         * This method returns string notation for the action.
         * 
         * @return 
         */
	public String getNotation(){
		return boardNotation[startPosition.x][startPosition.y]+"-"+boardNotation[endPosition.x][endPosition.y];
	}
	
	public abstract String toString();
	
	public abstract String getKey();
}
