package com.checkers.game.algorithms.move;

import com.checkers.game.algorithms.move.MoveAlgorithm;
import java.awt.Point;
import java.util.ArrayList;

import com.checkers.game.board.piece.GamePiece;
import com.checkers.game.board.actions.Move;

public class KingMoveAlgorithm extends MoveAlgorithm{

	public KingMoveAlgorithm(GamePiece[][] board) {
		super(board);
	}

	@Override
	public void findMoves(Point p, ArrayList<Move> moves, ArrayList<Move> unusedMoves) {
		findMovesDown(p, moves, unusedMoves);
		findMovesUp(p, moves, unusedMoves);
	}

}
