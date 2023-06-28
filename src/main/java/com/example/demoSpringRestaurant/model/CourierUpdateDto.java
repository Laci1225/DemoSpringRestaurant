package com.example.demoSpringRestaurant.model;

import com.example.demoSpringRestaurant.constant.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CourierUpdateDto {
    private String name;
    private boolean isActive;
    private List<OrderDto> orders;
    private OrderDto activeOrder;
    private double paymentPerOrder;
    private Vehicle vehicle;

    public CourierUpdateDto() {
    }
}
