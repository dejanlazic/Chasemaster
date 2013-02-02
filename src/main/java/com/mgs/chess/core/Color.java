package com.mgs.chess.core;

public enum Color {
	WHITE, BLACK, UNDEFINED;

	public Color opposite() {
		if (this==UNDEFINED) return UNDEFINED;
		return this == WHITE ? BLACK : WHITE;
	}

	public int getInitialRowFor(PieceType type) {
		if (this==WHITE && type == PieceType.PAWN) return 2;
		if (this==WHITE && type != PieceType.PAWN) return 1;
		if (this==BLACK && type == PieceType.PAWN) return 7;
		if (this==BLACK && type != PieceType.PAWN) return 8;
		return -1;
	}
}
