package com.market.marketnexus.service;

import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.ProductCategory;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.ProductCategoryRepository;
import com.market.marketnexus.repository.ProductRepository;
import com.market.marketnexus.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SaleService {
   @Autowired
   protected SaleRepository saleRepository;
   @Autowired
   protected ProductRepository productRepository;
   @Autowired
   protected ProductCategoryRepository productCategoryRepository;

   @Transactional
   public Sale saveSale(Sale sale, User user, Product product) {
      sale.setUser(user);
      sale.setProduct(product);
      return this.saleRepository.save(sale);
   }

   @Transactional
   public Sale saveSale(Sale sale) {
      return this.saleRepository.save(sale);
   }

   public Sale getSale(Long id) {
      Optional<Sale> result = this.saleRepository.findById(id);
      return result.orElse(null);
   }

   public Set<Sale> getAllSalesByUser(User user) {
      return this.saleRepository.findAllByUser(user);
   }

   public Set<Sale> getAllSales() {
      Set<Sale> result = new HashSet<Sale>();
      Iterable<Sale> iterable = this.saleRepository.findAllByOrderByUpdatedAt();
      for (Sale sale : iterable) {
         result.add(sale);
      }
      return result;
   }

   public Set<Sale> getAllSalesByProductName(String productName) {
      Set<Sale> result = new HashSet<Sale>();
      Set<Sale> sales = this.getAllSales();
      Set<Product> products = this.productRepository.findAllByNameContainingIgnoreCase(productName);
      for (Sale sale : sales) {
         if (products.contains(sale.getProduct())) {
            result.add(sale);
         }
      }
      return result;
   }

   public Set<Sale> getAllSalesByProductCategoryId(Long productCategoryId) {
      Set<Sale> result = new HashSet<Sale>();
      Set<Sale> sales = this.getAllSales();
      ProductCategory productCategory = this.productCategoryRepository.findById(productCategoryId).orElse(null);
      Set<Product> products = this.productRepository.findAllByCategory(productCategory);
      for (Sale sale : sales) {
         if (products.contains(sale.getProduct())) {
            result.add(sale);
         }
      }
      return result;
   }

   public Set<Sale> getAllSalesByProductNameAndProductCategoryId(String productName, Long productCategoryId) {
      Set<Sale> result = new HashSet<Sale>();
      Set<Sale> sales = this.getAllSales();
      ProductCategory productCategory = this.productCategoryRepository.findById(productCategoryId).orElse(null);
      Set<Product> products = this.productRepository.findAllByNameContainingIgnoreCaseAndCategory(productName, productCategory);
      for (Sale sale : sales) {
         if (products.contains(sale.getProduct())) {
            result.add(sale);
         }
      }
      return result;
   }

}
