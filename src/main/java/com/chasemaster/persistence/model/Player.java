package com.chasemaster.persistence.model;

import java.io.Serializable;

public class Player implements Serializable {
   private static final long serialVersionUID = 3543651458098937278L;

   private int id;
   private String username;
   private String firstName;
   private String lastName;

   public Player() {
      super();
   }

   public Player(int id) {
      super();
      this.id = id;
   }
   
   public Player(int id, String username, String firstName, String lastName) {
      super();
      
      this.id = id;
      this.username = username;
      this.firstName = firstName;
      this.lastName = lastName;
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

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("User [");
      sb.append("id=" + id);
      sb.append(", username=" + username);      
      sb.append(", firstName=" + firstName);
      sb.append(", lastName=" + lastName);
      sb.append("]");
      
      return sb.toString();
   }
}