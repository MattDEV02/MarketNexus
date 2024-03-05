package com.lambert.lambertecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @NotBlank
   @Column(name = "name")
   private String name;

   @NotBlank
   @Column(name = "surname")
   private String surname;

   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @Column(name = "birthdate", nullable = true)
   private Date birthDate;

   @NotBlank
   @Email
   @Column(name = "email", unique = true)
   private String email;

   @NotNull
   @Min(0)
   @Column(name = "balance")
   private Float balance;

   @ManyToOne
   @JoinColumn(name = "nation", nullable = false)
   private Nation nation;

   @Column(name = "inserted_at")
   private LocalDateTime insertedAt;

   @Column(name = "updated_at")
   private LocalDateTime updatedAt;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSurname() {
      return surname;
   }

   public void setSurname(String surname) {
      this.surname = surname;
   }

   public Date getBirthDate() {
      return this.birthDate;
   }

   public void setBirthDate(Date birthDate) {
      this.birthDate = birthDate;
   }

   public Float getBalance() {
      return this.balance;
   }

   public void setBalance(Float balance) {
      this.balance = balance;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Nation getNation() {
      return this.nation;
   }

   public void setNation(Nation nation) {
      this.nation = nation;
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
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || this.getClass() != o.getClass()) return false;
      User user = (User) o;
      return Objects.equals(this.getId(), user.getId()) || Objects.equals(this.getEmail(), user.getEmail());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId());
   }
}
