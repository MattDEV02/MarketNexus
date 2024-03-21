package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.controller.validator.ProductValidator;
import com.lambert.lambertecommerce.helpers.constants.ControllerSuffixes;
import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.Product;
import com.lambert.lambertecommerce.model.Sale;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/" + ControllerSuffixes.DASHBOARD)
public class DashboardController {

   private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);
   @Autowired
   private ProductValidator productValidator;
   @Autowired
   private ProductService productService;


   @GetMapping
   public ModelAndView showAdminIndex(@ModelAttribute("fieldSizes") Map<String, Integer> fieldSizes) {
      ModelAndView modelAndView = new ModelAndView(ControllerSuffixes.DASHBOARD + "/index.html");
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      System.out.println(authentication.toString());
      modelAndView.addObject("products", this.productService.getAllProducts());
      modelAndView.addObject("fieldSizes", fieldSizes);
      return modelAndView;
   }

   @GetMapping(value = "/searchProduct")
   public ModelAndView searchProduct(@Valid @ModelAttribute("product") Product product,
                                     BindingResult productBindingResult) {
      ModelAndView modelAndView = new ModelAndView(ControllerSuffixes.DASHBOARD + "/product.html");

      return modelAndView;
   }

   @GetMapping(value = "/account")
   public ModelAndView showAccount(@ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(ControllerSuffixes.DASHBOARD + "/account.html");
      Set<Product> sellingProducts = this.productService.findAllProductSellingByUser(loggedUser);
      Set<Product> orderedProducts = this.productService.findAllProductOrderByUser(loggedUser);
      modelAndView.addObject("salesProducts", sellingProducts);
      modelAndView.addObject("orderedProducts", orderedProducts);
      return modelAndView;
   }

   @GetMapping(value = "/cart")
   public ModelAndView showCart(@ModelAttribute("loggedUser") User loggedUser, @ModelAttribute("loggedCredentials") Credentials loggedCredentials) {
      ModelAndView modelAndView = new ModelAndView(ControllerSuffixes.DASHBOARD + "/cart.html");
      Set<Product> cartProducts = this.productService.findAllProductCartByUser(loggedUser);
      modelAndView.addObject("cartProducts", cartProducts);
      return modelAndView;
   }

   @GetMapping(value = "/stats")
   public ModelAndView showStats() {
      ModelAndView modelAndView = new ModelAndView(ControllerSuffixes.DASHBOARD + "/stats.html");
      return modelAndView;
   }

   @GetMapping(value = "/newSale")
   public ModelAndView formNewProduct() {
      ModelAndView modelAndView = new ModelAndView(ControllerSuffixes.DASHBOARD + "/newSale.html");
      modelAndView.addObject("sale", new Sale());
      modelAndView.addObject("product", new Product());
      return modelAndView;
   }

   @PostMapping("/publishNewSale")
   public ModelAndView newProduct(@Valid @ModelAttribute("product") Product product,
                                  BindingResult productBindingResult) {
      final String publishSuccessful = "dashboard/product/index.html";
      final String publishError = "dashboard/newProduct.html";
      ModelAndView modelAndView = new ModelAndView();
      this.productValidator.validate(product, productBindingResult);
      if (!productBindingResult.hasErrors()) {
         this.productService.saveProduct(product);
         modelAndView.addObject("product", product);
         modelAndView.setViewName(publishSuccessful);
      } else {
         modelAndView.addObject("errorMessage", "Product not valid, retry.");
         modelAndView.setViewName(publishError);
      }
      return modelAndView;
   }

   /*
   @GetMapping("/product/{id}")
   public ModelAndView getProductById(@PathVariable("id") Long id) {
      ModelAndView modelAndView = new ModelAndView("dashboard/product/index.html");
      modelAndView.addObject("product", this.productService.findById(id));
      return modelAndView;
   }

   @GetMapping("/artist")
   public ModelAndView getArtists() {
      ModelAndView modelAndView = new ModelAndView("dashboard/index.html");
      modelAndView.addObject("products", this.productService.findAll());
      return modelAndView;
   }
   */

}
