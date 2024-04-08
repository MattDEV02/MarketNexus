package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.Global;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BaseController {

   private final static Map<String, String> ACCORDIONS = new HashMap<>();

   static {
      // https://github.com/MattDEV02/LambertQuiz/blob/main/src/screens/helpScreen.js
      ACCORDIONS.put("How can I register in " + Global.APP_NAME + "?", "Response.");
      ACCORDIONS.put("Is it possible to have 2 account?", "Response.");
      ACCORDIONS.put("Is it possible to navigate in offline-mode?", "Response.");
      ACCORDIONS.put("Is it possible to have the same account on multiple devices?", "Response.");
      ACCORDIONS.put("How many Product Categories there are in " + Global.APP_NAME + "?", "Response.");
      ACCORDIONS.put("How should my password be made?", "Response.");
      ACCORDIONS.put("Where can I see my account?", "Response.");
      ACCORDIONS.put("Where is the repo of this beautiful site?", "Response.");
      ACCORDIONS.put("Who are the authors of this site?", "Response.");
   }

   @GetMapping(value = {"/"})
   public ModelAndView showHomePage() {
      return new ModelAndView("index.html");
   }

   @GetMapping(value = {"/FAQs", "/FAQs/"})
   public ModelAndView showFAQs() {
      ModelAndView modelAndView = new ModelAndView("FAQs.html");
      modelAndView.addObject("accordions", BaseController.ACCORDIONS);
      return modelAndView;
   }
}
