package com.lambert.lambertecommerce.model;

import com.lambert.lambertecommerce.helpers.constants.FieldSizes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product_categories")
public class ProductCategory {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @NotBlank
   @Column(name = "name")
   @Size(min = FieldSizes.PRODUCT_CATEGORY_NAME_MIN_LENGTH, max = FieldSizes.PRODUCT_CATEGORY_NAME_MIN_LENGTH)
   private String name;

   @NotBlank
   @Column(name = "description")
   @Size(min = FieldSizes.PRODUCT_CATEGORY_DESCRIPTION_MIN_LENGTH, max = FieldSizes.PRODUCT_CATEGORY_DESCRIPTION_MAX_LENGTH)
   private String description;

   @OneToMany(mappedBy = "category")
   private List<Product> products;

   public ProductCategory() {
      this.products = new LinkedList<Product>();
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

   public List<Product> getProducts() {
      return this.products;
   }

   public void setProducts(List<Product> products) {
      this.products = products;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.id);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || this.getClass() != o.getClass()) return false;
      ProductCategory productCategory = (ProductCategory) o;
      return Objects.equals(this.getId(), productCategory.getId()) || Objects.equals(this.getName(), productCategory.getName());
   }

   @Override
   public String toString() {
      return "ProductCategory{" +
              "id=" + this.id +
              ", name='" + this.name + '\'' +
              ", description='" + this.description + '\'' +
              '}';
   }
}