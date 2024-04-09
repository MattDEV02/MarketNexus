package com.market.marketnexus.controller;

import com.market.marketnexus.controller.validator.ProductValidator;
import com.market.marketnexus.helpers.constants.APISuffixes;
import com.market.marketnexus.helpers.products.Utils;
import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.ProductService;
import com.market.marketnexus.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(value = "/" + APISuffixes.DASHBOARD)
public class SaleController {

   private static final Logger LOGGER = LoggerFactory.getLogger(SaleController.class);
   @Autowired
   private ProductValidator productValidator;
   @Autowired
   private ProductService productService;
   @Autowired
   private SaleService saleService;

   @GetMapping(value = {"", "/"})
   public ModelAndView showAllSales() {
      ModelAndView modelAndView = new ModelAndView(APISuffixes.DASHBOARD + "/index.html");
      Set<Sale> allSales = this.saleService.getAllSales();
      modelAndView.addObject("sales", allSales);
      return modelAndView;
   }

   @GetMapping(value = {"/searchProducts", "/searchProducts/"})
   public ModelAndView searchSales(@NonNull HttpServletRequest request) {
      String productName = request.getParameter("product-name");
      String productCategoryId = request.getParameter("category");
      ModelAndView modelAndView = new ModelAndView(APISuffixes.DASHBOARD + "/index.html");
      Set<Sale> searchedSales = null;
      if (productName.isEmpty() && productCategoryId.isEmpty()) {
         searchedSales = this.saleService.getAllSales();
      } else if (productCategoryId.isEmpty()) {
         searchedSales = this.saleService.getAllSalesByProductName(productName);
      } else {
         Long longProductCategoryId = Long.parseLong(productCategoryId);
         if (productName.isEmpty()) {
            searchedSales = this.saleService.getAllSalesByProductCategoryId(longProductCategoryId);
         } else {
            searchedSales = this.saleService.getAllSalesByProductNameAndProductCategoryId(productName, longProductCategoryId);
         }
      }
      modelAndView.addObject("sales", searchedSales);
      return modelAndView;
   }

   @GetMapping(value = {"/newSale", "/newSale/"})
   public ModelAndView showNewSaleForm() {
      ModelAndView modelAndView = new ModelAndView(APISuffixes.DASHBOARD + "/newSale.html");
      modelAndView.addObject("sale", new Sale());
      modelAndView.addObject("product", new Product());
      return modelAndView;
   }

   @PostMapping(value = {"/publishNewSale", "/publishNewSale/"})
   public ModelAndView publishNewSale(
           @Valid @ModelAttribute("loggedUser") User loggedUser,
           @Valid @ModelAttribute("product") Product product,
           BindingResult productBindingResult,
           @Valid @ModelAttribute("sale") Sale sale,
           BindingResult saleBindingResult,
           @RequestParam("product-image") MultipartFile productImage) {
      System.out.println(productBindingResult);
      System.out.println(saleBindingResult);
      final String publishSuccessful = "redirect:/" + APISuffixes.SALE + "/";
      final String publishError = APISuffixes.DASHBOARD + "/newSale.html";
      ModelAndView modelAndView = new ModelAndView(publishError);
      this.productValidator.setProductImage(productImage);
      this.productValidator.validate(product, productBindingResult);
      if (!productBindingResult.hasErrors() && !saleBindingResult.hasErrors()) {
         Product savedProduct = this.productService.saveProduct(product);
         Sale savedSale = this.saleService.saveSale(sale, loggedUser, savedProduct);
         if (Utils.storeProductImage(savedProduct, productImage)) {
            modelAndView.setViewName(publishSuccessful + savedProduct.getId().toString());
            modelAndView.addObject("sale", savedSale);
         }
      } else {
         for (ObjectError error : productBindingResult.getGlobalErrors()) {
            modelAndView.addObject(Objects.requireNonNull(error.getCode()), error.getDefaultMessage());
         }
      }
      return modelAndView;
   }

   @GetMapping(value = {"/sale/{saleId}", "/sale/{saleId}/"})
   public ModelAndView showSaleById(@PathVariable("saleId") Long saleId) {
      ModelAndView modelAndView = new ModelAndView(APISuffixes.DASHBOARD + "/sale.html");
      Sale sale = this.saleService.getSale(saleId);
      modelAndView.addObject("sale", sale);
      modelAndView.addObject("isAddedToCart", false);
      return modelAndView;
   }


}
