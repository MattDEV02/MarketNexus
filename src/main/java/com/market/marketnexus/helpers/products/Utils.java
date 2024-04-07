package com.market.marketnexus.helpers.products;

import com.market.marketnexus.helpers.constants.ProjectPaths;
import com.market.marketnexus.model.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Utils {

   public final static String productImagesDirectory = "/products";
   public final static String productImageExtension = ".jpeg";

   public static @NotNull String getProductRelativeImageDirectory(@NotNull Product product) {
      return Utils.productImagesDirectory + '/' + product.getId().toString();
   }

   public static @NotNull String getProductRelativeImageFile(@NotNull Product product) {
      return product.getName().toLowerCase() + Utils.productImageExtension;
   }

   public static boolean storeProductImage(Product product, @NotNull MultipartFile productImage) {
      if (!productImage.isEmpty()) {
         try {
            String destinationDirectory = ProjectPaths.getImagesPath() + Utils.getProductRelativeImageDirectory(product);
            File directory = new File(destinationDirectory);
            if (directory.mkdir()) {
               String destinationFilePath = destinationDirectory + "/" + Utils.getProductRelativeImageFile(product);
               File file = new File(destinationFilePath);
               productImage.transferTo(file); // Salva il file sul filesystem del server
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
