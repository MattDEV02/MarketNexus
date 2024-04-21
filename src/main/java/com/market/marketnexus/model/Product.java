package com.market.marketnexus.model;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;

import java.util.Objects;

@Entity
@Table(name = "Products", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "products_name_description_price_imagerelvepath_category_unique", columnNames = {"name", "description", "price", "image_relative_path", "category"}))
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Unsigned
   @Min(1)
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

   @Min((long) (FieldSizes.PRODUCT_PRICE_MIN_VALUE))
   @Max((long) (FieldSizes.PRODUCT_PRICE_MAX_VALUE))
   @Column(name = "price", nullable = false)
   private Float price;

   @Column(name = "image_relative_path", nullable = true)
   @Size(min = (FieldSizes.PRODUCT_IMAGERELATIVEPATH_MIN_LENGTH))
   private String imageRelativePath;

   @ManyToOne(targetEntity = ProductCategory.class, optional = false)
   @JoinColumn(name = "category", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "products_productcategories_fk"))
   private ProductCategory category;

   public Product() {

   }

   public Product(String name, String description, Float price, ProductCategory category) {
      this.name = name;
      this.description = description;
      this.price = price;
      this.category = category;
   }

   public Product(String name, String description, Float price, ProductCategory category, String imageRelativePath) {
      this.name = name;
      this.description = description;
      this.price = price;
      this.imageRelativePath = imageRelativePath;
      this.category = category;
   }

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
      if (object == null || this.getClass() != object.getClass()) {
         return false;
      }
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