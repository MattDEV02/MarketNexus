package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPaths;
import com.market.marketnexus.helpers.credentials.Utils;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.service.OrderService;
import com.market.marketnexus.service.SaleService;
import com.market.marketnexus.service.UserService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(value = "/" + APIPaths.STATS)
@RestController
public class StatsController {
   @Autowired
   private UserService userService;
   @Autowired
   private SaleService saleService;
   @Autowired
   private OrderService orderService;

   @GetMapping(value = {"/chartData", "/chartData/"})
   public List<Object[]> getChartData(@NotNull @Valid @ModelAttribute("loggedUser") User loggedUser) {
      return this.saleService.countCurrentWeekUserSoldSales(loggedUser.getId());
   }

   @GetMapping(value = {"/mapData", "/mapData/"})
   public List<Object[]> getMapData(@RequestParam(required = false, name = "isOnline") Boolean isOnline,
                                    @RequestParam(required = false, name = "role") String role,
                                    @RequestParam(required = false, name = "registeredFrom") String registeredFrom,
                                    @RequestParam(required = false, name = "registeredTo") String registeredTo) {
      role = Utils.existsRole(role) ? role : null;
      registeredFrom = registeredFrom != null && registeredFrom.isEmpty() ? null : registeredFrom;
      registeredTo = registeredTo != null && registeredTo.isEmpty() ? null : registeredTo;
      return userService.countUsersByNation(isOnline, role, registeredFrom, registeredTo);
   }

   @GetMapping(value = {"/calendarData/sales", "/calendarData/sales/"})
   public Iterable<Sale> getSalesCalendarData(@NotNull @Valid @ModelAttribute("loggedUser") User loggedUser) {
      return this.saleService.getAllSalesByUser(loggedUser);
   }

   @GetMapping(value = {"/calendarData/orders", "/calendarData/orders/"})
   public List<Object[]> getOrdersCalendarData(@NotNull @Valid @ModelAttribute("loggedUser") User loggedUser) {
      return this.orderService.getAllOrdersForUser(loggedUser.getId());
   }

   @GetMapping(value = {"/tableData", "/tableData/"})
   public List<Object[]> getTableData() {
      return this.userService.usersPublishedSalesStats();
   }
}
