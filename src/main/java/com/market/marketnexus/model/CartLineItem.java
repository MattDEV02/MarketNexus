package com.market.marketnexus.model;

import com.market.marketnexus.helpers.constants.Global;
import com.market.marketnexus.helpers.constants.Temporals;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jdk.jfr.Unsigned;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cart_line_items", schema = Global.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "cartlineitems_user_sale_insertedat_unique", columnNames = {"_user", "sale", "inserted_at"}))
public class CartLineItem {

   @Id
   @Unsigned
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @Min(1)
   private Long id;

   @ManyToOne(targetEntity = User.class)
   @JoinColumn(name = "_user", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "carts_users_fk"))
   private User user;

   @ManyToOne(targetEntity = Sale.class, optional = false)
   @JoinColumn(name = "sale", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "sales_sales_fk"))
   private Sale sale;

   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   @Column(name = "inserted_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private LocalDateTime insertedAt;

   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   @Column(name = "updated_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private LocalDateTime updatedAt;

   public CartLineItem() {

   }

   public CartLineItem(Sale sale, User user) {
      this.user = user;
      this.sale = sale;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public User getUser() {
      return this.user;
   }

   public void setUser(User user) {
      this.user = user;
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
      CartLineItem sale = (CartLineItem) object;
      return Objects.equals(this.getId(), sale.getId()) || (Objects.equals(this.getUser(), sale.getUser()) && Objects.equals(this.getSale(), sale.getSale()) && Objects.equals(this.getInsertedAt(), sale.getInsertedAt()));
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getUser(), this.getSale(), this.getInsertedAt());
   }

   @Override
   public String toString() {
      return "Cart: {" +
              "id = " + this.getId().toString() +
              ", user = " + this.getUser().toString() +
              ", sale = " + this.getSale().toString() +
              ", insertedAt = " + this.getInsertedAt().toString() +
              ", updatedAt = " + this.getUpdatedAt().toString() +
              " }";
   }
}
