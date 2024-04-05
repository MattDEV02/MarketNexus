package com.lambert.lambertecommerce.controller;

import com.lambert.lambertecommerce.helpers.constants.PathSuffixes;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/" + PathSuffixes.ORDER)
public class OrderController {

   @Autowired
   private OrderService orderService;

   @GetMapping(value = "")
   public ModelAndView makeOrderFromCart(@Valid @ModelAttribute("loggedUser") User loggedUser) {
      ModelAndView modelAndView = new ModelAndView(PathSuffixes.DASHBOARD + "/order.html");
      return modelAndView;
   }

}
