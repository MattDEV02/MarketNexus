package com.lambert.lambertecommerce.controller.validator;

import com.lambert.lambertecommerce.model.Product;
import com.lambert.lambertecommerce.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {

   private ProductRepository productRepository;

   @Override

   public void validate(Object o, Errors errors) {
      Product product = (Product) o;
      if (true) { // verificare se già esiste
         errors.reject("Duplicated Product.");
      }
   }

   @Override
   public boolean supports(Class<?> aClass) {
      return Product.class.equals(aClass);
   }
}
