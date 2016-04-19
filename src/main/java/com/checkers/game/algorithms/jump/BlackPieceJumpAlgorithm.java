package com.checkers.game.algorithms.jump;

import com.checkers.game.algorithms.jump.JumpAlgorithm;
import java.util.ArrayList;

import com.checkers.game.board.piece.Color;
import com.checkers.game.board.piece.GamePiece;
import com.checkers.game.board.actions.Jump;
import com.checkers.game.board.piece.Type;

public class BlackPieceJumpAlgorithm extends JumpAlgorithm{
	
	public BlackPieceJumpAlgorithm(GamePiece[][] board) {
		super(board, Color.BLACK);
	}

	@Override
	protected void findJumpsReccur(ArrayList<Jump> jumps, ArrayList<Jump> unusedJumps, boolean notRoot) {
		boolean isLeaf = true;
		if(findJumpsDownLeft()){
			isLeaf=false;
			findJumpsReccur(jumps, unusedJumps, true);
		}
		if(findJumpsDownRight()){
			isLeaf=false;
			findJumpsReccur(jumps, unusedJumps, true);
		}
		if(isLeaf && notRoot)
			jumps.add(setJump(unusedJumps.remove(0),Type.MAN));
		removeLastJump();
	}
}
