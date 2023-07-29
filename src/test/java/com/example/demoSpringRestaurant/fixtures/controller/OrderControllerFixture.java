package com.example.demoSpringRestaurant.fixtures.controller;

import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.model.controller.Courier;
import com.example.demoSpringRestaurant.model.controller.Guest;
import com.example.demoSpringRestaurant.model.controller.Order;
import com.example.demoSpringRestaurant.model.controller.Restaurant;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderControllerFixture {

    private static final Restaurant RESTAURANT = RestaurantControllerFixture.getRestaurant(true);
    private static final MealType MEAL_TYPE = MealType.RICEANDFISH;
    private static final DrinkType DRINK_TYPE = DrinkType.COLA;
    private static final double PRICE = MEAL_TYPE.getValue() + DRINK_TYPE.getValue();
    private static final String DELIVERY_ADDRESS = "Budapest Heroes' Square";
    private static final OrderStatus ORDER_STATUS = OrderStatus.SENT;
    private static final String GUEST_ID = "1234";
    private static final String COURIER_ID = "1234";
    private static final Courier COURIER = CourierControllerFixture.getCourier(true);
    private static final Guest GUEST = GuestControllerFixture.getGuest(true);

    @CreatedDate
    private static final LocalDateTime CREATED_DATE = LocalDateTime.now();
    @LastModifiedDate
    private static final LocalDateTime MODIFIED_DATE = CREATED_DATE;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private static final LocalTime ESTIMATED_DELIVERY_TIME = CREATED_DATE.toLocalTime()
            .plusMinutes(30);


    public static Order getOrder(boolean withId) {
        var order = Order.builder()
                .restaurant(RESTAURANT)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST)
                .courier(COURIER)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
        if (withId)
            order.setId("1234");
        return order;
    }
    public static Order getOrder( OrderStatus status) {
        return Order.builder()
                .id("1234")
                .restaurant(RESTAURANT)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(status)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST)
                .courier(COURIER)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }

    public static List<Order> getOrderList() {
        var list = new ArrayList<Order>();
        list.add(Order.builder()
                .id("1234")
                .restaurant(RESTAURANT)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST)
                .courier(COURIER)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build());
        return list;
    }

    public static Order getOrderGetNextStatus(OrderStatus fromStatus) {
        return Order.builder()
                .id("1234")
                .restaurant(RESTAURANT)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(fromStatus.getNextStatus())
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST)
                .courier(COURIER)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }
}
