package com.mgs.chess.core;

public class PieceOnLocation{
	private final Piece piece;
	private final Location location;

	public PieceOnLocation(Piece piece, Location location) {
		this.piece = piece;
		this.location = location;
	}

	public Piece getPiece() {
		return piece;
	}

	public Location getLocation() {
		return location;
	}

	public Color getColor() {
		return piece.getColor();
	}

	public PieceType getType() {
		return piece.getType();
	}

	@Override
	public String toString() {
		return "PieceInBoard [location=" + location	+ ", piece=" + piece + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((piece == null) ? 0 : piece.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PieceOnLocation other = (PieceOnLocation) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (piece == null) {
			if (other.piece != null)
				return false;
		} else if (!piece.equals(other.piece))
			return false;
		return true;
	}

	public boolean isOnInitialRow() {
		return getColor().getInitialRowFor(getType()) == getLocation().getCoordinateY();
	}
}
