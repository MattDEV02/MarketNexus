package com.lambert.lambertecommerce.helpers.constants;

import org.springframework.core.io.FileSystemResource;

public class ProjectPaths {

   public final static String root = new FileSystemResource("").getFile().getAbsolutePath();

   public final static String src = "/src/main";

   public final static String resources = "/resources";

   public final static String _static = "/static";

   public final static String images = "/images";

   public static String getStaticPath() {
      return ProjectPaths.root + ProjectPaths.src + ProjectPaths.resources + ProjectPaths._static;
   }

   public static String getImagesPath() {
      return ProjectPaths.getStaticPath() + ProjectPaths.images;
   }

}
