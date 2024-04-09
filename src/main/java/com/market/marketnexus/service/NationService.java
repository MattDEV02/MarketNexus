package com.market.marketnexus.service;

import com.market.marketnexus.model.Nation;
import com.market.marketnexus.repository.NationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NationService {

   @Autowired
   protected NationRepository nationRepository;

   public Boolean existsById(Long id) {
      return this.nationRepository.existsById(id);
   }

   public Set<Nation> getAllNations() {
      Set<Nation> allNationsOrderByName = this.nationRepository.findAllByOrderByName();
      return allNationsOrderByName;
   }
}
