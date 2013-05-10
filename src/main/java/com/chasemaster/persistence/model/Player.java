package com.chasemaster.persistence.model;

import java.io.Serializable;

import com.chasemaster.Colour;

public class Player implements Serializable {
  private static final long serialVersionUID = 1;

  private int id;
  private int matchId;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private Colour colour;

  public Player() {
    super();
  }

  public Player(int id) {
    super();
    this.id = id;
  }

  public Player(int id, String username, Colour colour) {
    super();

    this.id = id;
    this.username = username;
    this.colour = colour;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Colour getColour() {
    return colour;
  }

  public void setColour(Colour colour) {
    this.colour = colour;
  }

  // helper
  public Boolean isWhite() {
    return colour == Colour.WHITE;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getMatchId() {
    return matchId;
  }

  public void setMatchId(int matchId) {
    this.matchId = matchId;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Player[");
    sb.append("id=" + id);
    sb.append(", matchId=" + matchId);
    sb.append(", username=" + username);
    sb.append(", colour=" + colour);
    sb.append(", isWhite=" + isWhite());
    sb.append("]");

    return sb.toString();
  }
}