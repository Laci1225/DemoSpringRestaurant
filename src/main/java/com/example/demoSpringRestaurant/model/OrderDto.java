package com.example.demoSpringRestaurant.model;
import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class OrderDto {

    @Id
    private String id;
   // @ManyToOne
    private RestaurantDto restaurant;
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
