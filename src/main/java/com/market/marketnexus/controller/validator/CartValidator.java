package com.market.marketnexus.controller.validator;

import com.market.marketnexus.helpers.credentials.Roles;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CartValidator implements Validator {
   @Autowired
   private CartService cartService;
   private User loggedUser;
   private Sale currentSale;

   public Sale getCurrentSale() {
      return this.currentSale;
   }

   public void setCurrentSale(Sale currentSale) {
      this.currentSale = currentSale;
   }

   public User getLoggedUser() {
      return this.loggedUser;
   }

   public void setLoggedUser(User loggedUser) {
      this.loggedUser = loggedUser;
   }

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      Cart cart = (Cart) object;
      if (!this.getLoggedUser().getCredentials().getRole().contains(Roles.BUYER_ROLE.toString())) {
         errors.reject("userNotBuyerAddOwnSaleToCartError", "Buyer users cannot add sales at them cart.");
      }
      if (this.loggedUser.equals(this.getCurrentSale().getUser())) {
         errors.reject("userAddOwnSaleToCartError", "You cannot add your own sale to your cart.");
      }
      if (this.cartService.existsCartLineItemSale(cart.getId(), this.getCurrentSale())) {
         errors.reject("userAddExistingSaleToCartError", "You have already added this product to your cart.");
      }

   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Cart.class.equals(aClass);
   }
}
