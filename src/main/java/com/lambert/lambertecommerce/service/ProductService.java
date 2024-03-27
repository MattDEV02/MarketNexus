package com.lambert.lambertecommerce.service;

import com.lambert.lambertecommerce.model.*;
import com.lambert.lambertecommerce.repository.ProductCategoryRepository;
import com.lambert.lambertecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

   /**
    * This method retrieves a Product from the DB based on its ID.
    *
    * @param id the id of the Product to retrieve from the DB
    * @return the retrieved Product, or null if no Product with the passed ID could be found in the DB
    */
   @Transactional
   public Product getProduct(Long id) {
      Optional<Product> result = this.productRepository.findById(id);
      return result.orElse(null);
   }

   /**
    * This method saves a Product in the DB.
    *
    * @param product the Product to save into the DB
    * @return the saved Product
    * @throws DataIntegrityViolationException if a Product with the same Productname
    *                                         as the passed Product already exists in the DB
    */
   @Transactional
   public Product saveProduct(Product product) {
      return this.productRepository.save(product);
   }

   /**
    * This method retrieves all Products from the DB.
    *
    * @return a Set with all the retrieved Products
    */
   @Transactional
   public Set<Product> getAllProducts() {
      Set<Product> result = new HashSet<Product>();
      Iterable<Product> iterable = this.productRepository.findAll();
      for (Product product : iterable) {
         result.add(product);
      }
      return result;
   }

   @Transactional
   public Set<Product> findAllProductSaleByUser(User user) {
      Long userId = user.getId();
      Set<Product> result = new HashSet<Product>();
      Set<Sale> saleByUser = this.saleService.findAllByUser(user);
      for (Sale sale : saleByUser) {
         if (sale.getUser().getId().equals(userId)) {
            result.add(sale.getProduct());
         }
      }
      return result;
   }

   @Transactional
   public Set<Product> findAllProductOrderByUser(User user) {
      Long userId = user.getId();
      Set<Product> result = new HashSet<Product>();
      Set<Order> ordersByUser = this.orderService.findAllByUser(user);
      for (Order order : ordersByUser) {
         if (order.getUser().getId().equals(userId)) {
            result.add(order.getSale().getProduct());
         }
      }
      return result;
   }

   @Transactional
   public Set<Product> findAllProductCartByUser(User user) {
      Long userId = user.getId();
      Set<Product> result = new HashSet<Product>();
      Set<Cart> cartsByUser = this.cartService.findAllByUser(user);
      for (Cart cart : cartsByUser) {
         if (cart.getUser().getId().equals(userId)) {
            result.add(cart.getSale().getProduct());
         }
      }
      return result;
   }


   @Transactional
   public Set<Product> findAllByName(String productName) {
      return this.productRepository.findAllByName(productName);
   }

   @Transactional
   public Set<Product> findAllByCategory(Long productCategoryId) {
      Set<Product> result = null;
      Optional<ProductCategory> productCategory = this.productCategoryRepository.findById(productCategoryId);
      if (productCategory.isPresent()) {
         result = this.productRepository.findAllByCategory(productCategory.get());
      }
      return result;
   }

   @Transactional
   public Set<Product> findAllByNameAndCategory(String productName, Long productCategoryId) {
      Set<Product> result = null;
      Optional<ProductCategory> productCategory = this.productCategoryRepository.findById(productCategoryId);
      if (productCategory.isPresent()) {
         result = this.productRepository.findAllByNameAndCategory(productName, productCategory.get());
      }
      return result;
   }

   public Product findById(Long id) {
      Optional<Product> product = this.productRepository.findById(id);
      return product.orElse(null);
   }

   public boolean existsById(Long id) {
      return this.productRepository.existsById(id);
   }

}
