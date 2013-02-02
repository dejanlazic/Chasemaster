package com.chasemaster.ws.data;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", namespace = "service")
@XmlRootElement(name="User")
public class User extends AbstractEntity {
   @Basic
   @XmlElement(name="Username")
   private String username;
   @Basic
   @XmlElement(name="Password")
   private String password;
   @XmlElement(name="PasswordConfirmation")
   private String passwordConfirmation;
   @Column(name="e_mail")
   @XmlElement(name="EMail")
   private String email;
   @Column(name="registered_on")
   @XmlTransient
   private Date registeredOn;
   
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
   public Date getRegisteredOn() {
      return registeredOn;
   }
   public void setRegisteredOn(Date registeredOn) {
      this.registeredOn = registeredOn;
   }
   public String getPasswordConfirmation() {
      return passwordConfirmation;
   }

   public void setPasswordConfirmation(String passwordConfirmation) {
      this.passwordConfirmation = passwordConfirmation;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("User[");
      sb.append("id=" + id);
      sb.append(", username=" + username);
      sb.append(", password=" + password);
      sb.append(", passwordConfirmation=" + passwordConfirmation);
      sb.append(", email=" + email);
      sb.append(", registeredOn=" + registeredOn);
      sb.append("]");
      
      return sb.toString();
   }
}