package com.lambert.lambertecommerce.model;

import com.lambert.lambertecommerce.helpers.constants.FieldSizes;
import com.lambert.lambertecommerce.helpers.credentials.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;


@Entity
@Table(name = "Credentials")
public class Credentials {

   public static String DEFAULT_ROLE = Roles.ALL.toString();

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

   @Column(name = "role", nullable = false)
   @Size(min = FieldSizes.ROLE_MIN_LENGTH, max = FieldSizes.ROLE_MAX_LENGTH)
   private String role;
   @OneToOne
   @JoinColumn(name = "_user", nullable = false, unique = true)
   private User user;

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

   public User getUser() {
      return this.user;
   }

   public void setUser(User user) {
      this.user = user;
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
              ", password = '" + this.getPassword() + '\'' +
              ", user = " + this.getUser().toString() +
              " }";
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) return true;
      if (object == null || this.getClass() != object.getClass()) return false;
      Credentials credentials = (Credentials) object;
      return Objects.equals(this.getId(), credentials.getId()) || Objects.equals(this.getUser(), credentials.getUser()) || Objects.equals(this.getUsername(), credentials.getUsername());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getUser(), this.getUsername());
   }
}
