package com.market.marketnexus.controller;

import com.market.marketnexus.helpers.constants.APIPaths;
import com.market.marketnexus.model.Nation;
import com.market.marketnexus.service.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(APIPaths.NATIONS)
public class NationController {
   @Autowired
   private NationService nationService;

   @GetMapping("/search")
   public List<Nation> searchNations(@RequestParam("query") String query) {
      return this.nationService.getNations(query);
   }
}
