package com.example.demoSpringRestaurant.model;

import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class OrderCreationDto {

    @NotNull
    private Long restaurantId;

    @Nullable
    @Enumerated(EnumType.STRING)
    private OrderEntity.MealType Meal;

    @Enumerated(EnumType.STRING)
    @Nullable
    private OrderEntity.DrinkType Drink;
    @Transient
    private double price;

    @NotNull
    private String deliveryAddress;

    @Transient
    private LocalDate createDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderEntity.OrderStatus orderStatus = OrderEntity.OrderStatus.SENT;

    enum OrderStatus {
        SENT,
        APPROVED,
        SHIPPING,
        SHIPPED;

        public OrderStatus getNextStatus() {
            return switch (this) {
                case SENT -> APPROVED;
                case APPROVED -> SHIPPING;
                case SHIPPING, SHIPPED -> SHIPPED;
            };
        }
    }

    enum DrinkType {
        COLA, WATER, JUICE, LEMONADE, TEA
    }

    public enum MealType {
        RICEANDFISH, FISHANDCHIPS, CHICKENANDRICE, CHICKENANDFISH
    }

    private double getDrinkPrice(OrderEntity.DrinkType drinkType) {
        if (drinkType == null) return 0;
        return switch (drinkType) {
            case COLA -> 3.1;
            case WATER -> 1.0;
            case JUICE, LEMONADE -> 2.6;
            case TEA -> 1.1;
        };
    }

    private double getFoodPrice(OrderEntity.MealType mealType) {
        if (mealType == null) return 0;
        return switch (mealType) {
            case RICEANDFISH -> 10.1;
            case FISHANDCHIPS -> 12.1;
            case CHICKENANDFISH -> 20.1;
            case CHICKENANDRICE -> 15.1;
        };
    }

    public OrderCreationDto(OrderEntity.MealType meal, OrderEntity.DrinkType drink, double price) {
        Meal = meal;
        Drink = drink;
        this.price = price;
    }

    public OrderCreationDto() {
    }

    public OrderEntity.MealType getMeal() {
        return Meal;
    }

    public void setMeal(OrderEntity.MealType meal) {
        Meal = meal;
    }

    public OrderEntity.DrinkType getDrink() {
        return Drink;
    }

    public void setDrink(OrderEntity.DrinkType drink) {
        Drink = drink;
    }

    public double getPrice() {
        return getFoodPrice(getMeal()) + getDrinkPrice(getDrink());
    }

    /*public void setPrice(double price) {
        this.price = price;
    }*/
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDate getCreateDate() {
        return LocalDate.now();
    }

    public OrderEntity.OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderEntity.OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}

