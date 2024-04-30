package com.market.marketnexus.model;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.helpers.constants.Temporals;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jdk.jfr.Unsigned;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Carts")
@Table(name = "Carts", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = {@UniqueConstraint(columnNames = {"_user", "inserted_at"})})
public class Cart {
   private final static Float CART_START_PRICE = 0.0F;
   @Id
   @Unsigned
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @Min(FieldSizes.ENTITY_ID_MIN_VALUE)
   private Long id;

   @Min((long) FieldSizes.CART_CARTPRICE_MIN_VALUE)
   @Column(name = "cart_price", nullable = false)
   private Float cartPrice;

   @OneToOne(targetEntity = User.class, optional = true)
   @JoinColumn(name = "_user", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "carts_users_fk"))
   private User user;

   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   @Column(name = "inserted_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private LocalDateTime insertedAt;

   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   @Column(name = "updated_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private LocalDateTime updatedAt;

   @OneToMany(targetEntity = CartLineItem.class, mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<CartLineItem> cartLineItems;

   public Cart() {
      this.cartPrice = Cart.CART_START_PRICE;
      this.cartLineItems = Collections.emptySet();
   }

   public Cart(User user) {
      this.user = user;
      this.cartPrice = Cart.CART_START_PRICE;
      this.cartLineItems = Collections.emptySet();
   }

   public Float getCartPrice() {
      return this.cartPrice;
   }

   public void setCartPrice(Float cartPrice) {
      this.cartPrice = cartPrice;
   }

   public User getUser() {
      return this.user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
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

   public Set<CartLineItem> getCartLineItems() {
      return this.cartLineItems;
   }

   public void setCartLineItems(Set<CartLineItem> cartLineItems) {
      this.cartLineItems = cartLineItems;
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
      Cart cart = (Cart) object;
      return Objects.equals(this.getId(), cart.getId()) || (Objects.equals(this.getUser(), cart.getUser()) && Objects.equals(this.getInsertedAt(), cart.getInsertedAt()));
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getUser(), this.getInsertedAt());
   }

   @Override
   public String toString() {
      return "Cart: {" +
              "id = " + this.getId().toString() +
              ", user = " + this.getUser().toString() +
              ", cart_price = " + this.getCartPrice().toString() +
              ", insertedAt = " + this.getInsertedAt().toString() +
              ", updatedAt = " + this.getUpdatedAt().toString() +
              " }";
   }

}
