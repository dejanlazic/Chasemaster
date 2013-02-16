package com.chasemaster.exception;

@SuppressWarnings("serial")
public class PersistenceException extends RuntimeException {
  public PersistenceException() {
  }

  public PersistenceException(String message) {
     super(message);
  }
}