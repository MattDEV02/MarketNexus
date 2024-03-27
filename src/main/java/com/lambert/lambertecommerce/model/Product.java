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
   @Column(name = "name", nullable = false)
   @NotBlank
   private String name;
   @Size(min = (FieldSizes.PRODUCT_DESCRIPTION_MIN_LENGTH), max = (FieldSizes.PRODUCT_DESCRIPTION_MAX_LENGTH))
   @Column(name = "description", nullable = false)
   @NotBlank
   private String description;

   @Column(name = "price", nullable = false)
   @NotNull
   @Min((long) (FieldSizes.PRODUCT_PRICE_MIN_VALUE))
   @Max((long) (FieldSizes.PRODUCT_PRICE_MAX_VALUE))
   private Float price;

   @Column(name = "image_relative_path", nullable = false)
   @Size(min = (FieldSizes.PRODUCT_IMAGERELATIVEPATH_MIN_LENGTH))
   private String imageRelativePath;

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

   public String getImageRelativePath() {
      return this.imageRelativePath;
   }

   public void setImageRelativePath(String imageRelativePath) {
      this.imageRelativePath = imageRelativePath;
   }

   public Float getPrice() {
      return this.price;
   }

   public void setPrice(Float price) {
      this.price = price;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) return true;
      if (object == null || this.getClass() != object.getClass()) return false;
      Product product = (Product) object;
      return Objects.equals(this.getId(), product.getId());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId());
   }

   @Override
   public String toString() {
      return "Product: {" +
              "id = " + this.getId().toString() +
              ", name = '" + this.getName() + '\'' +
              ", description = '" + this.getDescription() + '\'' +
              ", price = " + this.getPrice().toString() +
              ", imageRelativePath = " + this.getImageRelativePath() +
              ", category = " + this.getCategory().toString() +
              " }";
   }

}