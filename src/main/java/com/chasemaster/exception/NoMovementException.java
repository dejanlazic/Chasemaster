package com.chasemaster.exception;

@SuppressWarnings("serial")
public class NoMovementException extends MatchException {
  public NoMovementException() {
    super();
  }

  public NoMovementException(String message) {
     super(message);
  }
}