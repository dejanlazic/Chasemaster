package com.mgs.chess.core;

import java.util.List;

import com.mgs.chess.core.movement.Movement;


public interface ChessAnaliser {

	public Movement findCheckMateMovement(ChessBoard board, Movement previousMovement, Color color);

	public boolean isInCheck(ChessBoard board, Color color);

	public List<Location> findReachableLocations(PieceOnLocation pieceOnLocation, ChessBoard board, Movement previousMovement);
}
