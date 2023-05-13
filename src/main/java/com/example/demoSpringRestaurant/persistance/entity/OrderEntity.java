package com.example.demoSpringRestaurant.persistance.entity;

import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
public class OrderEntity {
    @Id
    @SequenceGenerator(name = "order", sequenceName = "order_sequence", initialValue = 1)
    @GeneratedValue(generator = "order_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    //@NotNull
    @ManyToOne
    private RestaurantEntity restaurant;

    @Enumerated(EnumType.STRING)
    @Nullable
    private MealType mealType;

    @Nullable
    @Enumerated(EnumType.STRING)
    private DrinkType drinkType;
    @Transient
    private double price;

    @NotNull
    private String deliveryAddress;

    @Transient
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus orderStatus = OrderStatus.SENT;


    private double getDrinkPrice(DrinkType drinkType) {
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
    }

    public double getPrice() {
        return  getMealPrice(getMealType()) + getDrinkPrice(getDrinkType());
    }


    public LocalDateTime getCreateDate() {
        return LocalDateTime.now();
    }

}
