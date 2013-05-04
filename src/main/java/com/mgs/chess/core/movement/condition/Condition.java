package com.mgs.chess.core.movement.condition;

import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.movement.Movement;

public interface Condition {
  boolean isApplicable(PieceOnLocation pieceOnLocation, Location locationTo, ChessBoard board, Movement previousMovement);
}