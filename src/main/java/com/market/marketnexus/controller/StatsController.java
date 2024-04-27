package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPrefixes;
import com.market.marketnexus.model.Order;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.SaleService;
import com.market.marketnexus.service.UserService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
   public List<Object[]> getChartData(@NotNull @Valid @ModelAttribute("loggedUser") User loggedUser) {
      return this.saleService.countCurrentWeekUserSales(loggedUser.getId());
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
   public List<Object[]> getTableData() {
      return this.userService.usersPublishedSalesStats();
   }
}
