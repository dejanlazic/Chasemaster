package com.chasemaster.ws.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="User")
public class User {
   private String username;
   private String password;
   private String email;
   
   public User() {}
   
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
   public String getEmail() {
      return email;
   }
   public void setEmail(String email) {
      this.email = email;
   }
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("User[");
      sb.append("username=" + username);
      sb.append(", password=" + password);
      sb.append(", email=" + email);
      sb.append("]");
      
      return sb.toString();
   }
}