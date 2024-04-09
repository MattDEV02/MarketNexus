package com.market.marketnexus.helpers.constants;

import org.springframework.core.io.FileSystemResource;

public class Paths {

   public final static String root = new FileSystemResource("").getFile().getAbsolutePath();

   public final static String src = "/src/main";

   public final static String resources = "/resources";

   public final static String _static = "/static";

   public final static String images = "/images";

   public static String getStaticPath() {
      return Paths.root + Paths.src + Paths.resources + Paths._static;
   }

   public static String getImagesPath() {
      return Paths.getStaticPath() + Paths.images;
   }

}
