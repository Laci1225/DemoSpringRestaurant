package com.example.demoSpringRestaurant.model;
import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    /*private double getDrinkPrice(DrinkType drinkType) {
        if (drinkType == null) return 0;
        return switch (drinkType) {
            case COLA -> 3.1;
            case WATER -> 1.0;
            case JUICE, LEMONADE -> 2.6;
            case TEA -> 1.1;
        };
    }

    private double getMealPrice(MealType mealType) {
        if (mealType == null) return 0;
        return switch (mealType) {
            case RICEANDFISH -> 10.1;
            case FISHANDCHIPS -> 12.1;
            case CHICKENANDFISH -> 20.1;
            case CHICKENANDRICE -> 15.1;
        };
    }*/

    public double getPrice() {
        return getMealType().getValue() + getDrinkType().getValue();
    }

    public LocalDateTime getCreateDate() {
        return LocalDateTime.now();
    }
}
