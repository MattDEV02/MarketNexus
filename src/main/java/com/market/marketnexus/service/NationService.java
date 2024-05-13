package com.market.marketnexus.service;

import com.market.marketnexus.model.Nation;
import com.market.marketnexus.repository.NationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class NationService {

   @Autowired
   protected NationRepository nationRepository;

   public Boolean existsById(Long id) {
      return this.nationRepository.existsById(id);
   }

   public Set<Nation> getAllNations() {
      return this.nationRepository.findAllByOrderByName();
   }

   public Map<Long, Nation> getAllNationsMap() {
      Map<Long, Nation> allNationsOrderByNameMap = new HashMap<Long, Nation>();
      Set<Nation> allNationsOrderByName = this.getAllNations();
      for (Nation nation : allNationsOrderByName) {
         allNationsOrderByNameMap.put(nation.getId(), nation);
      }
      return allNationsOrderByNameMap;
   }
}
