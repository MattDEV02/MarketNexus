package com.market.marketnexus.helpers.sale;

import com.market.marketnexus.model.Sale;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Utils {

   @Contract(pure = true)
   public static @NotNull Float roundNumberTo2Decimals(Float number) {
      if (number == null) {
         return 0.0F;
      }
      return Math.round(number * 100) / 100.0F;
   }

   public static @NotNull Float calculateSalePrice(@NotNull Sale sale) {
      return Utils.roundNumberTo2Decimals(sale.getProduct().getPrice() * sale.getQuantity());
   }

   public static @NotNull Float calculateSalePrice(@NotNull Sale sale, Integer quantity) {
      return Utils.roundNumberTo2Decimals(sale.getProduct().getPrice() * quantity);
   }
}
