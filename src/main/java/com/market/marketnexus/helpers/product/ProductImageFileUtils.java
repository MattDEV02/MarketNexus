package com.market.marketnexus.helpers.product;

import com.market.marketnexus.helpers.constants.ProjectPaths;
import com.market.marketnexus.model.Product;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class ProductImageFileUtils {

   public final static String PRODUCT_IMAGES_DIRECTORY = "/products";
   public final static String PRODUCT_IMAGE_EXTENSION = ".jpeg";

   public static @NonNull String getProductImageDirectoryName(@NonNull Product product) {
      return ProjectPaths.IMAGES + ProductImageFileUtils.PRODUCT_IMAGES_DIRECTORY + "/" + product.getId().toString();
   }

   public static @NonNull String getProductImageFileName(Integer index) {
      return String.valueOf(index + 1) + ProductImageFileUtils.PRODUCT_IMAGE_EXTENSION;
   }

   public static @NonNull String getProductImagePath(@NonNull Product product, Integer index) {
      return ProductImageFileUtils.getProductImageDirectoryName(product) + "/" + ProductImageFileUtils.getProductImageFileName(index);
   }

   public static void storeProductImage(@NonNull Product product, @NonNull MultipartFile productImage, Integer index, Boolean targetFlag) {
      try {
         String productImageRelativePath = product.getImageRelativePaths().get(index);
         Integer indexOfProductImageFileName = productImageRelativePath.indexOf(ProductImageFileUtils.getProductImageFileName(index));
         String productImageRelativePathDirectory = productImageRelativePath.substring(0, indexOfProductImageFileName);
         String staticDestinationName = targetFlag ? ProjectPaths.getTargetStaticPath() : ProjectPaths.getStaticPath();
         String destinationDirectoryName = staticDestinationName + productImageRelativePathDirectory;
         File destinationDirectory = new File(destinationDirectoryName);
         FileUtils.forceMkdir(destinationDirectory);
         String destinationFileName = productImageRelativePath.substring(indexOfProductImageFileName);
         Path fileOutput = Paths.get(destinationDirectoryName + destinationFileName);
         Files.copy(productImage.getInputStream(), fileOutput, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }

   public static void storeProductImage(@NonNull Product product, @NonNull MultipartFile productImage, Integer index) {
      // /images/products/{productId}/{productImageIndex + 1}.jpeg
      ProductImageFileUtils.storeProductImage(product, productImage, index, false);
      ProductImageFileUtils.storeProductImage(product, productImage, index, true);
   }

   public static void deleteProductImageDirectory(@NotNull Product product) {
      String productImageDirectoryName = ProductImageFileUtils.getProductImageDirectoryName(product);
      File productImageDirectory = new File(ProjectPaths.getStaticPath() + productImageDirectoryName);
      File productImageDirectoryTarget = new File(ProjectPaths.getTargetStaticPath() + productImageDirectoryName);
      try {
         FileUtils.deleteDirectory(productImageDirectory);
         FileUtils.deleteDirectory(productImageDirectoryTarget);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }

   public static void deleteProductImages(@NotNull Product product) {
      ProductImageFileUtils.deleteProductImages(product, false);
      ProductImageFileUtils.deleteProductImages(product, true);
   }

   public static void deleteProductImages(@NotNull Product product, Boolean targetFlag) {
      String ricettaImmagineDirectoryName = ProductImageFileUtils.getProductImageDirectoryName(product);
      String staticDestinationName = targetFlag ? ProjectPaths.getTargetStaticPath() : ProjectPaths.getStaticPath();
      Path ricettaImmagineDirectoryPath = Paths.get(staticDestinationName + ricettaImmagineDirectoryName);
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(ricettaImmagineDirectoryPath)) {
         for (Path filePath : stream) {
            if (Files.exists(filePath)) {
               FileUtils.forceDelete(filePath.toFile());
            }
         }
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }
}
