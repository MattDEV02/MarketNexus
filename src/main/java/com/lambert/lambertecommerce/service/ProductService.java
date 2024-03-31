package com.lambert.lambertecommerce.service;

import com.lambert.lambertecommerce.model.Product;
import com.lambert.lambertecommerce.repository.ProductCategoryRepository;
import com.lambert.lambertecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
   @Autowired
   protected ProductRepository productRepository;
   @Autowired
   protected ProductCategoryRepository productCategoryRepository;
   @Autowired
   protected CartService cartService;
   @Autowired
   private SaleService saleService;
   @Autowired
   private OrderService orderService;

   public Product getProduct(Long id) {
      Optional<Product> result = this.productRepository.findById(id);
      return result.orElse(null);
   }

   @Transactional
   public Product saveProduct(Product product) {
      return this.productRepository.save(product);
   }

   public Set<Product> getAllProducts() {
      Set<Product> result = new HashSet<Product>();
      Iterable<Product> iterable = this.productRepository.findAll();
      for (Product product : iterable) {
         result.add(product);
      }
      return result;
   }

}
