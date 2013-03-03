package com.mgs.chess.core.movement;

import com.mgs.chess.core.Color;

public enum Direction {
	HORIZONTAL_LEFT (-1, 0), VERTICAL_FORWARD (0, 1), HORIZONTAL_RIGHT (1, 0), VERTICAL_BACKWARD (0, -1), 
	DIAGONAL_LEFT_FORWARD (-1, 1), DIAGONAL_RIGHT_FORWARD (1, 1), DIAGONAL_LEFT_BACKWARD (-1, -1), DIAGONAL_RIGHT_BACKWARD (1, -1), 
	KNIGHT_2LEFT_1FORWARD (-2, 1), KNIGHT_1LEFT_2FORWARD (-1, 2), KNIGHT_1RIGHT_2FORWARD (1, 2), KNIGHT_2RIGHT_1FORWARD (2, 1), 
	KNIGHT_2RIGHT_1BACKWARD (2, -1), KNIGHT_1RIGHT_2BACKWARD (1, -2), KNIGHT_1LEFT_2BACKWARD (-1, -2), KNIGHT_2LEFT_1BACKWARD (-2, -1);

	int deltaX;
	int deltaY;
	
	private Direction(int deltaX, int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY(Color forColor) {
		if (forColor == Color.UNDEFINED) return 0;
		return forColor == Color.WHITE ? deltaY : -deltaY;
	}
}