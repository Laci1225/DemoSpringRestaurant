package com.example.demoSpringRestaurant.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class RestaurantCreationDto {


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

    public RestaurantCreationDto() {
    }

    public RestaurantCreationDto(String name, String owner, String address, String email, String phoneNumber, Integer numberOfTables, Boolean isVegan, Boolean canDeliver) {
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.numberOfTables = numberOfTables;
        this.isVegan = isVegan;
        this.canDeliver = canDeliver;
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
