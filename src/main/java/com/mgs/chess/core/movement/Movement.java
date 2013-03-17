package com.mgs.chess.core.movement;

import com.mgs.chess.core.Color;
import com.mgs.chess.core.Location;
import com.mgs.chess.core.Piece;
import com.mgs.chess.core.PieceOnLocation;

public class Movement {
  private Piece piece;
  private Location from;
  private Location to;
  private Long duration;

  public Movement(Piece piece, Location from, Location to) {
    this.piece = piece;
    this.from = from;
    this.to = to;
  }

  public Movement(Piece piece, Location from, Location to, Long duration) {
    this.piece = piece;
    this.from = from;
    this.to = to;
    this.duration = duration;
  }

  public Movement(PieceOnLocation pieceInBoard, Location locationTo) {
    this(pieceInBoard.getPiece(), pieceInBoard.getLocation(), locationTo);
  }

  public Piece getMovingPiece() {
    return piece;
  }

  public Location getFrom() {
    return from;
  }

  public Location getTo() {
    return to;
  }

  public Long getDuration() {
    return duration;
  }


  @Override
  public String toString() {
    return "Movement[piece=" + piece + ", " + "from=" + from + ", to=" + to + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((from == null) ? 0 : from.hashCode());
    result = prime * result + ((piece == null) ? 0 : piece.hashCode());
    result = prime * result + ((to == null) ? 0 : to.hashCode());
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
    Movement other = (Movement) obj;
    if (from == null) {
      if (other.from != null)
        return false;
    } else if (!from.equals(other.from))
      return false;
    if (piece == null) {
      if (other.piece != null)
        return false;
    } else if (!piece.equals(other.piece))
      return false;
    if (to == null) {
      if (other.to != null)
        return false;
    } else if (!to.equals(other.to))
      return false;
    return true;
  }

  public Color getColor() {
    return getMovingPiece().getColor();
  }
}