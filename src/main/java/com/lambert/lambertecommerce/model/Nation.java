package com.lambert.lambertecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "Nations")
public class Nation {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", nullable = false)
   private Long id;

   @NotBlank
   @Column(name = "name")
   private String name;

   @OneToMany(mappedBy = "nation")
   private Set<User> users;

   public Nation() {
      this.users = new HashSet<User>();
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
}