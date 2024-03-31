package com.lambert.lambertecommerce.service;

import com.lambert.lambertecommerce.model.Nation;
import com.lambert.lambertecommerce.repository.NationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class NationService {

   @Autowired
   protected NationRepository nationRepository;

   public Set<Nation> getAllNations() {
      Set<Nation> result = new HashSet<Nation>();
      Iterable<Nation> iterable = this.nationRepository.findAll();
      for (Nation nation : iterable) {
         result.add(nation);
      }
      return result;
   }
}
