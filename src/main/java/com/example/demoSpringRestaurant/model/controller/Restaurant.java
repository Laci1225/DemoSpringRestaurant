package com.example.demoSpringRestaurant.model.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    private String id;
    private List<Order> orders;
    private String name;
    private String owner;
    private String address;
    private String email;
    private String phoneNumber;

    private Integer numberOfTables;
    private Boolean isVegan;
    private Boolean canDeliver;
    private Boolean isOnWolt;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
