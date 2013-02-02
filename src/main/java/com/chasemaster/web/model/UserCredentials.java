package com.chasemaster.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class UserCredentials {  
   @Id
   @GeneratedValue
   private String username;
   @Transient
   private String password;
      
   public UserCredentials() {
      super();
   }
   
   public UserCredentials(String username, String password) {
      super();
      this.username = username;
      this.password = password;
   }

   public String getUsername() {
      return username;
   }
   public void setUsername(String username) {
      this.username = username;
   }
   public String getPassword() {
      return password;
   }
   public void setPassword(String password) {
      this.password = password;
   }
   
   @Override
   public String toString() {
      return "UserCredentials [username=" + username + ", password=" + password + "]";
   }            
}