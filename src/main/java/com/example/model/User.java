package com.example.model;

public class User {
  private Long id;
  private String username;
  private String password;
  private String passwordConfirmation;
  private String email;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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
    sb.append("]");

    return sb.toString();
  }
}