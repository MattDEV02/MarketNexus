package com.lambert.lambertecommerce.service;

import com.lambert.lambertecommerce.model.Product;
import com.lambert.lambertecommerce.model.Sale;
import com.lambert.lambertecommerce.model.User;
import com.lambert.lambertecommerce.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SaleService {
   @Autowired
   protected SaleRepository saleRepository;

   public Sale saveSale(Sale sale, User user, Product product) {
      sale.setUser(user);
      sale.setProduct(product);
      return this.saleRepository.save(sale);
   }

   public Set<Sale> findAllByUser(User user) {
      return this.saleRepository.findAllByUser(user);
   }

}
