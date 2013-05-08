package com.chasemaster.exception;

@SuppressWarnings("serial")
public class MovementException extends Exception {
   public MovementException() {
   }

   public MovementException(String message) {
      super(message);
   }

   public MovementException(Throwable cause) {
      super(cause);
   }

   public MovementException(String message, Throwable cause) {
      super(message, cause);
   }
}