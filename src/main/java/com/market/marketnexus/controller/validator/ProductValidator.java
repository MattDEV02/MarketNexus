package com.market.marketnexus.controller.validator;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.product.Utils;
import com.market.marketnexus.helpers.validators.FieldValidators;
import com.market.marketnexus.model.Product;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProductValidator implements Validator {

   private MultipartFile productImage;
   private Boolean isUpdate = false;

   public MultipartFile getProductImage() {
      return this.productImage;
   }

   public void setProductImage(MultipartFile productImage) {
      this.productImage = productImage;
   }

   public Boolean getIsUpdate() {
      return this.isUpdate;
   }

   public void setIsUpdate(Boolean isUpdate) {
      this.isUpdate = isUpdate;
   }

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      Product product = (Product) object;
      if (!FieldValidators.productNameValidator(product.getName())) {
         errors.rejectValue("name", "product.name.invalidFormat");
      }
      if (product.getCategory() == null) {
         errors.rejectValue("category", "product.category.notExists");
      }
      if (!this.getIsUpdate() && (this.getProductImage() == null || this.getProductImage().isEmpty())) {
         System.out.println(this.getIsUpdate());
         System.out.println(this.getProductImage() == null);
         System.out.println(this.getProductImage().isEmpty());
         errors.reject("productImageEmptyError", "Invalid or empty product image.");
      } else if (!this.getIsUpdate()) {
         if (this.getProductImage().getSize() > FieldSizes.PRODUCT_IMAGE_MAX_BYTE_SIZE) {
            errors.reject("productImageSizeError", "File size exceeds the allowed limit of " + (FieldSizes.PRODUCT_IMAGE_MAX_BYTE_SIZE / 1000) + " MB.");
         }
         String originalFilename = this.getProductImage().getOriginalFilename();
         if (originalFilename == null || (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png") && !originalFilename.endsWith(Utils.PRODUCT_IMAGE_EXTENSION))) {
            errors.reject("productImageExtensionError", "Unsupported file type. Please upload a .jpg, .jpeg or .png file.");
         }
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Product.class.equals(aClass);
   }
}
