package com.lambert.lambertecommerce.model;

import com.lambert.lambertecommerce.helpers.constants.FieldSizes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "Nations")
public class Nation {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @NotBlank
   @Column(name = "name", nullable = false)
   @Size(min = (FieldSizes.NATION_NAME_MIN_LENGTH), max = (FieldSizes.NATION_NAME_MAX_LENGTH))
   private String name;

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

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getName());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || this.getClass() != o.getClass()) return false;
      Nation nation = (Nation) o;
      return Objects.equals(this.getId(), nation.getId()) || Objects.equals(this.getName(), nation.getName());
   }

   @Override
   public String toString() {
      return "Nation: { " + "id = " + this.getId().toString() + ", name = '" + this.getName() + " }";
   }
}
