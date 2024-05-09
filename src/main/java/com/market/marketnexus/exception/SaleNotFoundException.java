package com.market.marketnexus.exception;

public class SaleNotFoundException extends RuntimeException {
   public SaleNotFoundException() {
      super();
   }

   public SaleNotFoundException(String message) {
      super(message);
   }
}
