package com.lambert.lambertecommerce.helpers.products;

import com.lambert.lambertecommerce.helpers.constants.ProjectPaths;
import com.lambert.lambertecommerce.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Utils {

   public final static String productImagesDirectory = "/products";
   public final static String productImageExtension = ".jpeg";

   public static String getProductRelativeImageDirectory(Product product) {
      return Utils.productImagesDirectory + '/' + product.getId().toString();
   }

   public static String getProductRelativeImageFile(Product product) {
      return product.getName().toLowerCase() + Utils.productImageExtension;
   }

   public static boolean storeProductImage(Product product, MultipartFile productImage) {
      if (!productImage.isEmpty()) {
         try {
            String destinationDirectory = ProjectPaths.getImagesPath() + Utils.getProductRelativeImageDirectory(product);
            File directory = new File(destinationDirectory);
            if (directory.mkdir()) {
               String destinationFilePath = destinationDirectory + "/" + Utils.getProductRelativeImageFile(product);
               File file = new File(destinationFilePath);
               productImage.transferTo(file); // Salva il file sul filesystem del server
               return true;
            } else {
               System.err.println("Directory for the new Product inserted (id = " + product.getId().toString() + ")" + " not created, file cannot be stored.");
               return false;
            }
         } catch (IOException e) {
            e.printStackTrace();
            return false;
         }
      } else {
         System.err.println("The file " + productImage.getName() + " is empty and it cannot be stored.");
         return false;
      }
   }
}
