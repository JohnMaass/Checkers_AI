package com.checkers.game.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The SQLiteConnection class creates a connection to an sqlite database file and
 * contains method to query the database.
 * @author maass
 */
public class SQLiteConnection {

	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	public SQLiteConnection() throws SQLException, ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:GameTable.db");
		stmt=conn.createStatement();
	}
    
        /**
         * This method is used to query the database for a given board state.
         * 
         * @param rep Representation of the board.
         * @param didBlackGoFirst Checks who went first.
         * @return An arraylist that contains the amount of black and red wins 
         * as well as draws.
         * @throws SQLException 
         */
	public ArrayList<String> getBoardStates(String rep, boolean didBlackGoFirst) throws SQLException{
		ArrayList<String> states=new ArrayList<>();
		String s = "Select black_wins, red_wins, game_draws, path, action from gametable where ";
		if(didBlackGoFirst)
			s+="black_first_rep ='"+rep+"';";
		else
			s+="red_first_rep ='"+rep+"';";
		rs=stmt.executeQuery(s);
		while(rs.next()){
			states.add(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
		}
		return states;
	}
	
}
