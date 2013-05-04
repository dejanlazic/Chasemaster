package com.mgs.chess.core;

public interface SquareContent {
	public static final EmptySquare EMPTY_SQUARE = EmptySquare.INSTANCE;
	
	enum EmptySquare implements SquareContent {
		INSTANCE;

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public Color getContentColor() {
			return Color.UNDEFINED;
		}

		@Override
		public Piece asPiece() {
			throw new PieceNotFoundException();
		}
	}
	
	public boolean isEmpty ();
	public Color getContentColor();
	public Piece asPiece();
}