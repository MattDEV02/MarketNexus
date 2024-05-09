package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPrefixes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.model.Order;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CartService;
import com.market.marketnexus.service.CredentialsService;
import com.market.marketnexus.service.OrderService;
import com.market.marketnexus.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

   private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
   @Autowired
   private OrderService orderService;
   @Autowired
   private CartService cartService;
   @Autowired
   private UserService userService;
   @Autowired
   private CredentialsService credentialsService;

   @GetMapping(value = {"/{cartId}", "/{cartId}/"})
   public ModelAndView makeOrderFromCartLineItem(@Valid @ModelAttribute("loggedUser") User loggedUser, @PathVariable("cartId") Long cartId, @NonNull HttpServletRequest request) {
      ModelAndView modelAndView = new ModelAndView("redirect:/" + APIPrefixes.CART);
      if (!request.getHeader("referer").contains(APIPrefixes.CART)) {
         return modelAndView;
      }
      if (!this.credentialsService.areBuyerCredentials(loggedUser.getCredentials())) {
         modelAndView.addObject("userNotBuyerAddOwnSaleToCartError", true);
      } else if (this.cartService.existsCartBtId(cartId)) {
         Cart cart = this.userService.getUserCurrentCart(loggedUser.getId());
         if (cart.getCartPrice() == 0) {
            LOGGER.error("emptyCartError");
            modelAndView.addObject("emptyCartError", true);
         } else if (loggedUser.getBalance() < cart.getCartPrice()) {
            LOGGER.error("userBalanceLowerThanCartPriceError");
            modelAndView.addObject("userBalanceLowerThanCartPriceError", true);
         } else {
            Order savedOrder = this.orderService.makeOrder(loggedUser.getId());
            LocalDateTime orderInsertedAt = savedOrder.getInsertedAt();
            Cart orderCart = savedOrder.getCart();
            Set<CartLineItem> cartLineItems = this.cartService.getAllSoldCartLineItems(orderCart.getId());
            modelAndView.addObject("orderInsertedAt", orderInsertedAt);
            modelAndView.addObject("cart", orderCart);
            modelAndView.addObject("cartLineItems", cartLineItems);
            LOGGER.info("New Order with ID: {}", savedOrder.getId());
            modelAndView.setViewName(APIPrefixes.ORDER + GlobalValues.TEMPLATES_EXTENSION);
         }
      } else {
         modelAndView.addObject("userCartNotExistsError", true);
      }
      return modelAndView;
   }

}
