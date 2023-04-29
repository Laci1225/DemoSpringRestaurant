package com.example.demoSpringRestaurant.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class RestaurantDto {
    private Long id;
    private String name;
    private String owner;

    private String address;
    private String email;
    private String phoneNumber = null;
    private Integer numberOfTables = null;
    private Boolean isVegan = false;
    private Boolean canDeliver;
    public RestaurantDto(){}
    public RestaurantDto(Long id, String name, String owner, String address, String email, String phoneNumber, Integer numberOfTables, Boolean isVegan, Boolean canDeliver) {
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
}
