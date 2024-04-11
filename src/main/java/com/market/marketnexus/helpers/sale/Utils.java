package com.market.marketnexus.helpers.sale;

public class Utils {

   public static Float roundNumberTo2Decimals(Float number) {
      if (number == null || !(number instanceof Float)) {
         return 0.0f;
      }
      return Math.round(number * 100.0) / 100.0f;
   }
}
