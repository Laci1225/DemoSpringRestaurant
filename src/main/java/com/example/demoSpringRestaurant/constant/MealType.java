package com.example.demoSpringRestaurant.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MealType {
    RICEANDFISH(14.0),
    FISHANDCHIPS(10.1),
    CHICKENANDRICE(7.7),
    CHICKENANDFISH(6.6);

    private final double value;
}
