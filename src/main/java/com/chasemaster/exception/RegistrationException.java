package com.chasemaster.exception;

@SuppressWarnings("serial")
public class RegistrationException extends Exception {
   public RegistrationException() {
   }

   public RegistrationException(String message) {
      super(message);
   }

   public RegistrationException(Throwable cause) {
      super(cause);
   }

   public RegistrationException(String message, Throwable cause) {
      super(message, cause);
   }
}