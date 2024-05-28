package com.market.marketnexus.helpers.product;

import com.market.marketnexus.helpers.constants.ProjectPaths;
import com.market.marketnexus.model.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

   public final static String PRODUCT_IMAGES_DIRECTORY = "/products";
   public final static String PRODUCT_IMAGE_EXTENSION = ".jpeg";

   public static @NonNull String getProductImageDirectoryName(@NonNull Product product) {
      return Utils.PRODUCT_IMAGES_DIRECTORY + '/' + product.getId().toString();
   }

   public static @NonNull String getProductImageFileName(@NonNull Product product) {
      return product.getName().toLowerCase() + Utils.PRODUCT_IMAGE_EXTENSION;
   }

   public static @NonNull String getProductImagePath(@NonNull Product product) {
      return ProjectPaths.IMAGES + Utils.getProductImageDirectoryName(product) + "/" + Utils.getProductImageFileName(product);
   }

   public static @NotNull Boolean storeProductImage(@NonNull Product product, @NonNull MultipartFile productImage) {
      // /images/products/{productId}/{productName}.jpeg
      if (!productImage.isEmpty()) { //
         try {
            String productImageRelativePath = product.getImageRelativePath();
            String productImageRelativePathDirectory = productImageRelativePath.replace(Utils.getProductImageFileName(product), "");
            String destinationDirectoryName = ProjectPaths.getStaticPath() + productImageRelativePathDirectory;
            File destinationDirectory = new File(destinationDirectoryName);
            if (destinationDirectory.isDirectory() && destinationDirectory.mkdir()) {
               File file = new File(destinationDirectory + Utils.getProductImageFileName(product));
               productImage.transferTo(file);
               return file.exists() && file.isFile();
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

   public static @NonNull Boolean deleteProductImage(@NotNull Product product) {
      String productImageDirectoryName = Utils.getProductImageDirectoryName(product);
      Path directoryPath = Paths.get(ProjectPaths.getStaticPath() + productImageDirectoryName);
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
         for (Path filePath : stream) {
            if (Files.exists(filePath)) {
               Files.delete(filePath);
            }
            return false;
         }
         return true;
      } catch (IOException iOException) {
         iOException.printStackTrace();
         return false;
      }
   }
}
