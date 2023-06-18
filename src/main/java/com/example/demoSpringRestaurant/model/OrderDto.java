package com.example.demoSpringRestaurant.model;
import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private Long id;
    @ManyToOne
    private RestaurantEntity restaurant;

    private MealType mealType;

    private DrinkType drinkType;
    private double price;

    private String deliveryAddress;

    private LocalDateTime createDate;

    private OrderStatus orderStatus = OrderStatus.SENT;


    private LocalTime estimatedDeliveryTime;

    public double getPrice() {
        return getMealType().getValue() + getDrinkType().getValue();
    }
}
