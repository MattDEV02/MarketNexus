package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APISuffixes;
import com.market.marketnexus.helpers.validators.DateValidators;
import com.market.marketnexus.model.Cart;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CartService;
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
   private CartService cartService;

   @GetMapping(value = {"", "/"})
   public ModelAndView showCart(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(APISuffixes.CART + ".html");
      Set<Cart> cartProducts = this.cartService.getAllCartsByUser(loggedUser);
      Float cartTotalPrice = this.cartService.getTotalCartPriceByUser(loggedUser);
      modelAndView.addObject("cartProducts", cartProducts);
      modelAndView.addObject("cartTotalPrice", cartTotalPrice);
      return modelAndView;
   }

   @GetMapping(value = {"/{saleId}", "/{saleId}/"})
   public ModelAndView addSaleProductToCartById(@Valid @ModelAttribute("loggedUser") User loggedUser, @PathVariable("saleId") Long saleId, @RequestParam("quantity") Integer quantity) {
      ModelAndView modelAndView = new ModelAndView(APISuffixes.SALE + ".html");
      Sale sale = this.saleService.getSale(saleId);
      Cart cart = new Cart(sale, loggedUser, quantity);
      Cart savedCart = this.cartService.saveCart(cart);
      modelAndView.addObject("sale", savedCart.getSale());
      modelAndView.addObject("isAddedToCart", DateValidators.validateTimestamp(savedCart.getInsertedAt()));
      return modelAndView;
   }

   @GetMapping(value = {"/delete/{cartId}", "/delete/{cartId}/"})
   public ModelAndView deleteCartById(@PathVariable("cartId") Long cartId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/" + APISuffixes.CART);
      Cart cartToDelete = this.cartService.getCart(cartId);
      if (!this.cartService.deleteCart(cartToDelete)) {
         modelAndView.addObject("cartNotDeletedError", true);
      }
      return modelAndView;
   }
}
