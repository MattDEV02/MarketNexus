package com.market.marketnexus.helpers.constants;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.FileSystemResource;

public class Paths {

   public final static String ROOT = new FileSystemResource("").getFile().getAbsolutePath();

   public final static String SRC = "/src/main";

   public final static String RESOURCES = "/resources";

   public final static String _STATIC = "/static";

   public final static String IMAGES = "/images";

   @Contract(pure = true)
   public static @NotNull String getStaticPath() {
      return Paths.ROOT + Paths.SRC + Paths.RESOURCES + Paths._STATIC;
   }

   @Contract(pure = true)
   public static @NotNull String getResourcesPath() {
      return Paths.getStaticPath() + Paths.IMAGES;
   }

   @Contract(pure = true)
   public static @NotNull String getImagesPath() {
      return Paths.getStaticPath() + Paths.IMAGES;
   }

}
