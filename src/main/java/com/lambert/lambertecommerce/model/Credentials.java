package com.lambert.lambertecommerce.model;

import com.lambert.lambertecommerce.helpers.constants.FieldSizes;
import com.lambert.lambertecommerce.helpers.constants.Global;
import com.lambert.lambertecommerce.helpers.credentials.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;


@Entity
@Table(name = "Credentials", schema = Global.SQL_SCHEMA_NAME)
public class Credentials {

   public static String DEFAULT_ROLE = Roles.SELLER_AND_BOUGHTER.toString();

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @Min(1)
   private Long id;
   @NotBlank
   @Size(min = FieldSizes.USERNAME_MIN_LENGTH, max = FieldSizes.USERNAME_MAX_LENGTH)
   @Column(name = "username", unique = true, nullable = false)
   @NotBlank
   private String username;
   @NotBlank
   @Column(name = "password", nullable = false)
   private String password;

   @Column(name = "role", nullable = true)
   @NotBlank
   @Size(min = FieldSizes.ROLE_MIN_LENGTH, max = FieldSizes.ROLE_MAX_LENGTH)
   private String role;

   public Credentials() {
      this.role = Credentials.DEFAULT_ROLE;
   }

   public Credentials(String username, String password, Roles role) {
      this.username = username;
      this.password = password;
      this.role = role.toString();
   }

   public Credentials(String username, String password, String role) {
      this.username = username;
      this.password = password;
      this.role = role;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getRole() {
      return this.role;
   }

   public void setRole(String role) {
      this.role = role;
   }

   @Override
   public String toString() {
      return "Credentials: {" +
              " id = " + this.getId().toString() +
              ", username = '" + this.getUsername() + '\'' +
              ", role = " + this.getRole() +
              ", password = '" + this.getPassword() + '\'' +
              " }";
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) return true;
      if (object == null || this.getClass() != object.getClass()) {
         return false;
      }
      Credentials credentials = (Credentials) object;
      return Objects.equals(this.getId(), credentials.getId()) || Objects.equals(this.getUsername(), credentials.getUsername());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getUsername());
   }
}
