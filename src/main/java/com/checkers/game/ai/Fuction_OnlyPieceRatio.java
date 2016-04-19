package com.checkers.game.ai;

import com.checkers.game.board.Board;
import com.checkers.game.board.piece.Color;

public class Fuction_OnlyPieceRatio extends AbstractFunction{

	public Fuction_OnlyPieceRatio(Color color) {
		super(color);
		// TODO Auto-generated constructor stub
	}

	@Override
	double manValueMove(Board b) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	double kingValueMove(Board b) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	double jumpValue(Board b) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	double findBoardValue(Board b) {
		// TODO Auto-generated method stub
		return pieceWeight(b);
	}

}
