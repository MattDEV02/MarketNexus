package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APISuffixes;
import com.market.marketnexus.helpers.validators.DateValidators;
import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CartLineItemService;
import com.market.marketnexus.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping(value = "/" + APISuffixes.CART)
public class CartController {

   @Autowired
   private SaleService saleService;
   @Autowired
   private CartLineItemService cartLineItemService;

   @GetMapping(value = {"", "/"})
   public ModelAndView showCartLineItem(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(APISuffixes.CART + ".html");
      Set<CartLineItem> cartProducts = this.cartLineItemService.getAllCartLineItemsByUser(loggedUser);
      Float cartTotalPrice = this.cartLineItemService.getTotalCartPriceByUser(loggedUser);
      modelAndView.addObject("cartProducts", cartProducts);
      modelAndView.addObject("cartTotalPrice", cartTotalPrice);
      return modelAndView;
   }

   @GetMapping(value = {"/{saleId}", "/{saleId}/"})
   public ModelAndView addSaleProductToCartById(@Valid @ModelAttribute("loggedUser") User loggedUser, @PathVariable("saleId") Long saleId, @RequestParam("quantity") Integer quantity) {
      ModelAndView modelAndView = new ModelAndView(APISuffixes.SALE + ".html");
      Sale sale = this.saleService.getSale(saleId);
      CartLineItem cart = new CartLineItem(sale, loggedUser);
      CartLineItem savedCartLineItem = this.cartLineItemService.saveCartLineItem(cart);
      modelAndView.addObject("sale", savedCartLineItem.getSale());
      modelAndView.addObject("isAddedToCart", DateValidators.validateTimestamp(savedCartLineItem.getInsertedAt()));
      return modelAndView;
   }

   @GetMapping(value = {"/delete/{cartId}", "/delete/{cartId}/"})
   public ModelAndView deleteCartById(@PathVariable("cartId") Long cartId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/" + APISuffixes.CART);
      CartLineItem cartToDelete = this.cartLineItemService.getCartLineItem(cartId);
      if (this.cartLineItemService.deleteCartLineItem(cartToDelete)) {
         modelAndView.addObject("cartDeletedSuccess", true);
      } else {
         modelAndView.addObject("cartNotDeletedError", true);
      }
      return modelAndView;
   }
}
