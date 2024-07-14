package com.market.marketnexus.model;

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

@Entity(name = "CartLineItem")
@Table(name = "cart_line_items", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "carts_user_unique", columnNames = {"cart", "sale", "inserted_at"}))
public class CartLineItem {

   public static final Integer CARTLINEITEM_DEFAULT_QUANTITY = 1;

   @Id
   @Unsigned
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @Min(FieldSizes.ENTITY_ID_MIN_VALUE)
   private Long id;

   @NotNull
   @Min(value = FieldSizes.CARTLINEITEM_QUANTITY_MIN_VALUE)
   @Max(value = FieldSizes.CARTLINEITEM_QUANTITY_MAX_VALUE)
   @Column(name = "quantity", nullable = false)
   private Integer quantity;

   @Min(value = (long) FieldSizes.CARTLINEITEM_CARTLINEITEMPRICE_MIN_VALUE)
   @Max(value = (long) FieldSizes.CARTLINEITEM_CARTLINEITEMPRICE_MAX_VALUE)
   @Column(name = "cartlineitem_price", nullable = false)
   private Float cartLineItemPrice;

   @ManyToOne(targetEntity = Cart.class)
   @JoinColumn(name = "cart", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "cartlineitems_carts_fk"))
   private Cart cart;

   @ManyToOne(targetEntity = Sale.class, optional = false)
   @JoinColumn(name = "sale", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "sales_sales_fk"))
   private Sale sale;

   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   @Column(name = "inserted_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private LocalDateTime insertedAt;

   @Column(name = "updated_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   private LocalDateTime updatedAt;

   public CartLineItem() {
      this.cart = null;
      this.sale = null;
      this.quantity = CartLineItem.CARTLINEITEM_DEFAULT_QUANTITY;
      this.cartLineItemPrice = 0.0F;
   }

   public CartLineItem(Cart cart, @org.jetbrains.annotations.NotNull Sale sale) {
      this.cart = cart;
      this.sale = sale;
      this.quantity = sale.getQuantity();
      this.cartLineItemPrice = sale.getSalePrice();
   }

   public CartLineItem(Cart cart, Sale sale, Integer quantity) {
      this.cart = cart;
      this.sale = sale;
      this.quantity = quantity;
      this.cartLineItemPrice = Utils.calculateSalePrice(sale, quantity);
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

   public Float getCartLineItemPrice() {
      return this.cartLineItemPrice;
   }

   public void setCartLineItemPrice(Float cartLineItemPrice) {
      this.cartLineItemPrice = cartLineItemPrice;
   }

   public Cart getCart() {
      return this.cart;
   }

   public void setUser(Cart cart) {
      this.cart = cart;
   }

   public Sale getSale() {
      return this.sale;
   }

   public void setSale(Sale sale) {
      this.sale = sale;
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
      if (this == object) return true;
      if (object == null || this.getClass() != object.getClass()) {
         return false;
      }
      CartLineItem cartLineItem = (CartLineItem) object;
      return Objects.equals(this.getId(), sale.getId()) || (Objects.equals(this.getCart(), cartLineItem.getCart()) && Objects.equals(this.getSale(), cartLineItem.getSale()) && Objects.equals(this.getInsertedAt(), sale.getInsertedAt()));
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getCart(), this.getSale(), this.getInsertedAt());
   }

   @Override
   public String toString() {
      return "CartLineItem: {" +
              //"id = " + this.getId().toString() +
              ", quantity = " + this.getQuantity().toString() +
              ", cartLineItemPrice = " + this.getCartLineItemPrice().toString() +
              ", cart = " + this.getCart().toString() +
              ", sale = " + this.getSale().toString() +
              // ", insertedAt = " + this.getInsertedAt().toString() +
              // ", insertedAt = " + this.getInsertedAt().toString() +
              " }";
   }
}
