package com.market.marketnexus.model;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.Global;
import com.market.marketnexus.helpers.constants.TemporalFormats;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Orders", schema = Global.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(columnNames = {"_user", "cart", "inserted_at"}))
public class Order {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @Min(FieldSizes.SALE_QUANTITY_MIN_VALUE)
   @Max(FieldSizes.SALE_QUANTITY_MAX_VALUE)
   @Column(name = "quantity", nullable = false)
   private Integer quantity;

   @ManyToOne
   @JoinColumn(name = "_user", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "orders_users_fk"))
   private User user;

   @ManyToOne
   @JoinColumn(name = "cart", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "orders_carts_fk"))
   private Cart cart;

   @Column(name = "inserted_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(pattern = TemporalFormats.DATE_TIME_FORMAT)
   private LocalDateTime insertedAt;

   @DateTimeFormat(pattern = TemporalFormats.DATE_TIME_FORMAT)
   @Column(name = "updated_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private LocalDateTime updatedAt;

   public Order() {

   }

   public Order(Integer quantity, User user, Cart cart) {
      this.quantity = quantity;
      this.user = user;
      this.cart = cart;
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

   public User getUser() {
      return this.user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Cart getCart() {
      return this.cart;
   }

   public void setCart(Cart cart) {
      this.cart = cart;
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
      Order order = (Order) object;
      return Objects.equals(this.getId(), order.getId()) || (Objects.equals(this.getUser(), order.getUser()) && Objects.equals(this.getCart(), order.getCart()) && Objects.equals(this.getInsertedAt(), order.getInsertedAt()));
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getUser(), this.getCart(), this.getInsertedAt());
   }

   @Override
   public String toString() {
      return "Order: {" +
              " id = " + this.getId().toString() +
              ", quantity = " + this.getQuantity().toString() +
              ", user = " + this.getUser().toString() +
              ", cart = " + this.getCart().toString() +
              ", insertedAt = " + this.getInsertedAt().toString() +
              ", updatedAt = " + this.getUpdatedAt().toString() +
              " }";
   }
}
