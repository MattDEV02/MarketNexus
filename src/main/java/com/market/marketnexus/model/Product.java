package com.market.marketnexus.model;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jdk.jfr.Unsigned;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Product")
@Table(name = "Products", schema = GlobalValues.SQL_SCHEMA_NAME)
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Unsigned
   @Min(FieldSizes.ENTITY_ID_MIN_VALUE)
   @Column(name = "id", nullable = false)
   private Long id;

   @Size(min = FieldSizes.PRODUCT_NAME_MIN_LENGTH, max = FieldSizes.PRODUCT_NAME_MAX_LENGTH, message = "The length of the Product name must be between 3 and 30 characters.")
   @Column(name = "name", nullable = false)
   @NotBlank(message = "Product Name is a mandatory field.")
   private String name;

   @Size(min = (FieldSizes.PRODUCT_DESCRIPTION_MIN_LENGTH), max = (FieldSizes.PRODUCT_DESCRIPTION_MAX_LENGTH))
   @Column(name = "description", nullable = false)
   @NotBlank
   private String description;

   @Min(value = (long) (FieldSizes.PRODUCT_PRICE_MIN_VALUE))
   @Max(value = (long) (FieldSizes.PRODUCT_PRICE_MAX_VALUE))
   @NotNull
   @Column(name = "price", nullable = false)
   private Float price;

   @Column(name = "image_relative_paths", nullable = false, columnDefinition = "TEXT[] NOT NULL")
   private List<String> imageRelativePaths;

   @ManyToOne(targetEntity = ProductCategory.class, optional = false)
   @JoinColumn(name = "category", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "products_productcategories_fk"))
   private ProductCategory category;

   public Product() {
      this.category = null;
      this.imageRelativePaths = new ArrayList<String>();
   }

   public Product(String name, String description, Float price, ProductCategory category) {
      this.name = name;
      this.description = description;
      this.price = price;
      this.category = category;
      this.imageRelativePaths = new ArrayList<String>();
   }

   public Product(String name, String description, Float price, ProductCategory category, String imageRelativePath) {
      this.name = name;
      this.description = description;
      this.price = price;
      this.category = category;
      this.imageRelativePaths = new ArrayList<String>();
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

   public List<String> getImageRelativePaths() {
      return this.imageRelativePaths;
   }

   public void setImageRelativePaths(List<String> imageRelativePaths) {
      this.imageRelativePaths = imageRelativePaths;
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
      return Objects.equals(this.getId(), product.getId()) || (Objects.equals(this.getName(), product.getName()) && Objects.equals(this.getDescription(), product.getDescription()) && Objects.equals(this.getPrice(), product.getPrice()) && Objects.equals(this.getCategory(), product.getCategory()));
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getName(), this.getDescription(), this.getPrice(), this.getCategory());
   }

   @Override
   public String toString() {
      return "Product: {" +
              //   "id = " + this.getId().toString() +
              ", name = '" + this.getName() +
              ", description = '" + this.getDescription() +
              ", price = " + this.getPrice().toString() +
              ", imageRelativePaths = " + this.getImageRelativePaths().toString() +
              ", category = " + this.getCategory().toString() +
              " }";
   }

}