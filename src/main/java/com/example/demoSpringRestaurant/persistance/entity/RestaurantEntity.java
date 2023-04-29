package com.example.demoSpringRestaurant.persistance.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class RestaurantEntity {
    @Id
    @SequenceGenerator(name = "restaurant", sequenceName = "restaurant_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_sequence")
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String owner;

    private String address;
    @Email
    private String email;
    @Nullable
    private String phoneNumber = null;
    @Nullable
    private Integer numberOfTables = null;
    @Nullable
    private Boolean isVegan = false;
    @NotNull
    private Boolean canDeliver;

    public RestaurantEntity(){}

        public RestaurantEntity(String name, String owner, String address, String email, String phoneNumber, Integer numberOfTables, Boolean isVegan, Boolean canDeliver) {
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.numberOfTables = numberOfTables;
        this.isVegan = isVegan;
        this.canDeliver = canDeliver;
    }

    public RestaurantEntity(Long id, String name, String owner, String address, String email, String phoneNumber, Integer numberOfTables, Boolean isVegan, Boolean canDeliver) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.numberOfTables = numberOfTables;
        this.isVegan = isVegan;
        this.canDeliver = canDeliver;
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getNumberOfTables() {
        return numberOfTables;
    }

    public void setNumberOfTables(Integer numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    public Boolean getVegan() {
        return isVegan;
    }

    public void setVegan(Boolean vegan) {
        isVegan = vegan;
    }

    public Boolean getCanDeliver() {
        return canDeliver;
    }

    public void setCanDeliver(Boolean canDeliver) {
        this.canDeliver = canDeliver;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", numberOfTables=" + numberOfTables +
                ", isVegan=" + isVegan +
                ", canDeliver=" + canDeliver +
                '}';
    }
}