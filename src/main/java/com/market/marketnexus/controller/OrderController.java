package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPrefixes;
import com.market.marketnexus.helpers.constants.GlobalErrorsMessages;
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
import java.util.List;

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
   public ModelAndView makeOrderFromCartLineItem(
           @Valid @ModelAttribute("loggedUser") User loggedUser,
           @PathVariable("cartId") Long cartId,
           @NonNull HttpServletRequest request
   ) {
      ModelAndView modelAndView = new ModelAndView("redirect:/" + APIPrefixes.CART);
      if (!request.getHeader("referer").contains(APIPrefixes.CART)) {
         return modelAndView;
      }
      if (!this.credentialsService.areBuyerCredentials(loggedUser.getCredentials())) {
         modelAndView.addObject("userNotBuyerAddSaleToCartError", true);
         OrderController.LOGGER.error(GlobalErrorsMessages.USER_NOT_BUYER_ADD_SALE_TO_CART_ERROR);
      } else if (this.cartService.existsCartBtId(cartId)) {
         Cart cart = this.userService.getUserCurrentCart(loggedUser.getId());
         if (cart.getCartPrice() == 0) {
            OrderController.LOGGER.error(GlobalErrorsMessages.EMPTY_CART_ERROR);
            modelAndView.addObject("emptyCartError", true);
         } else if (loggedUser.getBalance() < cart.getCartPrice()) {
            OrderController.LOGGER.error(GlobalErrorsMessages.USER_BALANCE_LOWER_THAN_CART_PRICE_ERROR);
            modelAndView.addObject("userBalanceLowerThanCartPriceError", true);
         } else {
            Order savedOrder = this.orderService.makeOrder(loggedUser.getId());
            LocalDateTime orderInsertedAt = savedOrder.getInsertedAt();
            Cart orderedCart = savedOrder.getCart();
            List<CartLineItem> cartLineItems = this.cartService.getAllSoldCartLineItems(orderedCart);
            OrderController.LOGGER.info("New Order with ID: {}", savedOrder.getId());
            modelAndView.addObject("orderInsertedAt", orderInsertedAt);
            modelAndView.addObject("cart", orderedCart);
            modelAndView.addObject("cartLineItems", cartLineItems);
            modelAndView.setViewName(APIPrefixes.ORDER + GlobalValues.TEMPLATES_EXTENSION);
         }
      } else {
         OrderController.LOGGER.error(GlobalErrorsMessages.USER_CART_NOT_EXISTS_ERROR);
         modelAndView.addObject("userCartNotExistsError", true);
      }
      return modelAndView;
   }

}
