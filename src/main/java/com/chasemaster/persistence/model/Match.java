package com.chasemaster.persistence.model;

import java.io.Serializable;
import java.util.Date;

public class Match implements Serializable {
  private static final long serialVersionUID = 1;

  private int id;
  private Date playOn;

  public Match() {
    super();
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Match[");
    sb.append("id=" + id);
    sb.append(", playOn=" + playOn);
    sb.append("]");

    return sb.toString();
  }
}