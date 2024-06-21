package com.market.marketnexus.model;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Entity(name = "Nation")
@Table(name = "Nations", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "nations_name_unique", columnNames = "name"))
public class Nation {

   @Id
   @Unsigned
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @Min(FieldSizes.ENTITY_ID_MIN_VALUE)
   private Long id;

   @NonNull
   @NotBlank
   @Column(name = "name", nullable = false, unique = true)
   @Size(min = (FieldSizes.NATION_NAME_MIN_LENGTH), max = (FieldSizes.NATION_NAME_MAX_LENGTH))
   private String name;

   public Nation() {

   }

   public Nation(@NotNull String name) {
      this.name = name;
   }

   public Nation(Long id, @NotNull String name) {
      this.id = id;
      this.name = name;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public @NotNull String getName() {
      return this.name;
   }

   public void setName(@NotNull String name) {
      this.name = name;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getName());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || this.getClass() != o.getClass()) {
         return false;
      }
      Nation nation = (Nation) o;
      return Objects.equals(this.getId(), nation.getId()) || Objects.equals(this.getName(), nation.getName());
   }

   @Override
   public String toString() {
      return "Nation: { " + "id = " + this.getId().toString() + ", name = '" + this.getName() + " }";
   }
}
