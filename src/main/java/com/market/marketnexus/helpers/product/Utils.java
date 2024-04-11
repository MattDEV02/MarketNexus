package com.market.marketnexus.helpers.product;

import com.market.marketnexus.helpers.constants.Paths;
import com.market.marketnexus.model.Product;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Utils {

   public final static String productImagesDirectory = "/products";
   public final static String productImageExtension = ".jpeg";

   public static @NonNull String getProductRelativeImageDirectory(@NonNull Product product) {
      return Utils.productImagesDirectory + '/' + product.getId().toString();
   }

   public static @NonNull String getProductRelativeImageFile(@NonNull Product product) {
      return product.getName().toLowerCase() + Utils.productImageExtension;
   }

   public static boolean storeProductImage(Product product, @NonNull MultipartFile productImage) {
      if (!productImage.isEmpty()) {
         try {
            String destinationDirectory = Paths.getImagesPath() + Utils.getProductRelativeImageDirectory(product);
            File directory = new File(destinationDirectory);
            if (directory.mkdir()) {
               String destinationFilePath = destinationDirectory + "/" + Utils.getProductRelativeImageFile(product);
               File file = new File(destinationFilePath);
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
