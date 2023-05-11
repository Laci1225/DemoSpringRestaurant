package com.example.demoSpringRestaurant.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantUpdateDto {
    @NotNull
    private String name;
    @NotNull
    private String owner;

    private String address;
    @Email
    private String email;
    @Nullable
    private String phoneNumber;
    @Nullable
    private Integer numberOfTables;
    @Nullable
    private Boolean isVegan;
    @NotNull
    private Boolean canDeliver;
}
