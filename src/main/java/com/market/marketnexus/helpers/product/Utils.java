package com.market.marketnexus.helpers.product;

import com.market.marketnexus.helpers.constants.ProjectPaths;
import com.market.marketnexus.model.Product;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

   public static void storeProductImage(@NonNull Product product, @NonNull MultipartFile productImage, Boolean targetFlag) {
      try {
         String productImageRelativePath = product.getImageRelativePath();
         Integer indexOfProductImageFileName = productImageRelativePath.indexOf(Utils.getProductImageFileName(product));
         String productImageRelativePathDirectory = productImageRelativePath.substring(0, indexOfProductImageFileName);
         String destinationDirectoryName = targetFlag ? ProjectPaths.getTargetStaticPath() : ProjectPaths.getStaticPath() + productImageRelativePathDirectory;
         File destinationDirectory = new File(destinationDirectoryName);
         FileUtils.forceMkdir(destinationDirectory);
         String destinationFileName = productImageRelativePath.substring(indexOfProductImageFileName);
         Path fileOutput = Paths.get(destinationDirectoryName + destinationFileName);
         Files.copy(productImage.getInputStream(), fileOutput, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }

   public static void storeProductImage(@NonNull Product product, @NonNull MultipartFile productImage) {
      // /images/products/{productId}/{productName}.jpeg
      Utils.storeProductImage(product, productImage, false);
      Utils.storeProductImage(product, productImage, true);
   }

   public static void deleteProductImageDirectory(@NotNull Product product) {
      String productImageDirectoryName = Utils.getProductImageDirectoryName(product);
      File productImageDirectory = new File(ProjectPaths.getStaticPath() + productImageDirectoryName);
      File productImageDirectoryTarget = new File(ProjectPaths.getTargetStaticPath() + productImageDirectoryName);
      try {
         FileUtils.deleteDirectory(productImageDirectory);
         FileUtils.deleteDirectory(productImageDirectoryTarget);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }
}
