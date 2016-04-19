package com.checkers.game.database;

import java.sql.SQLException;
import java.util.ArrayList;

import com.checkers.game.board.Board;
import com.checkers.game.board.BoardConstants;

public class EarlyGameMoveDB {

	private SQLiteConnection conn;
	private boolean didBlackGoFirst;
	
	public EarlyGameMoveDB(boolean didBlackGoFirst) throws ClassNotFoundException, SQLException{
		this.didBlackGoFirst=didBlackGoFirst;
		conn=new SQLiteConnection();
	}
	
        /**
         * This method returns move based on which one has the greater win plus
         * draw value.
         * @param states
         * @return 
         */
	public String bestMoveCalculator(ArrayList<String> states){
		String bestMove="";
		int bestValue=0;
		int bWins=0,rWins=0,draws=0;
		
		for(String s: states){
			bWins=Integer.parseInt(s.split("\t")[0]);
			rWins=Integer.parseInt(s.split("\t")[1]);
			draws=Integer.parseInt(s.split("\t")[2]);
			if(bestValue==0 || bestValue<(didBlackGoFirst?bWins:rWins)+draws){
				bestValue=(didBlackGoFirst?bWins:rWins)+draws;
				bestMove=s.split("\t")[4];
			}
		}
		
		return bestMove;
	}
	
        /**
         * This method gets the best move from the database if it exists.  Returns null
         * if it does not.
         * 
         * @param b Board state.
         * @return String representation of the best move.
         * @throws SQLException 
         */
	public String getBestMove(Board b) throws SQLException{
		String s = bestMoveCalculator(conn.getBoardStates(didBlackGoFirst?b.boardNotation():b.boardNotationInversion(), didBlackGoFirst));
		if(!didBlackGoFirst && !s.isEmpty())
			s=convertBackAction(s);
		return s;
	}
	
        /**
         * This method converts the notation back to the notation used in the board
         * if red went first.  The database only stores black board states so the
         * board has to be rotated 180 degrees.
         * 
         * @param s
         * @return 
         */
	private String convertBackAction(String s){
		return BoardConstants.convertNotationBack[Integer.parseInt(s.split("-")[0])]
				+"-"+BoardConstants.convertNotationBack[Integer.parseInt(s.split("-")[1])];
	}
}
