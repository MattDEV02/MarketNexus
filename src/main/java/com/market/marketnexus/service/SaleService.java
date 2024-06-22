package com.market.marketnexus.service;

import com.market.marketnexus.exception.SaleNotFoundException;
import com.market.marketnexus.helpers.sale.Utils;
import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.ProductCategory;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.ProductCategoryRepository;
import com.market.marketnexus.repository.ProductRepository;
import com.market.marketnexus.repository.SaleRepository;
import com.market.marketnexus.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.market.marketnexus.helpers.product.ProductImageFileUtils.deleteProductImageDirectory;

@Service
public class SaleService {
   @Autowired
   protected SaleRepository saleRepository;
   @Autowired
   protected ProductRepository productRepository;
   @Autowired
   protected ProductCategoryRepository productCategoryRepository;
   @Autowired
   protected UserRepository userRepository;

   @Transactional
   public Sale saveSale(@NotNull Sale sale) {
      Float salePrice = this.calculateSalePrice(sale);
      sale.setSalePrice(salePrice);
      Sale savedSale = this.saleRepository.save(sale);
      return savedSale;
   }

   @Transactional
   public Sale updateSale(@NotNull Sale saleToUpdate, @NotNull Sale sale) {
      saleToUpdate.setQuantity(sale.getQuantity());
      Float newSalePrice = this.calculateSalePrice(sale);
      saleToUpdate.setSalePrice(newSalePrice);
      Sale updatedSale = this.saleRepository.save(saleToUpdate);
      updatedSale.preUpdate();
      return updatedSale;
   }

   public Sale getSale(Long saleId) {
      return this.saleRepository.findById(saleId).orElseThrow(() -> new SaleNotFoundException("Sale with ID '" + saleId + "' was not found."));
   }

   public Iterable<Sale> getAllSalesByUser(@NotNull User user) {
      return this.saleRepository.findAllByUserOrderByUpdatedAtDesc(user);
   }

   public Iterable<Sale> getAllSales() {
      return this.saleRepository.findAllByOrderByUpdatedAtDesc();
   }

   public Set<Sale> getAllNotSoldSales(User user) {
      Set<Sale> notSoldSales = new HashSet<Sale>();
      Iterable<Sale> sales = this.saleRepository.findAllByUserOrderByUpdatedAtDesc(user);
      for (Sale sale : sales) {
         if (sale != null && !sale.getIsSold()) {
            notSoldSales.add(sale);
         }
      }
      return notSoldSales;
   }

   public Set<Sale> getAllSalesByProductName(String productName) {
      Set<Sale> salesByProductName = new HashSet<Sale>();
      Iterable<Sale> sales = this.saleRepository.findAllByOrderByUpdatedAtDesc();
      Set<Product> products = this.productRepository.findAllByNameContainingIgnoreCaseOrderById(productName);
      for (Sale sale : sales) {
         if (products.contains(sale.getProduct())) {
            salesByProductName.add(sale);
         }
      }
      return salesByProductName;
   }

   public Set<Sale> getAllSalesByProductCategoryId(Long productCategoryId) {
      Set<Sale> salesByProductCategoryId = new HashSet<Sale>();
      Iterable<Sale> sales = this.saleRepository.findAllByOrderByUpdatedAtDesc();
      ProductCategory productCategory = this.productCategoryRepository.findById(productCategoryId).orElse(null);
      Set<Product> products = this.productRepository.findAllByCategoryOrderById(productCategory);
      for (Sale sale : sales) {
         if (products.contains(sale.getProduct())) {
            salesByProductCategoryId.add(sale);
         }
      }
      return salesByProductCategoryId;
   }

   public Set<Sale> getAllSalesByProductNameAndProductCategoryId(String productName, Long productCategoryId) {
      Set<Sale> salesByProductNameAndProductCategoryId = new HashSet<Sale>();
      Iterable<Sale> sales = this.saleRepository.findAllByOrderByUpdatedAtDesc();
      ProductCategory productCategory = this.productCategoryRepository.findById(productCategoryId).orElse(null);
      Set<Product> products = this.productRepository.findAllByNameContainingIgnoreCaseAndCategoryOrderById(productName, productCategory);
      for (Sale sale : sales) {
         if (products.contains(sale.getProduct())) {
            salesByProductNameAndProductCategoryId.add(sale);
         }
      }
      return salesByProductNameAndProductCategoryId;
   }

   public Set<Sale> getAllUserSoldSales(User user) {
      Set<Sale> soldSales = new HashSet<Sale>();
      Iterable<Sale> sales = this.saleRepository.findAllByUserOrderByUpdatedAtDesc(user);
      for (Sale sale : sales) {
         if (sale != null && sale.getIsSold()) {
            soldSales.add(sale);
         }
      }
      return soldSales;
   }

   public List<Object[]> countCurrentWeekUserSoldSales(@NotNull Long userId) {
      return this.saleRepository.countCurrentWeekUserSales(userId);
   }

   public Float calculateSalePrice(@NotNull Sale sale) {
      return Utils.calculateSalePrice(sale);
   }

   public Boolean deleteSale(@NotNull Sale sale) {
      Product product = sale.getProduct();
      Long saleId = sale.getId();
      deleteProductImageDirectory(product);
      this.saleRepository.deleteById(saleId);
      return !this.saleRepository.existsById(saleId);
   }
}
