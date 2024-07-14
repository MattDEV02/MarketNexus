package com.market.marketnexus.model;

import com.market.marketnexus.helpers.constants.FieldSizes;
import com.market.marketnexus.helpers.constants.GlobalValues;
import com.market.marketnexus.helpers.constants.Temporals;
import com.market.marketnexus.helpers.credentials.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity(name = "Credentials")
@Table(name = "Credentials", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "credentials_username_unique", columnNames = "username"))
public class Credentials {

   public static Roles DEFAULT_ROLE = Roles.SELLER_AND_BUYER;

   @Id
   @Unsigned
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @Min(value = FieldSizes.ENTITY_ID_MIN_VALUE)
   private Long id;
   @NotBlank
   @Size(min = FieldSizes.USERNAME_MIN_LENGTH, max = FieldSizes.USERNAME_MAX_LENGTH)
   @Column(name = "username", unique = true, nullable = false)
   @NotBlank()
   private String username;

   @Column(name = "password", nullable = false)
   private String password;

   @Column(name = "role", nullable = false)
   @NotBlank()
   private String role;

   @Column(name = "is_online", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
   private Boolean isOnline;

   @Column(name = "inserted_at", nullable = false)
   @Temporal(value = TemporalType.TIMESTAMP)
   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   private LocalDateTime insertedAt;

   @Column(name = "updated_at", nullable = false)
   @Temporal(value = TemporalType.TIMESTAMP)
   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   private LocalDateTime updatedAt;

   public Credentials() {
      this.role = Credentials.DEFAULT_ROLE.toString();
      this.isOnline = false;
   }

   public Credentials(String username, String password) {
      this.username = username;
      this.password = password;
      this.role = Credentials.DEFAULT_ROLE.toString();
   }

   public Credentials(String username, String password, @NotNull Roles role) {
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

   public Boolean getIsOnline() {
      return this.isOnline;
   }

   public void setIsOnline(Boolean isOnline) {
      this.isOnline = isOnline;
   }

   public LocalDateTime getInsertedAt() {
      return this.insertedAt;
   }

   public void setInsertedAt(LocalDateTime insertedAt) {
      this.insertedAt = insertedAt;
   }

   public LocalDateTime getUpdatedAt() {
      return this.updatedAt;
   }

   public void setUpdatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
   }

   @PrePersist
   public void prePersist() {
      if (this.insertedAt == null) {
         this.insertedAt = LocalDateTime.now();
      }
      if (this.updatedAt == null) {
         this.updatedAt = this.insertedAt;
      }
   }

   @PreUpdate
   public void preUpdate() {
      this.updatedAt = LocalDateTime.now();
   }

   @Override
   public String toString() {
      return "Credentials: {" +
              // " id = " + this.getId() != null ? this.getId().toString() : "null" +
              ", username = " + this.getUsername() +
              ", role = " + this.getRole() +
              ", password = " + this.getPassword() +
              ", isOnline = '" + this.getIsOnline() +
              //", insertedAt = " + this.getInsertedAt() != null ? this.getInsertedAt().toString() : "null" +
              //", updatedAt = " + this.getUpdatedAt() != null ? this.getUpdatedAt().toString() : "null" +
              " }";
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
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
