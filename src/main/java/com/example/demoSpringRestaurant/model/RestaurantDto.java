package com.example.demoSpringRestaurant.model;

import lombok.*;

@Data
@Builder
public class RestaurantDto {
    private Long id;
    private String name;
    private String owner;
    private String address;
    private String email;
    private String phoneNumber;
    private Integer numberOfTables;
    private Boolean isVegan;
    private Boolean canDeliver;
    private Boolean isOnWolt;
}
