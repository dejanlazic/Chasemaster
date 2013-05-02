package com.chasemaster.exception;

@SuppressWarnings("serial")
public class NoMovementException extends GameException {
  public NoMovementException() {
    super();
  }

  public NoMovementException(String message) {
     super(message);
  }
}