package com.market.marketnexus.exception;

public class UserCredentialsUsernameNotExistsException extends RuntimeException {
   public UserCredentialsUsernameNotExistsException() {
      super();
   }

   public UserCredentialsUsernameNotExistsException(String message) {
      super(message);
   }
}