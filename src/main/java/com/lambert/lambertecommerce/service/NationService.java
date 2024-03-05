package com.lambert.lambertecommerce.service;

import com.lambert.lambertecommerce.model.Nation;
import com.lambert.lambertecommerce.repository.NationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class NationService {

   @Autowired
   protected NationRepository nationRepository;

   @Transactional
   public Nation getNation(Long id) {
      Optional<Nation> result = this.nationRepository.findById(id);
      return result.orElse(null);
   }

   @Transactional
   public Nation saveNation(Nation nation) {
      return this.nationRepository.save(nation);
   }

   /**
    * This method retrieves all Users from the DB.
    *
    * @return a Set with all the retrieved Users
    */
   @Transactional
   public Set<Nation> getAllNations() {
      Set<Nation> result = new HashSet<Nation>();
      Iterable<Nation> iterable = this.nationRepository.findAll();
      for (Nation nation : iterable) {
         result.add(nation);
      }
      return result;
   }
}
