package com.example.demoSpringRestaurant.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DrinkType {
    COLA(5.2), WATER(2.5), JUICE(3.5), LEMONADE(4.2), TEA(3.4);
    private final double value;
}
