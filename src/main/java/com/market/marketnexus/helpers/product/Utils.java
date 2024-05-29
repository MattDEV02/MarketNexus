package com.market.marketnexus.helpers.product;

import com.market.marketnexus.helpers.constants.ProjectPaths;
import com.market.marketnexus.model.Product;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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
      return ProjectPaths.IMAGES + Utils.PRODUCT_IMAGES_DIRECTORY + "/" + product.getId().toString();
   }

   public static @NonNull String getProductImageFileName(@NonNull Product product) {
      return product.getId().toString() + Utils.PRODUCT_IMAGE_EXTENSION;
   }

   public static @NonNull String getProductImagePath(@NonNull Product product) {
      return Utils.getProductImageDirectoryName(product) + "/" + Utils.getProductImageFileName(product);
   }

   public static @NotNull Boolean storeProductImage(@NonNull Product product, @NonNull MultipartFile productImage) {
      // /images/products/{productId}/{productName}.jpeg
      try {
         String productImageRelativePath = product.getImageRelativePath();
         Integer indexOfProductImageFileName = productImageRelativePath.indexOf(Utils.getProductImageFileName(product));
         String productImageRelativePathDirectory = productImageRelativePath.substring(0, indexOfProductImageFileName);
         String destinationDirectoryName = ProjectPaths.getStaticPath() + productImageRelativePathDirectory;
         File destinationDirectory = new File(destinationDirectoryName);
         FileUtils.forceMkdir(destinationDirectory);
         String destinationFileName = productImageRelativePath.substring(indexOfProductImageFileName);
         File fileOutput = new File(destinationDirectoryName + destinationFileName);
         productImage.transferTo(fileOutput);
         return fileOutput.exists() && fileOutput.isFile();
      } catch (IOException iOException) {
         iOException.printStackTrace();
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
