package com.mgs.chess.core;

import java.util.ArrayList;
import java.util.List;

import com.mgs.chess.core.movement.Movement;

public class ChessBoard {
	private final Squares squares;

	public ChessBoard() {
		this (new Squares());
	}
	
	public ChessBoard(Squares squares) {
		this.squares = squares;
	}

	public ChessBoard performMovement(Piece piece, Location locationFrom, Location locationTo) {
		Squares copy = squares.copy();
		copy.setContent(SquareContent.EMPTY_SQUARE, locationFrom.getCoordinateX(), locationFrom.getCoordinateY());
		copy.setContent(piece, locationTo.getCoordinateX(), locationTo.getCoordinateY());
		return new ChessBoard(copy);
	}
	
	public ChessBoard performMovement(PieceOnLocation pieceInBoard, Location locationTo) {
		return performMovement(pieceInBoard.getPiece(), pieceInBoard.getLocation(), locationTo);
	}

	public ChessBoard performMovement(Movement movement) {
		return performMovement(movement.getMovingPiece(), movement.getFrom(), movement.getTo());
	}

	public SquareContent getSquareContent(Location location) {
		return squares.getContent(location.getCoordinateX(), location.getCoordinateY());
	}

	public ChessBoard addPiece(Piece piece, Location location) {
		Squares copy = squares.copy();
		copy.setContent (piece, location.getCoordinateX(), location.getCoordinateY());
		return new ChessBoard(copy);
	}

	public List<PieceOnLocation> getPieces(Color movingColor) {
		List<PieceOnLocation> pieces = new ArrayList<PieceOnLocation>();
		for (Location location : Location.values()) {
			SquareContent content = squares.getContent(location.getCoordinateX(), location.getCoordinateY());
			if (! content.isEmpty()){
				Piece piece = (Piece) content;
				if (piece.getColor() == movingColor) pieces.add(piece.on(location));
			}
		}
		return pieces;
	}

	public List<PieceOnLocation> find(Piece pieceToFind) {
		List<PieceOnLocation> pieces = new ArrayList<PieceOnLocation>();
		for (Location location : Location.values()) {
			SquareContent content = squares.getContent(location.getCoordinateX(), location.getCoordinateY());
			if (! content.isEmpty()){
				Piece piece = (Piece) content;
				if (piece == pieceToFind) pieces.add(piece.on(location));
			}
		}
		return pieces;
	}

	public PieceOnLocation getPieceOnLocation(Location location) {
		return new PieceOnLocation(getSquareContent(location).asPiece(), location);
	}

	public ChessBoard empty(Location location) {
		Squares copy = squares.copy();
		copy.setContent(SquareContent.EMPTY_SQUARE, location.getCoordinateX(), location.getCoordinateY());
		return new ChessBoard(copy);
	}
}
