package com.mgs.chess.core;


public enum Piece implements SquareContent{
	WHITE_ROOK (PieceType.ROOK, Color.WHITE),
	WHITE_QUEEN (PieceType.QUEEN, Color.WHITE),
	WHITE_KING (PieceType.KING, Color.WHITE),
	WHITE_KNIGHT (PieceType.KNIGHT, Color.WHITE),
	WHITE_PAWN (PieceType.PAWN, Color.WHITE),
	WHITE_BISHOP (PieceType.BISHOP, Color.WHITE),
	
	BLACK_ROOK (PieceType.ROOK, Color.BLACK),
	BLACK_QUEEN (PieceType.QUEEN, Color.BLACK),
	BLACK_KING (PieceType.KING, Color.BLACK), 
	BLACK_KNIGHT (PieceType.KNIGHT, Color.BLACK), 
	BLACK_PAWN (PieceType.PAWN, Color.BLACK), 
	BLACK_BISHOP (PieceType.BISHOP, Color.BLACK),
	;
	
	private final PieceType type;
	private final Color color;

	private Piece(PieceType type, Color color) {
		this.type = type;
		this.color = color;
	}

	public PieceType getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}

	public PieceOnLocation on(Location location) {
		return new PieceOnLocation(this, location);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	public static Piece with(PieceType pieceType, Color color) {
		switch (color){
		case BLACK:
			switch (pieceType){
				case KING:
					return BLACK_KING;
				case QUEEN:
					return BLACK_QUEEN;
				case ROOK:
					return BLACK_ROOK;
				case KNIGHT:
					return BLACK_KNIGHT;
			}
		case WHITE:
			switch (pieceType){
			case KING:
				return WHITE_KING;
			case QUEEN:
				return WHITE_QUEEN;
			case ROOK:
				return WHITE_ROOK;
			case KNIGHT:
				return WHITE_KNIGHT;
			}
		}
		return null;
	}

	@Override
	public Color getContentColor() {
		return getColor();
	}

	@Override
	public Piece asPiece() {
		return this;
	}

	public boolean colorIs(Color color) {
		return getColor() == color;
	}
}
