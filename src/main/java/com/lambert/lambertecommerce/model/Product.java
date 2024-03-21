package com.lambert.lambertecommerce.model;

import com.lambert.lambertecommerce.helpers.constants.FieldSizes;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Objects;

@Entity
@Table(name = "Products")
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @Size(min = (FieldSizes.PRODUCT_NAME_MIN_LENGTH), max = (FieldSizes.PRODUCT_NAME_MAX_LENGTH))
   @Column(name = "name")
   @NotBlank
   private String name;
   @Size(min = (FieldSizes.PRODUCT_DESCRIPTION_MIN_LENGTH), max = (FieldSizes.PRODUCT_DESCRIPTION_MAX_LENGTH))
   @Column(name = "description")
   @NotBlank
   private String description;

   @Column(name = "price")
   @NotNull
   @Min((long) (FieldSizes.PRODUCT_PRICE_MIN_VALUE))
   @Max((long) (FieldSizes.PRODUCT_PRICE_MAX_VALUE))
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
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public ProductCategory getCategory() {
      return this.category;
   }

   public void setCategory(ProductCategory category) {
      this.category = category;
   }

   public Float getPrice() {
      return this.price;
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