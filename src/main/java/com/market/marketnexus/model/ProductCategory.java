package com.market.marketnexus.model;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;

import java.util.Objects;

@Entity(name = "product_categories")
@Table(name = "product_categories", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "productcategories_name_unique", columnNames = "name"))
public class ProductCategory {
   @Id
   @Unsigned
   @Min(FieldSizes.ENTITY_ID_MIN_VALUE)
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @NotBlank
   @Column(name = "name", nullable = false, unique = true)
   @Size(min = FieldSizes.PRODUCT_CATEGORY_NAME_MIN_LENGTH, max = FieldSizes.PRODUCT_CATEGORY_NAME_MAX_LENGTH)
   private String name;

   @NotBlank
   @Column(name = "description", nullable = false)
   @Size(min = FieldSizes.PRODUCT_CATEGORY_DESCRIPTION_MIN_LENGTH, max = FieldSizes.PRODUCT_CATEGORY_DESCRIPTION_MAX_LENGTH)
   private String description;

   public ProductCategory() {

   }

   public ProductCategory(Long id, String name, String description) {
      this.id = id;
      this.name = name;
      this.description = description;
   }

   public ProductCategory(String name, String description) {
      this.name = name;
      this.description = description;
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
      return this.name;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getName());
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) return true;
      if (object == null || this.getClass() != object.getClass()) {
         return false;
      }
      ProductCategory productCategory = (ProductCategory) object;
      return Objects.equals(this.getId(), productCategory.getId()) || Objects.equals(this.getName(), productCategory.getName());
   }

   @Override
   public String toString() {
      return "ProductCategory: {" +
              " id = " + this.getId().toString() +
              ", name = '" + this.getName() + '\'' +
              ", description = '" + this.getDescription() + '\'' +
              " }";
   }
}