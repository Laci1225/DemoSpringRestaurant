package com.example.demoSpringRestaurant.persistance.document;

import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@Document(collection = "orders")
public class OrderDocument {
    @Id
    private String id;
    @DocumentReference
    private RestaurantDocument restaurant;
    private MealType mealType;
    private DrinkType drinkType;
    private double price;
    private boolean isDelivery;
    private String deliveryAddress;
    @DocumentReference
    private CourierDocument courierDocument;
    private LocalDateTime createDate;
    private OrderStatus orderStatus = OrderStatus.SENT;

    private LocalTime estimatedPreparationTime;
    @DocumentReference
    private GuestDocument guestDocument;

    public double getPrice() {
        return getMealType().getValue() + getDrinkType().getValue();
    }
}
