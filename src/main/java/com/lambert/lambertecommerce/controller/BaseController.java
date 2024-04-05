package com.lambert.lambertecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BaseController {

   private final static Map<String, String> ACCORDIONS = new HashMap<>();

   static {
      ACCORDIONS.put("Quanto fa 2+2 ?", "Fa 4!");
      ACCORDIONS.put("Quanto fa 5+7 ?", "Fa 12!");
      for (int i = 0; i < 10; i++) {
         ACCORDIONS.put("Quanto fa 3 + " + (i + 1) + " ?", "Fa " + (3 + i + 1) + " !");
      }
   }

   @GetMapping(value = "/")
   public ModelAndView showHomePage() {
      return new ModelAndView("index.html");
   }

   @GetMapping(value = "/FAQs")
   public ModelAndView showFAQs() {
      ModelAndView modelAndView = new ModelAndView("FAQs.html");
      modelAndView.addObject("accordions", BaseController.ACCORDIONS);
      return modelAndView;
   }
}
