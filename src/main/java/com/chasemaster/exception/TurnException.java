package com.chasemaster.exception;

@SuppressWarnings("serial")
public class TurnException extends Exception {
   public TurnException() {
   }

   public TurnException(String message) {
      super(message);
   }

   public TurnException(Throwable cause) {
      super(cause);
   }

   public TurnException(String message, Throwable cause) {
      super(message, cause);
   }
}