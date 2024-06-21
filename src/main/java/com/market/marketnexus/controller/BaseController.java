package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPrefixes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BaseController {

   private final static Map<String, String> FAQ_ACCORDIONS = new HashMap<>();

   static {
      FAQ_ACCORDIONS.put("How can I register in " + GlobalValues.APP_NAME + "?", "Simply ! Go to /registration and insert your data!");
      FAQ_ACCORDIONS.put("Is it possible to have 2 account?", "Yes it is possible but with 2 not equals emails and usernames!");
      FAQ_ACCORDIONS.put("Is it possible to navigate in offline-mode?", "No, unfortunately it is not possible.");
      FAQ_ACCORDIONS.put("Is it possible to have the same account on multiple devices?", "Yes, fortunately it is possible.");
      FAQ_ACCORDIONS.put("How many Product Categories there are in " + GlobalValues.APP_NAME + "?", "At this moment there are " + String.valueOf(9) + " Product Categories.");
      FAQ_ACCORDIONS.put("How should my password be made?", "Your " + GlobalValues.APP_NAME + " password must has 8 characters, uppercase, lowercase letters and numbers.");
      FAQ_ACCORDIONS.put("Where can I see my account?", "Easy! You can see your account at /" + APIPrefixes.ACCOUNT);
      FAQ_ACCORDIONS.put("Where is the repo of this beautiful site?", "Easy! You can find the Github repo of this beatiful site at: " + GlobalValues.APP_REPO);
      FAQ_ACCORDIONS.put("Who is the Author of this beautiful site?", "The Author of this beatiful site is " + GlobalValues.AUTHORS[0].substring(0, GlobalValues.AUTHORS[0].indexOf(",")) + ".");
   }

   @GetMapping(value = {"/"})
   public ModelAndView showHomePage() {
      return new ModelAndView("index.html");
   }

   @GetMapping(value = {"/FAQs", "/FAQs/"})
   public ModelAndView showFAQs() {
      ModelAndView modelAndView = new ModelAndView("FAQs.html");
      modelAndView.addObject("accordions", BaseController.FAQ_ACCORDIONS);
      return modelAndView;
   }
}
