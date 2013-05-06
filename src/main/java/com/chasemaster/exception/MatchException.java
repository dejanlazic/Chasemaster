package com.chasemaster.exception;

@SuppressWarnings("serial")
public class MatchException extends Exception {
   public MatchException() {
   }

   public MatchException(String message) {
      super(message);
   }

   public MatchException(Throwable cause) {
      super(cause);
   }

   public MatchException(String message, Throwable cause) {
      super(message, cause);
   }
}