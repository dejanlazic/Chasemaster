package com.chasemaster.persistence;

public class DAOException extends Exception {
  private static final long serialVersionUID = -8338818875632778212L;

  public DAOException() {
  }

  public DAOException(String message) {
    super(message);
  }

  public DAOException(Throwable cause) {
    super(cause);
  }

  public DAOException(String message, Throwable cause) {
    super(message, cause);
  }
}