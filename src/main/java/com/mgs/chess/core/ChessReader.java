package com.mgs.chess.core;

import com.mgs.chess.core.movement.MovementLines;

public interface ChessReader {

  MovementLines findAllMovementLines(Location location);

  boolean containsPieceWithColor(Location location, Color color, ChessBoard board);

  MovementLines findMovementLines(PieceOnLocation pieceOnLocation);
}