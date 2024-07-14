package com.market.marketnexus.controller.validator;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.product.ProductImageFileUtils;
import com.market.marketnexus.model.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProductValidator implements Validator {

   private MultipartFile[] productImages;
   private Boolean isUpdate = false;

   private static @NotNull Boolean productImagesContainsEmptyFile(MultipartFile @NotNull [] productImages) {
      for (MultipartFile productImage : productImages) {
         if (productImage == null || productImage.isEmpty()) {
            return true;
         }
      }
      return false;
   }

   private static @NotNull Boolean productImagesContainsExceedSizeFile(MultipartFile @NotNull [] productImages) {
      for (MultipartFile productImage : productImages) {
         if (productImage.getSize() > FieldSizes.PRODUCT_IMAGE_MAX_BYTE_SIZE) {
            return true;
         }
      }
      return false;
   }

   private static @NotNull Boolean productImagesContainsWrongExtensionFile(MultipartFile @NotNull [] productImages) {
      for (MultipartFile productImage : productImages) {
         String originalFilename = productImage.getOriginalFilename();
         if (originalFilename == null || (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png") && !originalFilename.endsWith(ProductImageFileUtils.PRODUCT_IMAGE_EXTENSION))) {
            return true;
         }
      }
      return false;
   }

   public MultipartFile[] getProductImages() {
      return this.productImages;
   }

   public void setProductImages(MultipartFile[] productImage) {
      this.productImages = productImage;
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
     /* if (!FieldValidators.productNameValidator(product.getName())) {
         errors.rejectValue("name", "product.name.invalidFormat");
      }*/
      if (product.getCategory() == null) {
         errors.rejectValue("category", "product.category.notExists");
      }
      if (!this.getIsUpdate()) {
         if (ProductValidator.productImagesContainsEmptyFile(this.getProductImages())) {
            errors.reject("productImageEmptyError", "Invalid or empty product image.");
         }
         if (ProductValidator.productImagesContainsExceedSizeFile(this.getProductImages())) {
            errors.reject("productImageSizeError", "File size exceeds the allowed limit of " + (FieldSizes.PRODUCT_IMAGE_MAX_BYTE_SIZE / 1000) + " MB.");
         }
         if (ProductValidator.productImagesContainsWrongExtensionFile(this.getProductImages())) {
            errors.reject("productImageExtensionError", "Unsupported file type. Please upload a .jpg, .jpeg or .png file.");
         }
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Product.class.equals(aClass);
   }
}
