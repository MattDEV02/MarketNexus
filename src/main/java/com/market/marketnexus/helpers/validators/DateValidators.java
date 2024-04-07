package com.market.marketnexus.helpers.validators;

import java.time.LocalDateTime;

public class DateValidators {

   public static boolean validateTimestamp(LocalDateTime timestamp) {
      return timestamp != null && timestamp instanceof LocalDateTime && timestamp.isBefore(LocalDateTime.now());
   }
}
