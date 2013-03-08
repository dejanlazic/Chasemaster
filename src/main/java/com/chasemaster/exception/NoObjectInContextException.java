package com.chasemaster.exception;

@SuppressWarnings("serial")
public class NoObjectInContextException extends PersistenceException {
  public NoObjectInContextException() {
    super();
  }

  public NoObjectInContextException(String message) {
     super(message);
  }
}