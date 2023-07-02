package com.example.demoSpringRestaurant.model;

import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

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
    private OrderStatus orderStatus = OrderStatus.SENT;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime estimatedPreparationTime;
    private GuestDto guestDto;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public double getPrice() {
        return getMealType().getValue() + getDrinkType().getValue();
    }


}

