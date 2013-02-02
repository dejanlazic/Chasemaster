package com.chasemaster.ws.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userCredentials", namespace = "service")
@XmlRootElement(name = "User")
public class UserCredentials {
   @XmlElement(name = "Username")
   private String username;
   @XmlElement(name = "Password")
   private String password;

   public UserCredentials() {
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
      StringBuilder sb = new StringBuilder("UserCredentials[");
      sb.append("username=" + username);
      sb.append(", password=" + password);
      sb.append("]");

      return sb.toString();
   }
}