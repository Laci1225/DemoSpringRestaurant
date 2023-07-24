package com.example.demoSpringRestaurant.fixtures;

import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import com.example.demoSpringRestaurant.persistance.document.RestaurantDocument;
import jakarta.validation.constraints.Email;

import java.util.ArrayList;
import java.util.List;


public class RestaurantFixture {

    private static final String NAME = "John's restaurant";
    private static final String OWNER = "John";
    private static final String ADDRESS = "Budapest xy street 6.";
    private static final List<OrderDocument> orders = null;
    private static final List<OrderDto> ordersDto = null;
    @Email
    private static final String EMAIL = "asd@dsa.com"; // TODO email not verified
    private static final String PHONE_NUMBER = "PhoneNumber";
    private static final Integer NUMBER_OF_TABLES = 45;
    private static final Boolean IS_VEGAN = false;
    private static final Boolean CAN_DELIVER = false;
    private static final Boolean IS_ON_WOLT = false;

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
                .isOnWolt(IS_ON_WOLT)
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
                .isOnWolt(IS_ON_WOLT)
                .build();
    }

    public static RestaurantUpdateDto getRestaurantUpdateDtoSetEverythingToNull() {
        return RestaurantUpdateDto.builder()
                .name(null)
                .owner(null)
                .address(null)
                .email(null)
                .phoneNumber(null)
                .numberOfTables(null)
                .isVegan(null)
                .canDeliver(null)
                .isOnWolt(null)
                .build();
    }

    public static RestaurantDocument getRestaurantDocument(boolean withId) {

        var restaurantDocument = RestaurantDocument.builder()
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
            restaurantDocument.setId("1L");
        return restaurantDocument;
    }

    public static RestaurantDto getRestaurantDto() {
        return RestaurantDto.builder()
                .id("1L")
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
    }

    public static RestaurantDto getRestaurantDto(String id) {
        return RestaurantDto.builder()
                .id(id)
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
    }

    public static List<RestaurantDocument> getRestaurantDocumentList() {
        var list = new ArrayList<RestaurantDocument>();
        list.add(RestaurantDocument.builder()
                .id("1L")
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

    public static List<RestaurantDto> getRestaurantDtoList() {
        var list = new ArrayList<RestaurantDto>();
        list.add(RestaurantDto.builder()
                .id("1L")
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

    public static RestaurantUpdateDto getUpdatedRestaurantUpdateDto() {
        return RestaurantUpdateDto.builder()
                .name(NAME + "Updated")
                .owner(OWNER)
                .address(ADDRESS)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .numberOfTables(NUMBER_OF_TABLES)
                .isVegan(IS_VEGAN)
                .canDeliver(CAN_DELIVER)
                .isOnWolt(IS_ON_WOLT)
                .build();
    }

    public static RestaurantDocument getRestaurantDocumentIsVegan(boolean withId) {

        var restaurantDocument = RestaurantDocument.builder()
                .name(NAME)
                .owner(OWNER)
                .address(ADDRESS)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .numberOfTables(NUMBER_OF_TABLES)
                .isVegan(true)
                .canDeliver(CAN_DELIVER)
                .isOnWolt(IS_ON_WOLT)
                .build();
        if (withId)
            restaurantDocument.setId("1L");
        return restaurantDocument;
    }
}
