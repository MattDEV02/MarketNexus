package com.market.marketnexus.controller;

import com.market.marketnexus.exception.SaleNotFoundException;
import com.market.marketnexus.helpers.constants.APIPaths;
import com.market.marketnexus.helpers.constants.GlobalErrorsMessages;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.helpers.validators.TypeValidators;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CartService;
import com.market.marketnexus.service.SaleService;
import com.market.marketnexus.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/" + APIPaths.CART)
public class CartController {

   private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
   @Autowired
   private UserService userService;
   @Autowired
   private SaleService saleService;
   @Autowired
   private CartService cartService;


   @GetMapping(value = {"", "/"})
   public ModelAndView showCart(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(APIPaths.CART + GlobalValues.TEMPLATES_EXTENSION);
      Cart cart = this.userService.getUserCurrentCart(loggedUser.getId());
      List<CartLineItem> cartLineItems = this.cartService.getAllNotSoldCartLineItems(cart);
      modelAndView.addObject("cart", cart);
      modelAndView.addObject("cartLineItems", cartLineItems);
      return modelAndView;
   }

   @GetMapping(value = {"/{saleId}", "/{saleId}/"})
   public ModelAndView addSaleProductToCart(@Valid @ModelAttribute("loggedUser") User loggedUser, @PathVariable("saleId") Long saleId) {
      ModelAndView modelAndView = new ModelAndView(APIPaths.MARKETPLACE + "/sale" + GlobalValues.TEMPLATES_EXTENSION);
      try {
         Sale sale = this.saleService.getSale(saleId);
         modelAndView.addObject("sale", sale);
         modelAndView.addObject("isAddedToCart", false);
         Cart cart = this.userService.getUserCurrentCart(loggedUser.getId());
         if (loggedUser.equals(sale.getUser())) {
            CartController.LOGGER.error(GlobalErrorsMessages.USER_ADD_OWN_SALE_TO_CART_ERROR);
            modelAndView.addObject("userAddOwnSaleToCartError", true);
         } else if (this.cartService.existsCartLineItemSale(cart, sale)) {
            CartController.LOGGER.error(GlobalErrorsMessages.USER_ADD_EXISTING_SALE_TO_CART_ERROR);
            modelAndView.addObject("userAddExistingSaleToCartError", true);
         } else {
            CartLineItem savedCartLineItem = this.cartService.makeCartLineItem(cart, sale);
            modelAndView.addObject("sale", savedCartLineItem.getSale());
            modelAndView.addObject("isAddedToCart", TypeValidators.validateTimestamp(savedCartLineItem.getInsertedAt()));
         }
      } catch (SaleNotFoundException saleNotFoundException) {
         CartController.LOGGER.error(saleNotFoundException.getMessage());
         modelAndView.setViewName("redirect:/" + APIPaths.MARKETPLACE + "/sale/" + saleId);
      }
      return modelAndView;
   }

   @PutMapping(value = {"/updateCartLineItemQuantity/{cartLineItemId}", "/updateCartLineItemQuantity/{cartLineItemId}/"})
   public ModelAndView updateCartLineItemQuantity(@Valid @ModelAttribute("loggedUser") User loggedUser, @PathVariable("cartLineItemId") Long cartLineItemId, @RequestBody Map<String, String> data) {
      ModelAndView modelAndView = new ModelAndView(APIPaths.CART + GlobalValues.TEMPLATES_EXTENSION + " :: dynamicCartSection");
      try {
         Cart cart = this.userService.getUserCurrentCart(loggedUser.getId());
         Integer quantity = Integer.parseInt(data.get("quantity"));
         this.cartService.updateCartLineItem(cart, cartLineItemId, quantity);
         List<CartLineItem> cartLineItems = this.cartService.getAllNotSoldCartLineItems(cart);
         modelAndView.addObject("cart", cart);
         modelAndView.addObject("cartLineItems", cartLineItems);
      } catch (NumberFormatException numberFormatException) {
         CartController.LOGGER.error(numberFormatException.getMessage(), numberFormatException);
      }
      return modelAndView;
   }

   @DeleteMapping(value = {"/delete/{cartLineItemId}", "/delete/{cartLineItemId}/"})
   public ModelAndView deleteCartLineItemById(@Valid @ModelAttribute("loggedUser") User loggedUser, @PathVariable("cartLineItemId") Long cartLineItemId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/" + APIPaths.CART);
      Cart cart = this.userService.getUserCurrentCart(loggedUser.getId());
      if (this.cartService.deleteCartLineItem(cart, cartLineItemId)) {
         CartController.LOGGER.info("Deleted CartLineItem with CartLineItem ID: {}", cartLineItemId);
         modelAndView.setViewName(APIPaths.CART + GlobalValues.TEMPLATES_EXTENSION + " :: dynamicCartSection");
         modelAndView.addObject("cartLineItemDeletedSuccess", true);
         List<CartLineItem> cartLineItems = this.cartService.getAllNotSoldCartLineItems(cart);
         modelAndView.addObject("cart", cart);
         modelAndView.addObject("cartLineItems", cartLineItems);
      } else {
         CartController.LOGGER.error(GlobalErrorsMessages.CART_LINE_NOT_DELETED_ERROR);
         modelAndView.addObject("cartLineItemNotDeletedError", true);
      }
      return modelAndView;
   }
}
