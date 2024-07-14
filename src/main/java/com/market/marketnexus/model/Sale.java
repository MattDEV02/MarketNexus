package com.market.marketnexus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.helpers.constants.Temporals;
import com.market.marketnexus.helpers.sale.Utils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Unsigned;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "Sale")
@Table(name = "Sales", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "sales_user_product_unique", columnNames = {"_user", "product"}))
public class Sale {

   public static final Integer SALE_DEFAULT_QUANTITY = 1;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Min(value = FieldSizes.ENTITY_ID_MIN_VALUE)
   @Unsigned
   @Column(name = "id", nullable = false)
   private Long id;

   @JsonIgnore
   @NotNull
   @Min(value = FieldSizes.SALE_QUANTITY_MIN_VALUE)
   @Max(value = FieldSizes.SALE_QUANTITY_MAX_VALUE)
   @Column(name = "quantity", nullable = false)
   private Integer quantity;
   @Column(name = "is_sold", columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
   private Boolean isSold;
   @JsonIgnore
   @Min(value = (long) FieldSizes.SALE_SALEPRICE_MIN_VALUE)
   @Max(value = (long) FieldSizes.SALE_SALEPRICE_MAX_VALUE)
   @Column(name = "sale_price", nullable = false)
   private Float salePrice;
   @JsonIgnore
   @ManyToOne(targetEntity = User.class, optional = false)
   @JoinColumn(name = "_user", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "sales_users_fk"))
   private User user;
   @ManyToOne(targetEntity = Product.class, optional = false, cascade = {CascadeType.REMOVE})
   @JoinColumn(name = "product", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "sales_products_fk"))
   private Product product;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Temporals.DATE_FORMAT)
   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   @Column(name = "inserted_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private LocalDateTime insertedAt;
   @JsonIgnore
   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   @Column(name = "updated_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private LocalDateTime updatedAt;

   public Sale() {
      this.user = null;
      this.product = null;
      this.isSold = false;
      this.quantity = Sale.SALE_DEFAULT_QUANTITY;
      this.salePrice = 0.0F;
   }

   public Sale(Integer quantity, User user, Product product) {
      this.quantity = quantity;
      this.user = user;
      this.product = product;
      this.isSold = false;
      this.salePrice = Utils.calculateSalePrice(this);
   }

   public Sale(Integer quantity, User user, Product product, Float salePrice) {
      this.quantity = quantity;
      this.user = user;
      this.product = product;
      this.isSold = false;
      this.salePrice = salePrice;
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

   public Boolean getIsSold() {
      return this.isSold;
   }

   public void setIsSold(Boolean isSold) {
      this.isSold = isSold;
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
              //" id = " + this.getId() != null ? this.getId().toString() : "null" +
              ", quantity = " + this.getQuantity().toString() +
              ", isSold = " + this.getIsSold().toString() +
              ", salePrice = " + this.getSalePrice().toString() +
              ", user = " + this.getUser().toString() +
              ", product = " + this.getProduct().toString() +
              //  ", insertedAt = " + this.getInsertedAt() != null ? this.getInsertedAt().toString() : "null" +
              //", updatedAt = " + this.getUpdatedAt() != null ? this.getUpdatedAt().toString() : "null" +
              " }";
   }
}
