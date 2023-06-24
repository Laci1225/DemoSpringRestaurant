package com.example.demoSpringRestaurant.model;

import lombok.*;

import java.util.List;

@Data
@Builder
public class RestaurantDto {
    private String id;
    private List<OrderDto> orders;
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
