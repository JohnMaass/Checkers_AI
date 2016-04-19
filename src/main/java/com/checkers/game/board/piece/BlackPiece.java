package com.checkers.game.board.piece;

/**
 * The BlackPiece class extends the GamePiece class and overrides its constructor.
 * @author maass
 */
public class BlackPiece extends GamePiece{

	public BlackPiece(Type pieceID, int xPos, int yPos, GamePiece[][] board) {
		super(Color.BLACK, pieceID, xPos, yPos,board);
	}

}
