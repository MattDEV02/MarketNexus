package com.lambert.lambertecommerce.controller.validator;

import com.lambert.lambertecommerce.helpers.validators.FieldValidators;
import com.lambert.lambertecommerce.model.Product;
import com.lambert.lambertecommerce.repository.ProductRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {

   private ProductRepository productRepository;

   @Override
   public void validate(@NonNull Object o, @NonNull Errors errors) {
      Product product = (Product) o;
      if (!FieldValidators.productNameValidator(product.getName())) {
         errors.reject("productNameFormatError", "Invalid product name format." + product.getName());
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Product.class.equals(aClass);
   }
}
