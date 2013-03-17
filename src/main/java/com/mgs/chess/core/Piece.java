package com.mgs.chess.core;


public enum Piece implements SquareContent{
	WHITE_ROOK (PieceType.ROOK, Color.WHITE, "wrook.gif"),
	WHITE_QUEEN (PieceType.QUEEN, Color.WHITE, "wqueen.gif"),
	WHITE_KING (PieceType.KING, Color.WHITE, "wking.gif"),
	WHITE_KNIGHT (PieceType.KNIGHT, Color.WHITE, "wknight.gif"),
	WHITE_PAWN (PieceType.PAWN, Color.WHITE, "wpawn.gif"),
	WHITE_BISHOP (PieceType.BISHOP, Color.WHITE, "wbishop.gif"),
	
	BLACK_ROOK (PieceType.ROOK, Color.BLACK, "brook.gif"),
	BLACK_QUEEN (PieceType.QUEEN, Color.BLACK, "bqueen.gif"),
	BLACK_KING (PieceType.KING, Color.BLACK, "bking.gif"), 
	BLACK_KNIGHT (PieceType.KNIGHT, Color.BLACK, "bknight.gif"), 
	BLACK_PAWN (PieceType.PAWN, Color.BLACK, "bpawn.gif"), 
	BLACK_BISHOP (PieceType.BISHOP, Color.BLACK, "bbishop.gif");
	
	private final PieceType type;
	private final Color color;
	private final String imageName;

	private Piece(PieceType type, Color color, String imageName) {
		this.type = type;
		this.color = color;
		this.imageName = imageName;
	}
	
	public PieceType getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}
	
	public String getImageName() {
	  return imageName;
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

//  @Override
//  public String toString() {
//    return "Piece[" + type + " " + color + "]";
//  }
}