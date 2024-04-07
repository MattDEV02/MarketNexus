package com.market.marketnexus.repository;

import com.market.marketnexus.model.Nation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NationRepository extends CrudRepository<Nation, Long> {
   public Optional<Nation> findByName(String name);
}
