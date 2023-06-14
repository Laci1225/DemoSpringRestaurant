package com.example.demoSpringRestaurant.model;

import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderCreationDto {

    @ManyToOne
    private RestaurantEntity restaurant;

    @Nullable
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @Nullable
    @Enumerated(EnumType.STRING)
    private DrinkType drinkType;
    @Transient
    private double price;

    @NotNull
    private String deliveryAddress;

    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus orderStatus = OrderStatus.SENT;

    private LocalTime estimatedDeliveryTime;
    public double getPrice() {
        return getMealType().getValue() + getDrinkType().getValue();
    }



}

