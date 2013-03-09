package com.chasemaster;

import com.mgs.chess.core.Location;

public class Movement {
  private final Location from;
  private final Location to;
  private final Long duration;
  private Colour Colour; // needed?

  public Movement(Location from, Location to, Long duration) {
    this.from = from;
    this.to = to;
    this.duration = duration;
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
    return "Movement[from=" + from + ", to=" + to + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((from == null) ? 0 : from.hashCode());
    result = prime * result + ((to == null) ? 0 : to.hashCode());
    //result = prime * result + ((duration == null) ? 0 : duration.hashCode());

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
    if (to == null) {
      if (other.to != null)
        return false;
    } else if (!to.equals(other.to))
      return false;
    return true;
  }
}