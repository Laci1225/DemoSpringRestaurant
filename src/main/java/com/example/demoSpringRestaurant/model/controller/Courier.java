package com.example.demoSpringRestaurant.model.controller;

import com.example.demoSpringRestaurant.constant.Vehicle;
import com.example.demoSpringRestaurant.model.service.OrderDto;
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
public class Courier {
    @Id
    private String id;
    private String name;
    private boolean active;
    private List<Order> orders;
    private Order activeOrder;
    private double paymentPerOrder;
    private Vehicle vehicle;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;


}
