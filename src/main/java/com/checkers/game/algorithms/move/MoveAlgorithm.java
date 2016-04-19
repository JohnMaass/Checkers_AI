package com.checkers.game.algorithms.move;

import java.awt.Point;
import java.util.ArrayList;

import com.checkers.game.board.piece.GamePiece;
import com.checkers.game.board.actions.Move;
import com.checkers.game.board.piece.Type;

public abstract class MoveAlgorithm {
	private GamePiece[][] board;
	
	public MoveAlgorithm(GamePiece[][] board){
		this.board=board;
	}
	
	protected void findMovesUp(Point p, ArrayList<Move> moves, ArrayList<Move> unusedMoves){
		//check if can move up
		if(p.x!=0){
			//check left bound
			if(p.y!=0 && board[p.x-1][p.y-1]==null){
				moves.add(setMove(unusedMoves.remove(0), p.x, p.y, p.x-1, p.y-1, board[p.x][p.y].getType()));
				//unusedMoves.remove(0);
			}
			//check right bound
			if(p.y!=7 && board[p.x-1][p.y+1]==null){
				moves.add(setMove(unusedMoves.remove(0), p.x, p.y, p.x-1, p.y+1, board[p.x][p.y].getType()));
				//unusedMoves.remove(0);
			}
		}
		
	}

	protected void findMovesDown(Point p, ArrayList<Move> moves, ArrayList<Move> unusedMoves) {
		// check if can move down
		if (p.x != 7) {
			// check left bound
			if (p.y != 0 && board[p.x + 1][p.y - 1] == null){
				moves.add(
						setMove(unusedMoves.remove(0), p.x, p.y, p.x + 1, p.y - 1, board[p.x][p.y].getType()));
				//unusedMoves.remove(0);
			}
			// check right bound
			if (p.y != 7 && board[p.x + 1][p.y + 1] == null) {
				moves.add(
						setMove(unusedMoves.remove(0), p.x, p.y, p.x + 1, p.y + 1, board[p.x][p.y].getType()));
				//unusedMoves.remove(0);
			}
		}

	}

	private Move setMove(Move m, int startX, int startY, int finalX, int finalY, Type id){
		m.setAction(startX, startY, finalX, finalY, id);
		return m;
	}
	
	abstract public void findMoves(Point p, ArrayList<Move> moves, ArrayList<Move> unusedMoves);
}
