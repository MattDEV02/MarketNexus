package com.lambert.lambertecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ProductCategories")
public class ProductCategory {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @NotBlank
   @Column(name = "name")
   private String name;

   @NotBlank
   @Column(name = "description")
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
}