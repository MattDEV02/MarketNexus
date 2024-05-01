package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPrefixes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.helpers.credentials.Roles;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.model.Order;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CartService;
import com.market.marketnexus.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Set;

@Controller
@RequestMapping(value = "/" + APIPrefixes.ORDER)
public class OrderController {

   @Autowired
   private OrderService orderService;
   @Autowired
   private CartService cartService;

   @GetMapping(value = {"/{cartId}", "/{cartId}/"})
   public ModelAndView makeOrderFromCartLineItem(@Valid @ModelAttribute("loggedUser") User loggedUser, @PathVariable("cartId") Long cartId, @NonNull HttpServletRequest request) {
      ModelAndView modelAndView = new ModelAndView("redirect:/" + APIPrefixes.CART);
      if (!request.getHeader("referer").contains(APIPrefixes.CART)) {
         return modelAndView;
      }
      if (!loggedUser.getCredentials().getRole().contains(Roles.BUYER_ROLE.toString())) {
         modelAndView.addObject("userNotBuyerAddOwnSaleToCartError", true);
      } else if (this.cartService.existsCartBtId(cartId)) {
         Cart cart = loggedUser.getCart();
         if (loggedUser.getBalance() < cart.getCartPrice()) {
            modelAndView.addObject("userBalanceLowerThanCartPriceError", true);
         } else {
            Order order = this.orderService.makeOrder(loggedUser, cart);
            LocalDateTime orderInsertedAt = order.getInsertedAt();
            Cart orderCart = order.getCart();
            Set<CartLineItem> cartLineItems = this.cartService.getAllCartLineItems(orderCart.getId());
            modelAndView.addObject("orderInsertedAt", orderInsertedAt);
            modelAndView.addObject("cart", orderCart);
            modelAndView.addObject("cartLineItems", cartLineItems);
            modelAndView.setViewName(APIPrefixes.ORDER + GlobalValues.TEMPLATES_EXTENSION);
         }
      } else {
         modelAndView.addObject("userCartNotExistsError", true);
      }
      return modelAndView;
   }

}
