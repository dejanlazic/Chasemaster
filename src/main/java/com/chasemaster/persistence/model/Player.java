package com.chasemaster.persistence.model;

import java.io.Serializable;

import com.chasemaster.Colour;

public class Player implements Serializable {
  private static final long serialVersionUID = 1;

  private int id;
  private String username;
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Player [");
    sb.append("id=" + id);
    sb.append(", username=" + username);
    // sb.append(", firstName=" + firstName);
    // sb.append(", lastName=" + lastName);
    sb.append(", colour=" + colour);
    sb.append(", isWhite=" + isWhite());
    sb.append("]");

    return sb.toString();
  }

  // test
  public static void main(String[] args) {
    Player p = new Player(1, "p1", Colour.WHITE);
    System.out.println(p);
    
    p = new Player(2, "p2", Colour.BLACK);
    System.out.println(p);
  }
}