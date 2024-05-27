package com.market.marketnexus.model;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.helpers.constants.Temporals;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jdk.jfr.Unsigned;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "cart_line_items")
@Table(name = "cart_line_items", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "carts_user_unique", columnNames = {"cart", "sale", "inserted_at"}))
public class CartLineItem {

   @Id
   @Unsigned
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @Min(FieldSizes.ENTITY_ID_MIN_VALUE)
   private Long id;

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

   public CartLineItem() {
      this.cart = null;
      this.sale = null;
   }

   public CartLineItem(Cart cart, Sale sale) {
      this.cart = cart;
      this.sale = sale;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
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


   @PrePersist
   public void prePersist() {
      if (this.insertedAt == null) {
         this.insertedAt = LocalDateTime.now();
      }
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
              "id = " + this.getId().toString() +
              ", cart = " + this.getCart().toString() +
              ", sale = " + this.getSale().toString() +
              ", insertedAt = " + this.getInsertedAt().toString() +
              " }";
   }
}
