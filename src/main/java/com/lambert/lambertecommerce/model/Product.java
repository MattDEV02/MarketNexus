package com.lambert.lambertecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "Products")
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @Size(min = 3, max = 30)
   @Column(name = "name")
   @NotBlank
   private String name;
   @Size(min = 3, max = 100)
   @Column(name = "description")
   @NotBlank
   private String description;

   @Column(name = "price")
   @NotNull
   @Min((long) (0.1))
   private Float price;

   @ManyToOne
   @JoinColumn(name = "category", nullable = false)
   private ProductCategory category;

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public ProductCategory getCategory() {
      return category;
   }

   public void setCategory(ProductCategory category) {
      this.category = category;
   }

   public Float getPrice() {
      return price;
   }

   public void setPrice(Float price) {
      this.price = price;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || this.getClass() != o.getClass()) return false;
      Product product = (Product) o;
      return Objects.equals(this.getId(), product.getId());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId());
   }

   @Override
   public String toString() {
      return "Product{" +
              "id=" + this.id +
              ", name='" + this.name + '\'' +
              ", description='" + this.description + '\'' +
              ", price=" + this.price +
              ", category=" + this.category.toString() +
              '}';
   }
}