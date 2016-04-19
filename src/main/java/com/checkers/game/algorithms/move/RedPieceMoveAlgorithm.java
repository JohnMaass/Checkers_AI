package com.checkers.game.algorithms.move;

import java.awt.Point;
import java.util.ArrayList;

import com.checkers.game.board.piece.GamePiece;
import com.checkers.game.board.actions.Move;

public class RedPieceMoveAlgorithm extends MoveAlgorithm{

	public RedPieceMoveAlgorithm(GamePiece[][] board) {
		super(board);
	}

	@Override
	public void findMoves(Point p, ArrayList<Move> moves, ArrayList<Move> unusedMoves) {
		findMovesUp(p, moves, unusedMoves);
	}
	
}
