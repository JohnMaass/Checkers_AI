package com.checkers.game.ai;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.checkers.game.board.Board;
import com.checkers.game.board.piece.Color;
import com.checkers.game.board.actions.GamePieceAction;
import com.checkers.game.database.EarlyGameMoveDB;

public class MinMaxEvaluator {
	final int DEPTH;
	Color color;
	AbstractFunction func;
	ArrayList<Integer> bestActions;
	Random r = new Random();
	EarlyGameMoveDB moveDB;
	int count =0;
	double currentMax, tempMax;
	String startState;
	boolean useDB;
	//add function
	
	
	public MinMaxEvaluator(Color color,int depth, boolean didBlackGoFirst,boolean useDB){
		this.color=color;
		this.useDB=useDB;
		DEPTH=depth;
		func = new BasicFunction(color);
		bestActions = new ArrayList<>();
		try {
			moveDB=new EarlyGameMoveDB(didBlackGoFirst);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GamePieceAction getBestAction(Board board){
		GamePieceAction gamePieceAction = null;
		startState=board.boardNotation();
		count = 0;
		currentMax=0.0;
		int index = 0;
		String action = "";
		double value = -1000000.0, tempV = 0.0;
		// add copy constructor later
		if (board.numberOfTurns() < 16 && useDB) {
			try {
				action = moveDB.getBestMove(board);
				if (color == Color.BLACK) {
					if (!board.getBlackJumps().isEmpty()) {
						for (int i = 0; i < board.getBlackJumps().size(); i++) {
							if (board.getBlackJumps().get(i).getNotation().equals(action)) {
								gamePieceAction = board.getBlackJumps().get(i);
								break;
							}
						}
					} else {
						for (int i = 0; i < board.getBlackMoves().size(); i++) {
							if (board.getBlackMoves().get(i).getNotation().equals(action)) {
								gamePieceAction = board.getBlackMoves().get(i);
								break;
							}
						}
					}
				} else {
					if (!board.getRedJumps().isEmpty()) {
						for (int i = 0; i < board.getRedJumps().size(); i++) {
							if (board.getRedJumps().get(i).getNotation().equals(action)) {
								gamePieceAction = board.getRedJumps().get(i);
								break;
							}
						}
					} else {
						for (int i = 0; i < board.getRedMoves().size(); i++) {
							if (board.getRedMoves().get(i).getNotation().equals(action)) {
								gamePieceAction = board.getRedMoves().get(i);
								break;
							}
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (gamePieceAction == null) {
			if (color == Color.BLACK) {

				if (!board.getBlackJumps().isEmpty()) {
					for (int i = 0; i < board.getBlackJumps().size(); i++) {
						board.executeAction(board.getBlackJumps().get(i));
						tempV = findBest(board, DEPTH);
						board.undueLastAction();
						if (tempV > value) {
							currentMax=tempV;
							value = tempV;
							index = i;
							bestActions.clear();
							bestActions.add(i);
						} else if (tempV == value) {
							bestActions.add(i);
						}
					}
					gamePieceAction = board.getBlackJumps().get(bestActions.get(r.nextInt(bestActions.size())));
				} else {
					for (int i = 0; i < board.getBlackMoves().size(); i++) {
						board.executeAction(board.getBlackMoves().get(i));
						tempV = findBest(board, DEPTH);
						board.undueLastAction();
						if (tempV > value) {
							currentMax=tempV;
							value = tempV;
							index = i;
							bestActions.clear();
							bestActions.add(i);
						} else if (tempV == value) {
							bestActions.add(i);
						}
					}
					gamePieceAction = board.getBlackMoves().get(bestActions.get(r.nextInt(bestActions.size())));
				}
			} else {
				if (!board.getRedJumps().isEmpty()) {
					for (int i = 0; i < board.getRedJumps().size(); i++) {
						board.executeAction(board.getRedJumps().get(i));
						tempV = findBest(board, DEPTH);
						board.undueLastAction();
						if (tempV > value) {
							currentMax=tempV;
							value = tempV;
							index = i;
							bestActions.clear();
							bestActions.add(i);
						} else if (tempV == value) {
							bestActions.add(i);
						}
					}
					gamePieceAction = board.getRedJumps().get(bestActions.get(r.nextInt(bestActions.size())));
				} else {
					for (int i = 0; i < board.getRedMoves().size(); i++) {
						board.executeAction(board.getRedMoves().get(i));
						tempV = findBest(board, DEPTH);
						board.undueLastAction();
						if (tempV > value) {
							currentMax=tempV;
							value = tempV;
							index = i;
							bestActions.clear();
							bestActions.add(i);
						} else if (tempV == value) {
							bestActions.add(i);
						}
					}
					gamePieceAction = board.getRedMoves().get(bestActions.get(r.nextInt(bestActions.size())));
				}
			}
		}

		return gamePieceAction;
	}
	
	
	private double findBest(Board board, int depth){
		double value = -1000000.0, tempV=0.0;
		count++;
		if(depth==1)
			return func.findBoardValue(board);
		
		if((depth==2 || depth==4) && board.boardNotation().equals(startState)){
			return 0.0;
		}
		
		//prun node
		if(depth==2 && currentMax!=0.0){
			tempMax=func.findBoardValue(board);
			if(tempMax<currentMax)
				return tempMax;
		}
		
		if(board.isIsRedTurn()){
			if(!board.getRedJumps().isEmpty()){
//				System.out.println("REd Jumps "+board.getRedJumps().size());
				for(int i =0;i<board.getRedJumps().size();i++){
					board.executeAction(board.getRedJumps().get(i));
					tempV=findBest(board,depth-1);
					board.undueLastAction();
					value=(color==Color.RED?pickBest(value, tempV):pickWorst(value, tempV));
				}
			}
			else if(!board.getRedMoves().isEmpty()){
//				System.out.println("Red Moves "+board.getRedMoves().size());
				for(int i =0;i<board.getRedMoves().size();i++){
					board.executeAction(board.getRedMoves().get(i));
					tempV=findBest(board,depth-1);
					board.undueLastAction();
					value=(color==Color.RED?pickBest(value, tempV):pickWorst(value, tempV));
				}
			}
			else{
				//If it is reds turn and it has no moves or jumps AND this is its AI this is a losing leaf
				if(color==Color.RED)
					return depth * -10000;
				//This is a winning node if the AI is for black
				else
					return depth * 10000;
			}
		}
		else{
			if(!board.getBlackJumps().isEmpty()){
//				System.out.println("Black Jumps "+board.getBlackJumps().size());
				for(int i =0;i<board.getBlackJumps().size();i++){
					board.executeAction(board.getBlackJumps().get(i));
					tempV=findBest(board,depth-1);
					board.undueLastAction();
					value=(color==Color.BLACK?pickBest(value, tempV):pickWorst(value, tempV));
				}
			}
			else if(!board.getBlackMoves().isEmpty()){
//				System.out.println("Black Moves "+board.getBlackMoves().size());
				for(int i =0;i<board.getBlackMoves().size();i++){
					board.executeAction(board.getBlackMoves().get(i));
					tempV=findBest(board,depth-1);
					board.undueLastAction();
					value=(color==Color.BLACK?pickBest(value, tempV):pickWorst(value, tempV));
				}
			}
			else{
				//If it is blacks turn and it has no moves or jumps AND this is its AI this is a losing leaf
				if(color==Color.BLACK)
					return depth * -10000;
				//This is a winning node if the AI is for red
				else
					return depth * 10000;
			}
		}
		return value;
	}
	
	private double pickWorst(double current, double next){
		return ((next<current || current==-1000000.0)?next:current);
	}
	
	private double pickBest(double current, double next){
		return ((next>current || current==-1000000.0)?next:current);
	}
}
