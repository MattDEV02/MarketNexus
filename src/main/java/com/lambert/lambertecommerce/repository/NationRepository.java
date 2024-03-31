package com.lambert.lambertecommerce.repository;

import com.lambert.lambertecommerce.model.Nation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NationRepository extends CrudRepository<Nation, Long> {
   public Optional<Nation> findByName(String name);
}
