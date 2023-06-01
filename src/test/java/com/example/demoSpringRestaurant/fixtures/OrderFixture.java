package com.example.demoSpringRestaurant.fixtures;

import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.OrderUpdateDto;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderFixture {

    @ManyToOne
    private static final RestaurantEntity RESTAURANT_ENTITY = new RestaurantEntity();
    private static final MealType MEAL_TYPE = MealType.RICEANDFISH;
    private static final DrinkType DRINK_TYPE = DrinkType.COLA;
    private static final double PRICE = MEAL_TYPE.getValue() + DRINK_TYPE.getValue();
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

    public static OrderDto getOrderDtoGetNextStatus(OrderStatus fromStatus) {
        return OrderDto.builder()
                .id(1L)
                .restaurant(RESTAURANT_ENTITY)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(fromStatus.getNextStatus())
                .build();
    }

    public static OrderEntity getOrderEntity(boolean withId) {
        var orderEntity = OrderEntity.builder()
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

    public static OrderCreationDto getOrderCreationDto() {
        return OrderCreationDto.builder()
                .restaurant(RESTAURANT_ENTITY)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .build();
    }

    public static List<OrderDto> getOrderDtoList() {
        var list = new ArrayList<OrderDto>();
        list.add(OrderDto.builder()
                .id(1L)
                .restaurant(RESTAURANT_ENTITY)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .build());
        return list;
    }

    public static OrderUpdateDto getorderUpdateDto() {
        return OrderUpdateDto.builder()
                .restaurant(RESTAURANT_ENTITY)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .build();
    }

    public static OrderEntity getOrderEntityToGivenRestaurant(boolean withId,RestaurantEntity restaurantEntity) {
        var orderEntity = OrderEntity.builder()
                .restaurant(restaurantEntity)
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
    public static OrderDto getOrderDtoToGivenRestaurant(RestaurantEntity restaurantEntity) {
        return OrderDto.builder()
                .restaurant(restaurantEntity)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .build();
    }
    public static OrderDto getOrderDtoGetNextStatusToGivenRestaurant(OrderStatus fromStatus,RestaurantEntity restaurantEntity) {
        return OrderDto.builder()
                .id(1L)
                .restaurant(restaurantEntity)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(fromStatus.getNextStatus())
                .build();
    }
}
