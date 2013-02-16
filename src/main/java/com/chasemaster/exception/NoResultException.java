package com.chasemaster.exception;

@SuppressWarnings("serial")
public class NoResultException extends PersistenceException {
  public NoResultException() {
    super();
  }

  public NoResultException(String message) {
     super(message);
  }
}