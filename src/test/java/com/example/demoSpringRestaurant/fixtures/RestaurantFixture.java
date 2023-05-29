package com.example.demoSpringRestaurant.fixtures;

import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import jakarta.validation.constraints.Email;

import java.util.ArrayList;
import java.util.List;


public class RestaurantFixture {

    private static final String NAME = "John's restaurant";
    private static final String OWNER = "John";
    private static final String ADDRESS = "Budapest xy street 6.";
    @Email
    private static final String EMAIL = "asd@dsa.com"; // TODO email not verified
    private static final String PHONE_NUMBER = "PhoneNumber";
    private static final Integer NUMBER_OF_TABLES = 45;
    private static final Boolean IS_VEGAN = false;
    private static final Boolean CAN_DELIVER = false;

    public static RestaurantCreationDto getRestaurantCreationDto() {
        return RestaurantCreationDto.builder()
                .name(NAME)
                .owner(OWNER)
                .address(ADDRESS)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .numberOfTables(NUMBER_OF_TABLES)
                .isVegan(IS_VEGAN)
                .canDeliver(CAN_DELIVER)
                .build();
    }

    public static RestaurantUpdateDto getRestaurantUpdateDto() {
        return RestaurantUpdateDto.builder()
                .name(NAME)
                .owner(OWNER)
                .address(ADDRESS)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .numberOfTables(NUMBER_OF_TABLES)
                .isVegan(IS_VEGAN)
                .canDeliver(CAN_DELIVER)
                .build();
    }

    public static RestaurantEntity getRestaurantEntity(boolean withId) {

        var restaurantEntity = RestaurantEntity.builder()
                .name(NAME)
                .owner(OWNER)
                .address(ADDRESS)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .numberOfTables(NUMBER_OF_TABLES)
                .isVegan(IS_VEGAN)
                .canDeliver(CAN_DELIVER)
                .build();
        if (withId)
            restaurantEntity.setId(1L);
        return restaurantEntity;
    }

    public static RestaurantDto getRestaurantDto() {
        return RestaurantDto.builder()
                .id(1L)
                .name(NAME)
                .owner(OWNER)
                .address(ADDRESS)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .numberOfTables(NUMBER_OF_TABLES)
                .isVegan(IS_VEGAN)
                .canDeliver(CAN_DELIVER)
                .build();
    }

    public static List<RestaurantEntity> getRestaurantEntityList() {
        var list = new ArrayList<RestaurantEntity>();
        list.add(RestaurantEntity.builder()
                .id(1L)
                .name(NAME)
                .owner(OWNER)
                .address(ADDRESS)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .numberOfTables(NUMBER_OF_TABLES)
                .isVegan(IS_VEGAN)
                .canDeliver(CAN_DELIVER)
                .build());
        return list;
    }

    public static List<RestaurantDto> getRestaurantDtoList() {
        var list = new ArrayList<RestaurantDto>();
        list.add(RestaurantDto.builder()
                .id(1L)
                .name(NAME)
                .owner(OWNER)
                .address(ADDRESS)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .numberOfTables(NUMBER_OF_TABLES)
                .isVegan(IS_VEGAN)
                .canDeliver(CAN_DELIVER)
                .build());
        return list;
    }
}
