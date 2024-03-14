package com.lambert.lambertecommerce.model;

import com.lambert.lambertecommerce.helpers.credentials.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

import static com.lambert.lambertecommerce.helpers.constants.FieldSizes.USERNAME_MAX_LENGTH;
import static com.lambert.lambertecommerce.helpers.constants.FieldSizes.USERNAME_MIN_LENGTH;


@Entity
@Table(name = "Credentials")
public class Credentials {

   public static String DEFAULT_ROLE = Roles.ALL.toString();

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   @NotBlank
   @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH)
   @Column(name = "username", unique = true)
   @NotBlank
   private String username;
   @NotBlank
   @Column(name = "password")
   private String password;

   @Column(name = "role")
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
      return "Credentials = {" +
              "id = " + this.id +
              ", username = '" + this.username + '\'' +
              ", password = '" + this.password + '\'' +
              ", user = " + this.user +
              '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || this.getClass() != o.getClass()) return false;
      Credentials that = (Credentials) o;
      return Objects.equals(this.getId(), that.getId());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId());
   }
}
