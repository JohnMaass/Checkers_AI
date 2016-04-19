package com.checkers.game.board.piece;

/**
 * Enum used to determine the color of a piece.
 * 
 * @author maass
 */
public enum Color {
	RED,
	BLACK;
	
        /**
         * Gets oppisite color of current value.
         * 
         * @return 
         */
	public Color getOppositeColor(){
		return this==BLACK?RED:BLACK;
	}
}
