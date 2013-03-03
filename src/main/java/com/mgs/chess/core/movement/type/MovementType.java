package com.mgs.chess.core.movement.type;

import com.mgs.chess.core.ChessBoard;
import com.mgs.chess.core.Color;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.PieceOnLocation;
import com.mgs.chess.core.movement.Direction;
import com.mgs.chess.core.movement.Movement;

public interface MovementType {
  public Direction getDirection();

  public DevelopmentType getDevelopmentType();

  public int getDeltaX();

  public int getDeltaY(Color forColor);

  public boolean isApplicable(PieceOnLocation pieceOnLocation,
      Location locationTo, ChessBoard board, Movement previousMovement);
}