package com.market.marketnexus.model;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.Global;
import com.market.marketnexus.helpers.constants.Temporals;
import com.market.marketnexus.helpers.sale.Utils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jdk.jfr.Unsigned;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Sales", schema = Global.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "sales_user_product_insertedat_unique", columnNames = {"_user", "product", "inserted_at"}))
public class Sale {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Min(1)
   @Unsigned
   @Column(name = "id", nullable = false)
   private Long id;

   @Min(FieldSizes.SALE_QUANTITY_MIN_VALUE)
   @Max(FieldSizes.SALE_QUANTITY_MAX_VALUE)
   @Column(name = "quantity", nullable = false)
   private Integer quantity;

   @Min((long) FieldSizes.SALE_SALEPRICE_MIN_VALUE)
   @Max((long) FieldSizes.SALE_SALEPRICE_MAX_VALUE)
   @Column(name = "sale_price", nullable = false)
   private Float salePrice;

   @ManyToOne(targetEntity = User.class, optional = false)
   @JoinColumn(name = "_user", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "sales_users_fk"))
   private User user;

   @ManyToOne(targetEntity = Product.class, optional = false)
   @JoinColumn(name = "product", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "sales_products_fk"))
   private Product product;

   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   @Column(name = "inserted_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private LocalDateTime insertedAt;

   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   @Column(name = "updated_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private LocalDateTime updatedAt;

   public Sale() {
      //this.salePrice = this.calculateSalePrice(this.getProduct(), this.getQuantity());
   }

   public Sale(Integer quantity, User user, Product product) {
      this.quantity = quantity;
      this.user = user;
      this.product = product;
      this.salePrice = this.calculateSalePrice(product, quantity);
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Integer getQuantity() {
      return this.quantity;
   }

   public void setQuantity(Integer quantity) {
      this.quantity = quantity;
   }

   public Float getSalePrice() {
      return this.salePrice;
   }

   public void setSalePrice(Float salePrice) {
      this.salePrice = salePrice;
   }

   public User getUser() {
      return this.user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   public LocalDateTime getInsertedAt() {
      return this.insertedAt;
   }

   public void setInsertedAt(LocalDateTime insertedAt) {
      this.insertedAt = insertedAt;
   }

   public LocalDateTime getUpdatedAt() {
      return this.updatedAt;
   }

   public void setUpdatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
   }

   @PrePersist
   public void prePersist() {
      if (this.insertedAt == null) {
         this.insertedAt = LocalDateTime.now();
      }
      if (this.updatedAt == null) {
         this.updatedAt = this.insertedAt;
      }
   }

   @PreUpdate
   public void preUpdate() {
      this.updatedAt = LocalDateTime.now();
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (object == null || this.getClass() != object.getClass()) {
         return false;
      }
      Sale sale = (Sale) object;
      return Objects.equals(this.getId(), sale.getId()) || (Objects.equals(this.getUser(), sale.getUser()) && Objects.equals(this.getProduct(), sale.getProduct()) && Objects.equals(this.getInsertedAt(), sale.getInsertedAt()));
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getUser(), this.getProduct(), this.getInsertedAt());
   }

   @Override
   public String toString() {
      return "Sale: {" +
              " id = " + this.getId().toString() +
              ", quantity = " + this.getQuantity().toString() +
              ", salePrice = " + this.getSalePrice().toString() +
              ", user = " + this.getUser().getId() +
              ", product = " + this.getProduct().getId() +
              ", insertedAt = " + this.getInsertedAt().toString() +
              ", updatedAt = " + this.getUpdatedAt().toString() +
              " }";
   }

   public Float calculateSalePrice(Product product, Integer quantity) {
      return Utils.roundNumberTo2Decimals(product.getPrice() * quantity);
   }
}
