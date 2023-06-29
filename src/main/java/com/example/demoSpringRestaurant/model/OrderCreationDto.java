package com.example.demoSpringRestaurant.model;

import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderCreationDto {

    @Id
    private String id;
    private RestaurantDto restaurant;
    private MealType mealType;
    private DrinkType drinkType;
    private double price;
    private boolean isDelivery;
    private String deliveryAddress;
    private CourierDto courierDto;
    private LocalDateTime createDate;
    private OrderStatus orderStatus = OrderStatus.SENT;
    private LocalTime estimatedPreparationTime;
    private GuestDto guestDto;
    public double getPrice() {
        return getMealType().getValue() + getDrinkType().getValue();
    }



}

