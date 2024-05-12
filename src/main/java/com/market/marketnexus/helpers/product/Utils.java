package com.market.marketnexus.helpers.product;

import com.market.marketnexus.helpers.constants.ProjectPaths;
import com.market.marketnexus.model.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Utils {

   public final static String PRODUCT_IMAGES_DIRECTORY = "/products";
   public final static String PRODUCT_IMAGE_EXTENSION = ".jpeg";

   public static @NonNull String getProductRelativeImageDirectory(@NonNull Product product) {
      return Utils.PRODUCT_IMAGES_DIRECTORY + '/' + product.getId().toString();
   }

   public static @NonNull String getProductRelativeImageFile(@NonNull Product product) {
      return product.getName().toLowerCase() + Utils.PRODUCT_IMAGE_EXTENSION;
   }

   public static @NotNull Boolean storeProductImage(Product product, @NonNull MultipartFile productImage) {
      if (!productImage.isEmpty()) {
         try {
            String destinationDirectory = ProjectPaths.getStaticPath() + product.getImageRelativePath().replace(product.getName().toLowerCase(), "").replace(Utils.PRODUCT_IMAGE_EXTENSION, "");
            System.out.println(destinationDirectory);
            File directory = new File(destinationDirectory);
            if (directory.mkdir()) {
               File file = new File(destinationDirectory + product.getName().toLowerCase() + Utils.PRODUCT_IMAGE_EXTENSION);
               productImage.transferTo(file);
               return productImage.getResource().exists() && file.exists(); //
            } else {
               System.err.println("Directory for the new Product inserted (id = " + product.getId().toString() + ")" + " not created, file cannot be stored.");
               return false;
            }
         } catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
         }
      } else {
         System.err.println("The file " + productImage.getName() + " is empty and it cannot be stored.");
         return false;
      }
   }
}
