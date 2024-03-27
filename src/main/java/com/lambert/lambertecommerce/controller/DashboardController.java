package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.controller.validator.ProductValidator;
import com.lambert.lambertecommerce.helpers.constants.ControllerSuffixes;
import com.lambert.lambertecommerce.helpers.products.Utils;
import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.Product;
import com.lambert.lambertecommerce.model.Sale;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.ProductService;
import com.lambert.lambertecommerce.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/" + ControllerSuffixes.DASHBOARD)
public class DashboardController {

   private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);
   @Autowired
   private ProductValidator productValidator;
   @Autowired
   private ProductService productService;
   @Autowired
   private SaleService saleService;


   @GetMapping
   public ModelAndView showAdminIndex() {
      ModelAndView modelAndView = new ModelAndView(ControllerSuffixes.DASHBOARD + "/index.html");
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      System.out.println(authentication.toString());
      modelAndView.addObject("products", this.productService.getAllProducts());
      return modelAndView;
   }

   @GetMapping(value = "/searchProducts")
   public ModelAndView searchProduct(HttpServletRequest request) {
      String productName = request.getParameter("product-name");
      String productCategoryId = request.getParameter("category");
      ModelAndView modelAndView = new ModelAndView(ControllerSuffixes.DASHBOARD + "/index.html");
      Set<Product> products = null;
      if (productName.isEmpty() && productCategoryId.isEmpty()) {
         products = this.productService.getAllProducts();
      } else if (productCategoryId.isEmpty()) {
         products = this.productService.findAllByName(productName);
      } else {
         Long longProductCategoryId = Long.parseLong(productCategoryId);
         if (productName.isEmpty()) {
            products = this.productService.findAllByCategory(longProductCategoryId);
         } else {
            products = this.productService.findAllByNameAndCategory(productName, longProductCategoryId);
         }
      }
      modelAndView.addObject("products", products);
      return modelAndView;
   }

   @GetMapping(value = "/account")
   public ModelAndView showAccount(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(ControllerSuffixes.DASHBOARD + "/account.html");
      Set<Product> saleProducts = this.productService.findAllProductSaleByUser(loggedUser);
      Set<Product> orderedProducts = this.productService.findAllProductOrderByUser(loggedUser);
      modelAndView.addObject("salesProducts", saleProducts);
      modelAndView.addObject("orderedProducts", orderedProducts);
      return modelAndView;
   }

   @GetMapping(value = "/cart")
   public ModelAndView showCart(@Valid @ModelAttribute("loggedUser") User loggedUser, @Valid @ModelAttribute("loggedCredentials") Credentials loggedCredentials) {
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
   public ModelAndView newProduct(
           @Valid @ModelAttribute("loggedUser") User loggedUser,
           @ModelAttribute("product") Product product,
           BindingResult productBindingResult,
           @Valid @ModelAttribute("sale") Sale sale,
           BindingResult saleBindingResult,
           @RequestParam("product-image") MultipartFile productImage) {
      System.out.println(productBindingResult);
      System.out.println(saleBindingResult);
      final String publishSuccessful = "redirect:/" + ControllerSuffixes.DASHBOARD + "/product/";
      final String publishError = ControllerSuffixes.DASHBOARD + "/newSale.html";
      ModelAndView modelAndView = new ModelAndView();
      this.productValidator.setProductImage(productImage);
      this.productValidator.validate(product, productBindingResult);
      if (!productBindingResult.hasErrors() && !saleBindingResult.hasErrors()) {
         Product savedProduct = this.productService.saveProduct(product);
         Sale savedSale = this.saleService.saveSale(sale, loggedUser, savedProduct);
         if (Utils.storeProductImage(savedProduct, productImage)) {
            modelAndView.setViewName(publishSuccessful + savedProduct.getId().toString());
            modelAndView.addObject("product", savedProduct);
            modelAndView.addObject("sale", savedSale);
         }
      } else {
         System.out.println("error");
         System.out.println(productBindingResult);
         System.out.println(saleBindingResult);
         for (ObjectError error : productBindingResult.getGlobalErrors()) {
            modelAndView.addObject(error.getCode(), error.getDefaultMessage());
         }
         modelAndView.setViewName(publishError);
      }
      return modelAndView;
   }

   @GetMapping("/product/{id}")
   public ModelAndView getProductById(@PathVariable("id") Long id) {
      ModelAndView modelAndView = new ModelAndView(ControllerSuffixes.DASHBOARD + "/product.html");
      Product product = this.productService.findById(id);
      modelAndView.addObject("product", product);
      return modelAndView;
   }

}
