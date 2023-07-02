package com.example.demoSpringRestaurant.model;

import com.example.demoSpringRestaurant.constant.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CourierCreationDto {
    private String name;
    private boolean isActive;
    private List<OrderDto> orders;
    private OrderDto activeOrder;
    private double paymentPerOrder;
    private Vehicle vehicle;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    public CourierCreationDto() {
    }
}
