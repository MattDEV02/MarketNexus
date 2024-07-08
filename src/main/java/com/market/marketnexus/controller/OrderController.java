package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPaths;
import com.market.marketnexus.helpers.constants.GlobalErrorsMessages;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.model.Order;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.OrderService;
import com.market.marketnexus.service.UserService;
import com.market.marketnexus.service.email.OrderedUserSaleEmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(value = "/" + APIPaths.ORDER)
public class OrderController {

   private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
   @Autowired
   private OrderService orderService;
   @Autowired
   private UserService userService;
   @Autowired
   private OrderedUserSaleEmailService orderedUserSaleEmailService;


   @GetMapping(value = {"", "/"})
   public ModelAndView makeOrderFromCartLineItem(
           @Valid @ModelAttribute("loggedUser") User loggedUser,
           @NonNull HttpServletRequest request
   ) {
      ModelAndView modelAndView = new ModelAndView("redirect:/" + APIPaths.CART);
      if (!request.getHeader("referer").contains(APIPaths.CART)) {
         return modelAndView;
      }
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
         List<CartLineItem> cartLineItems = orderedCart.getCartLineItems();
         this.orderService.sendSalesOrderEmail(loggedUser, savedOrder);
         OrderController.LOGGER.info("New Order with ID: {}", savedOrder.getId());
         modelAndView.addObject("orderInsertedAt", orderInsertedAt);
         modelAndView.addObject("cart", orderedCart);
         modelAndView.addObject("cartLineItems", cartLineItems);
         modelAndView.setViewName(APIPaths.ORDER + GlobalValues.TEMPLATES_EXTENSION);
      }
      return modelAndView;
   }

}
