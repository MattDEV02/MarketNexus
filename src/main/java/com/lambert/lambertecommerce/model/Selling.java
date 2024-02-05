package com.lambert.lambertecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Credentials", uniqueConstraints = @UniqueConstraint(columnNames = {"_user", "product", "inserted_at"}))
public class Selling {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id")
   private Long id;

   @Min(1)
   @Column(name = "quantity")
   private Integer quantity;

   @ManyToOne
   @Column(name = "_user")
   @JoinColumn(name = "_user", nullable = false)
   private User user;

   @ManyToOne
   @Column(name = "product")
   @JoinColumn(name = "product", nullable = false)
   private Product product;

   @Column(name = "inserted_at")
   private LocalDateTime insertedAt;

   @Column(name = "updated_at")
   private LocalDateTime updatedAt;

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
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || this.getClass() != o.getClass()) return false;
      Selling selling = (Selling) o;
      return Objects.equals(this.getId(), selling.getId()) || (Objects.equals(this.getUser(), selling.getUser()) && Objects.equals(this.getProduct(), selling.getProduct()) && Objects.equals(this.getInsertedAt(), selling.getInsertedAt()));
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId());
   }
}
