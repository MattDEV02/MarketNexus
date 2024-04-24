package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPrefixes;
import com.market.marketnexus.model.*;
import com.market.marketnexus.service.SaleService;
import com.market.marketnexus.service.UserService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RequestMapping(value = "/" + APIPrefixes.STATS)
@RestController
public class StatsController {

   @Autowired
   private UserService userService;
   @Autowired
   private SaleService saleService;

   @GetMapping(value = {"/chartData", "/chartData/"})
   public List<Object[]> getChartData() {
      return this.saleService.countSalesByProductCategory();
   }

   @GetMapping(value = {"/mapData", "/mapData/"})
   public List<Object[]> getMapData() {
      return this.userService.countUsersByNation();
   }

   @GetMapping(value = {"/calendarData/sales", "/calendarData/sales/"})
   public Set<Sale> getSalesCalendarData(@NotNull @Valid @ModelAttribute("loggedUser") User loggedUser) {
      return this.userService.getAllSalesForUser(loggedUser.getId());
   }


   @GetMapping(value = {"/calendarData/orders", "/calendarData/orders/"})
   public Set<Order> getOrdersCalendarData(@NotNull @Valid @ModelAttribute("loggedUser") User loggedUser) {
      return this.userService.getAllOrdersForUser(loggedUser.getId());
   }


   @GetMapping(value = {"/tableData", "/tableData/"})
   public Object getTableData() {
      User user = new User("Matteo", "Lambertucci", "matteolambertucci3@gmail.com", 20.0F, new Credentials(), new Nation());
      Set<User> set = new HashSet<User>();
      set.add(user);
      return set;
   }
}
