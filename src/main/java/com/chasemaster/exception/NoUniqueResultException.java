package com.chasemaster.exception;

@SuppressWarnings("serial")
public class NoUniqueResultException extends PersistenceException {
  public NoUniqueResultException() {
    super();
  }

  public NoUniqueResultException(String message) {
     super(message);
  }
}