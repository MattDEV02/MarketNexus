package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APISuffixes;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/" + APISuffixes.ORDER)
public class OrderController {

   @Autowired
   private OrderService orderService;

   @GetMapping(value = {"", "/"})
   public ModelAndView makeOrderFromCartLineItem(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(APISuffixes.DASHBOARD + "/order.html");
      return modelAndView;
   }

}
