package com.chasemaster.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Match implements Serializable {
  private static final long serialVersionUID = 1;

  private int id;
  private Date playOn;
  List<Turn> turns;

  public Match() {
    super();
    turns = new ArrayList<Turn>();
  }

  public Match(int id) {
    this();
    this.id = id;
  }

  public Match(int id, Date playOn) {
    this(id);
    this.playOn = playOn;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getPlayOn() {
    return playOn;
  }

  public void setPlayOn(Date playOn) {
    this.playOn = playOn;
  }    

  public List<Turn> getTurns() {
    return turns;
  }

  public void setTurns(List<Turn> turns) {
    this.turns = turns;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Match[");
    sb.append("id=" + id);
    sb.append(", playOn=" + playOn);
    sb.append(", turns=" + ((turns==null)? "NULL" : turns));
    sb.append("]");

    return sb.toString();
  }
}