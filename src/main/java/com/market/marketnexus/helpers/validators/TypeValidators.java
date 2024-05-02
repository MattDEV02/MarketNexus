package com.market.marketnexus.helpers.validators;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class TypeValidators {

   public static @NotNull Boolean validateTimestamp(LocalDateTime timestamp) {
      return timestamp != null && timestamp.isBefore(LocalDateTime.now());
   }

   public static @NotNull Boolean validateString(String string) {
      return string != null && !string.isBlank() && !string.isEmpty();
   }
}
