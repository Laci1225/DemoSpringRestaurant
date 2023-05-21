package com.example.demoSpringRestaurant.fixtures;

import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class OrderFixture {

    @ManyToOne
    private static final RestaurantEntity RESTAURANT_ENTITY = new RestaurantEntity();
    private static final MealType MEAL_TYPE = MealType.RICEANDFISH;
    private static final DrinkType DRINK_TYPE = DrinkType.COLA;
    private static final double PRICE = 0;
    private static final String DELIVERY_ADDRESS = "Budapest Heroes' Square";
    private static final LocalDateTime CREATE_DATE = LocalDateTime.now();
    private static final OrderStatus ORDER_STATUS = OrderStatus.SENT;


    public static OrderDto getOrderDto() {
        return OrderDto.builder()
                .id(1L)
                .restaurant(RESTAURANT_ENTITY)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .build();
    }
    public static OrderDto getOrderDtoGetNextStatus() {
        return OrderDto.builder()
                .id(1L)
                .restaurant(RESTAURANT_ENTITY)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS.getNextStatus())
                .build();
    }

    public static OrderEntity getOrderEntity(boolean withId) {
        var orderEntity = OrderEntity.builder()
                .id(1L)
                .restaurant(RESTAURANT_ENTITY)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .build();
        if (withId)
            orderEntity.setId(1L);
        return orderEntity;
    }
}
