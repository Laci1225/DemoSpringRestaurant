package com.example.demoSpringRestaurant.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
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
}
