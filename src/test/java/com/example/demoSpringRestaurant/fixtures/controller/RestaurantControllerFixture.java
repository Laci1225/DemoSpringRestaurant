package com.example.demoSpringRestaurant.fixtures.controller;

import com.example.demoSpringRestaurant.model.controller.Order;
import com.example.demoSpringRestaurant.model.controller.Restaurant;
import jakarta.validation.constraints.Email;

import java.util.ArrayList;
import java.util.List;


public class RestaurantControllerFixture {

    private static final String NAME = "John's restaurant";
    private static final String OWNER = "John";
    private static final String ADDRESS = "Budapest xy street 6.";
    private static final List<Order> ORDERS = OrderControllerFixture.getOrderList();
    @Email
    private static final String EMAIL = "asd@dsa.com";
    private static final String PHONE_NUMBER = "PhoneNumber";
    private static final Integer NUMBER_OF_TABLES = 45;
    private static final Boolean IS_VEGAN = false;
    private static final Boolean CAN_DELIVER = false;
    private static final Boolean IS_ON_WOLT = false;


    public static Restaurant getRestaurant(boolean withId) {

        var restaurant = Restaurant.builder()
                .name(NAME)
                .owner(OWNER)
                .address(ADDRESS)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .numberOfTables(NUMBER_OF_TABLES)
                .isVegan(IS_VEGAN)
                .canDeliver(CAN_DELIVER)
                .isOnWolt(IS_ON_WOLT)
                .build();
        if (withId)
            restaurant.setId("1234");
        return restaurant;
    }

    public static List<Restaurant> getRestaurantList() {
            var list = new ArrayList<Restaurant>();
            list.add(Restaurant.builder()
                    .id("1234")
                    .name(NAME)
                    .owner(OWNER)
                    .address(ADDRESS)
                    .email(EMAIL)
                    .phoneNumber(PHONE_NUMBER)
                    .numberOfTables(NUMBER_OF_TABLES)
                    .isVegan(IS_VEGAN)
                    .canDeliver(CAN_DELIVER)
                    .isOnWolt(IS_ON_WOLT)
                    .build());
            return list;
        }
    }

