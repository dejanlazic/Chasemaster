package com.chasemaster.persistence.model;

import java.util.ArrayList;
import java.util.List;

import com.mgs.chess.core.movement.Movement;

public class Turn {
  private int id;
  private String colour;
  private List<Integer> winners;
  private List<Movement> movements;
  
  public Turn() {
    super();
    winners = new ArrayList<Integer>();
    movements = new ArrayList<Movement>();
  }
  
  public Turn(int id) {
    this();
    this.id = id;
  }
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getColour() {
    return colour;
  }
  public void setColour(String colour) {
    this.colour = colour;
  }
  public List<Integer> getWinners() {
    return winners;
  }
  public void setWinners(List<Integer> winners) {
    this.winners = winners;
  }
  
  
  public List<Movement> getMovements() {
    return movements;
  }

  public void setMovements(List<Movement> movements) {
    this.movements = movements;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Turn[");
    sb.append("id=" + id);
    sb.append(", colour=" + colour);
    sb.append(", winners=" + winners);
    sb.append(", movements=" + movements);
    sb.append("]");
    
    return sb.toString();
  }  
}