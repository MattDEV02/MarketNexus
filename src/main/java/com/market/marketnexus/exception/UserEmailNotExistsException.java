package com.market.marketnexus.exception;

public class UserEmailNotExistsException extends RuntimeException {
   public UserEmailNotExistsException() {
      super();
   }

   public UserEmailNotExistsException(String message) {
      super(message);
   }
}
