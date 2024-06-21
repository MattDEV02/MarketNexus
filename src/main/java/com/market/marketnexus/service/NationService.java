package com.market.marketnexus.service;

import com.market.marketnexus.model.Nation;
import com.market.marketnexus.repository.NationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NationService {

   @Autowired
   protected NationRepository nationRepository;

   public Iterable<Nation> getAllNations() {
      return this.nationRepository.findAllByOrderByName();
   }

   public Map<Long, Nation> getAllNationsMap() {
      Map<Long, Nation> allNationsOrderByNameMap = new HashMap<Long, Nation>();
      Iterable<Nation> allNationsOrderByName = this.getAllNations();
      for (Nation nation : allNationsOrderByName) {
         allNationsOrderByNameMap.put(nation.getId(), nation);
      }
      return allNationsOrderByNameMap;
   }
}
