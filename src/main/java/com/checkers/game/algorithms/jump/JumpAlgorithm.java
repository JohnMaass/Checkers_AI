package com.checkers.game.algorithms.jump;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import com.checkers.game.board.piece.Color;
import com.checkers.game.board.piece.GamePiece;
import com.checkers.game.board.actions.Jump;
import com.checkers.game.board.piece.Type;

public abstract class JumpAlgorithm {
	
	private GamePiece[][] board;
	private ArrayList<Point> unusedMenJumpedPositions;
	private ArrayList<Point> usedMenJumpedPositions;
	private ArrayList<Point> unusedMenPositions;
	private ArrayList<Point> usedMenPositions;
	private ArrayList<Point> menPositions;
	private ArrayList<Point> jumpedMenPositions;
	private ArrayList<Type> jumpedMenTypes;
	private Color color;
	
	public JumpAlgorithm(GamePiece[][] board, Color color){
		this.board=board;
		this.color=color;
		menPositions = new ArrayList<>();
		jumpedMenPositions = new ArrayList<>();
		jumpedMenTypes = new ArrayList<>();
		unusedMenJumpedPositions = new ArrayList<>(64);
		usedMenJumpedPositions = new ArrayList<>(64);
		unusedMenPositions = new ArrayList<>(64);
		usedMenPositions = new ArrayList<>(64);
		for(int i =0;i<64;i++){
			unusedMenJumpedPositions.add(new Point());
			unusedMenPositions.add(new Point());
		}
	}
	
	protected boolean findJumpsUpLeft(){
		if(currentYPos()>1 && currentXPos()>1){ 
			return findJumps(-2, -2);
		}
		return false;
	}
	
	protected boolean findJumpsUpRight(){
		if(currentYPos()<6 && currentXPos()>1 ){
			return findJumps(-2, 2);
		}
		return false;
		
	}
	
	protected boolean findJumpsDownLeft(){
		if(currentYPos()>1 && currentXPos()<6 ){
			return findJumps(2, -2);
		}
		return false;
		
	}
	
	protected boolean findJumpsDownRight(){
		if(currentYPos()<6 && currentXPos()<6 ){
			return findJumps(2, 2);
		}
		return false;
		
	}
	
	private boolean findJumps(int xInc, int yInc){
		//check if position to jump to is empty or is original(can happen for kings with multiple jumps)
		if((board[currentXPos()+xInc][currentYPos()+yInc]==null 
				|| board[currentXPos()+xInc][currentYPos()+yInc].getPosition().equals(menPositions.get(0)))){
			//checks to make sure there is a piece to jump, it is the correct color and it has not already been jumped
			if(board[currentXPos()+xInc/2][currentYPos()+yInc/2]!=null 
					&& board[currentXPos()+xInc/2][currentYPos()+yInc/2].isOppositecolor(color) 
					&& alreadyJumped(xInc, yInc)){
				
				jumpedMenTypes.add(board[currentXPos()+xInc/2][currentYPos()+yInc/2].getType());
				
				//set location of piece that is jumped and the new current location of jumping piece
				unusedMenJumpedPositions.get(0).setLocation(currentXPos()+xInc/2, currentYPos()+yInc/2);
				unusedMenPositions.get(0).setLocation(currentXPos()+xInc, currentYPos()+yInc);
				
				//add to arraylists tacking path
				jumpedMenPositions.add(unusedMenJumpedPositions.get(0));
				menPositions.add(unusedMenPositions.get(0));
				
				
				//add points to arraylist of already used point and remove from unused arraylist points
				usedMenJumpedPositions.add(unusedMenJumpedPositions.remove(0));
				usedMenPositions.add(unusedMenPositions.remove(0));
				return true;
			}
		}
		return false;
	}
	
	protected Jump setJump(Jump j, Type t){
		j.setJump(menPositions ,jumpedMenPositions, jumpedMenTypes,t);
		return j;
	}
	
	protected void removeLastJump(){
		if(!jumpedMenPositions.isEmpty()){
			jumpedMenTypes.remove(jumpedMenTypes.size()-1);
			menPositions.remove(menPositions.size()-1);
			jumpedMenPositions.remove(jumpedMenPositions.size()-1);
		}
	}
	
	private void resetPoints(){
		jumpedMenTypes.clear();
		menPositions.clear();
		jumpedMenPositions.clear();
		unusedMenJumpedPositions.addAll(usedMenJumpedPositions);
		usedMenJumpedPositions.clear();
		unusedMenPositions.addAll(usedMenPositions);
		usedMenPositions.clear();
	}
	
	private boolean alreadyJumped(int xInc, int yInc){
		return !jumpedMenPositions.contains(board[currentXPos()+xInc/2][currentYPos()+yInc/2].getPosition());
	}
	
	private void setStartPosition(Point start){
		unusedMenPositions.get(0).setLocation(start);
		menPositions.add(unusedMenPositions.get(0));
		usedMenPositions.add(unusedMenPositions.remove(0));
	}
	
	private int currentXPos(){
		return menPositions.get(menPositions.size()-1).x;
	}
	
	private int currentYPos(){
		return menPositions.get(menPositions.size()-1).y;
	}
	
	public void findJumps(Point start, ArrayList<Jump> jumps, ArrayList<Jump> unusedJumps){
		resetPoints();
		setStartPosition(start);
		findJumpsReccur(jumps, unusedJumps, false);
	}
	
	protected void setColor(Color color){
		this.color=color;
	}
	
	abstract protected void findJumpsReccur(ArrayList<Jump> jumps, ArrayList<Jump> unusedJumps, boolean notRoot);
	
}
