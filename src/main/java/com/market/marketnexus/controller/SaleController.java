package com.market.marketnexus.controller;

import com.market.marketnexus.controller.validator.ProductValidator;
import com.market.marketnexus.exception.SaleNotFoundException;
import com.market.marketnexus.helpers.constants.APIPrefixes;
import com.market.marketnexus.helpers.constants.GlobalErrorsMessages;
import com.market.marketnexus.helpers.product.Utils;
import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.CredentialsService;
import com.market.marketnexus.service.ProductCategoryService;
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

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(value = "/" + APIPrefixes.DASHBOARD)
public class SaleController {

   public final static String PUBLISH_SUCCESSFUL = "redirect:/" + APIPrefixes.SALE + "/";
   public final static String PUBLISH_ERROR = APIPrefixes.DASHBOARD + "/newSale.html";

   private static final Logger LOGGER = LoggerFactory.getLogger(SaleController.class);
   @Autowired
   private ProductCategoryService productCategoryService;
   @Autowired
   private ProductValidator productValidator;
   @Autowired
   private ProductService productService;
   @Autowired
   private SaleService saleService;
   @Autowired
   private CredentialsService credentialsService;

   @GetMapping(value = {"", "/"})
   public ModelAndView showAllSales() {
      ModelAndView modelAndView = new ModelAndView(APIPrefixes.DASHBOARD + "/index.html");
      Set<Sale> allSales = this.saleService.getAllSales();
      modelAndView.addObject("sales", allSales);
      modelAndView.addObject("hasSearchedSales", false);
      return modelAndView;
   }

   @GetMapping(value = {"/searchProducts", "/searchProducts/"})
   public ModelAndView searchSales(@NonNull HttpServletRequest request, @Valid @ModelAttribute("loggedUser") User loggedUser) {
      String productName = request.getParameter("product-name");
      String productCategoryId = request.getParameter("category");
      SaleController.LOGGER.info("User username: {} has searched sale with product name: {} and product category ID: {}", loggedUser.getCredentials().getUsername(), productName, productCategoryId);
      ModelAndView modelAndView = new ModelAndView(APIPrefixes.DASHBOARD + "/index.html");
      Set<Sale> searchedSales = null;
      if (productName.isEmpty() && productCategoryId.isEmpty()) {
         searchedSales = this.saleService.getAllSales();
      } else if (productCategoryId.isEmpty()) {
         searchedSales = this.saleService.getAllSalesByProductName(productName);
      } else {
         Long longProductCategoryId = Long.parseLong(productCategoryId);
         modelAndView.addObject("searchedProductCategoryName", this.productCategoryService.getProductCategory(longProductCategoryId).getName());
         if (productName.isEmpty()) {
            searchedSales = this.saleService.getAllSalesByProductCategoryId(longProductCategoryId);
         } else {
            searchedSales = this.saleService.getAllSalesByProductNameAndProductCategoryId(productName, longProductCategoryId);
         }
      }
      modelAndView.addObject("sales", searchedSales);
      modelAndView.addObject("searchedProductName", productName);
      modelAndView.addObject("hasSearchedSales", true);
      return modelAndView;
   }

   @GetMapping(value = {"/newSale", "/newSale/"})
   public ModelAndView showNewSaleForm() {
      ModelAndView modelAndView = new ModelAndView(APIPrefixes.DASHBOARD + "/newSale.html");
      modelAndView.addObject("sale", new Sale());
      modelAndView.addObject("product", new Product());
      return modelAndView;
   }

   @PostMapping(value = {"/publishNewSale", "/publishNewSale/"})
   public ModelAndView publishNewSale(
           @ModelAttribute("loggedUser") User loggedUser,
           @Valid @ModelAttribute("product") Product product,
           BindingResult productBindingResult,
           @Valid @ModelAttribute("sale") Sale sale,
           BindingResult saleBindingResult,
           @RequestParam("product-image") MultipartFile productImage) {
      ModelAndView modelAndView = new ModelAndView(SaleController.PUBLISH_ERROR);
      if (!this.credentialsService.areSellerCredentials(loggedUser.getCredentials())) {
         modelAndView.addObject("userNotSellerPublishedASaleError", true);
         SaleController.LOGGER.error(GlobalErrorsMessages.USER_NOT_SELLER_PUBLISHED_A_SALE_ERROR);
         return modelAndView;
      }
      this.productValidator.setProductImage(productImage);
      this.productValidator.validate(product, productBindingResult);
      if (!productBindingResult.hasErrors() && !saleBindingResult.hasErrors()) {
         Product savedProduct = this.productService.saveProduct(product);
         if (Utils.storeProductImage(savedProduct, productImage)) {
            Sale savedSale = this.saleService.saveSale(sale, loggedUser, savedProduct);
            modelAndView.setViewName(SaleController.PUBLISH_SUCCESSFUL + savedProduct.getId().toString());
            modelAndView.addObject("sale", savedSale);
            SaleController.LOGGER.info("Published new Sale with ID: {}", savedSale.getId());
         } else {
            SaleController.LOGGER.error(GlobalErrorsMessages.SALE_NOT_PUBLISHED_ERROR);
            modelAndView.addObject("saleNotPublishedError", true);
         }
      } else {
         List<ObjectError> productGlobalErrors = productBindingResult.getGlobalErrors();
         for (ObjectError productGlobalError : productGlobalErrors) {
            modelAndView.addObject(Objects.requireNonNull(productGlobalError.getCode()), productGlobalError.getDefaultMessage());
         }
         List<ObjectError> saleGlobalErrors = saleBindingResult.getGlobalErrors();
         for (ObjectError saleGlobalError : saleGlobalErrors) {
            modelAndView.addObject(Objects.requireNonNull(saleGlobalError.getCode()), saleGlobalError.getDefaultMessage());
         }
      }
      return modelAndView;
   }

   @GetMapping(value = {"/sale/{saleId}", "/sale/{saleId}/"})
   public ModelAndView showSaleById(@PathVariable("saleId") Long saleId) {
      ModelAndView modelAndView = new ModelAndView(APIPrefixes.DASHBOARD + "/sale.html");
      try {
         Sale sale = this.saleService.getSale(saleId);
         modelAndView.addObject("saleId", saleId);
         modelAndView.addObject("sale", sale);
         modelAndView.addObject("isAddedToCart", false);
      } catch (SaleNotFoundException saleNotFoundException) {
         SaleController.LOGGER.error(saleNotFoundException.getMessage());
      }
      return modelAndView;
   }

}