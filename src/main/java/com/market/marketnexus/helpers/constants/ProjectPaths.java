package com.market.marketnexus.helpers.constants;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.FileSystemResource;

public class ProjectPaths {

   public final static String ROOT = new FileSystemResource("").getFile().getAbsolutePath();

   public final static String SRC = "/src/main";

   public final static String RESOURCES = "/resources";

   public final static String _STATIC = "/static";

   public final static String IMAGES = "/images";

   @Contract(pure = true)
   public static @NotNull String getStaticPath() {
      return ProjectPaths.ROOT + ProjectPaths.SRC + ProjectPaths.RESOURCES + ProjectPaths._STATIC;
   }

   @Contract(pure = true)
   public static @NotNull String getResourcesPath() {
      return ProjectPaths.getStaticPath() + ProjectPaths.IMAGES;
   }

   @Contract(pure = true)
   public static @NotNull String getImagesPath() {
      return ProjectPaths.getStaticPath() + ProjectPaths.IMAGES;
   }

}
