package com.example.demoSpringRestaurant.fixtures.service;

import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.model.service.*;
import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import com.example.demoSpringRestaurant.persistance.document.RestaurantDocument;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderFixture {

    private static final RestaurantDocument RESTAURANT_DOCUMENT = RestaurantFixture.getRestaurantDocument(true);
    private static final RestaurantDto RESTAURANT_DTO = RestaurantFixture.getRestaurantDto();
    private static final MealType MEAL_TYPE = MealType.RICEANDFISH;
    private static final DrinkType DRINK_TYPE = DrinkType.COLA;
    private static final double PRICE = MEAL_TYPE.getValue() + DRINK_TYPE.getValue();
    private static final String DELIVERY_ADDRESS = "Budapest Heroes' Square";
    private static final OrderStatus ORDER_STATUS = OrderStatus.SENT;
    private static final String GUEST_ID = "1234";
    private static final String COURIER_ID = "1234";
    private static final CourierDocument COURIER_DOCUMENT = CourierFixture.getCourierDocument(true);
    private static final CourierDto COURIER_DTO = CourierFixture.getCourierDto();
    private static final GuestDocument GUEST_DOCUMENT =  GuestFixture.getGuestDocument(true);
    private static final GuestDto GUEST_DTO =  GuestFixture.getGuestDto();

    @CreatedDate
    private static final LocalDateTime CREATED_DATE = LocalDateTime.now();
    @LastModifiedDate
    private static final LocalDateTime MODIFIED_DATE = CREATED_DATE;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private static final LocalTime ESTIMATED_DELIVERY_TIME = CREATED_DATE.toLocalTime()
            .plusMinutes(30);


    public static OrderDto getOrderDto() {
        return OrderDto.builder()
                .id("1234")
                .restaurant(RESTAURANT_DTO)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DTO)
                .courier(COURIER_DTO)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }
    public static OrderDto getOrderDtoWithoutId() {
        return OrderDto.builder()
                .restaurant(RESTAURANT_DTO)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DTO)
                .courier(COURIER_DTO)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }

    public static OrderDto getOrderDtoGetNextStatus(OrderStatus fromStatus) {
        return OrderDto.builder()
                .id("1234")
                .restaurant(RESTAURANT_DTO)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(fromStatus.getNextStatus())
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DTO)
                .courier(COURIER_DTO)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }

    public static OrderDocument getOrderDocument(boolean withId) {
        var orderDocument = OrderDocument.builder()
                .restaurant(RESTAURANT_DOCUMENT)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DOCUMENT)
                .courier(COURIER_DOCUMENT)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
        if (withId)
            orderDocument.setId("1234");
        return orderDocument;
    }

    public static OrderDocument getOrderDocument(String id) {
        return OrderDocument.builder()
                .restaurant(RESTAURANT_DOCUMENT)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DOCUMENT)
                .courier(COURIER_DOCUMENT)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .id(id)
                .build();
    }

    public static OrderCreationDto getOrderCreationDto() {
        return OrderCreationDto.builder()
                .restaurant(RESTAURANT_DTO)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guestId(GUEST_ID)
                .courierId(COURIER_ID)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }
    public static OrderUpdateDto getOrderUpdateDto() {
        return OrderUpdateDto.builder()
                .restaurant(RESTAURANT_DTO)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DTO)
                .courier(COURIER_DTO)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }

    public static List<OrderDto> getOrderDtoList() {
        var list = new ArrayList<OrderDto>();
        list.add(OrderDto.builder()
                .id("1234")
                .restaurant(RESTAURANT_DTO)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DTO)
                .courier(COURIER_DTO )
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build());
        return list;
    }

    public static OrderUpdateDto getorderUpdateDto() {
        return OrderUpdateDto.builder()
                .restaurant(RESTAURANT_DTO)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DTO)
                .courier(COURIER_DTO)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }

    public static OrderDocument getOrderDocumentToGivenRestaurant(boolean withId, RestaurantDocument restaurantDocument) {
        var orderDocument = OrderDocument.builder()
                .restaurant(restaurantDocument)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DOCUMENT)
                .courier(COURIER_DOCUMENT)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
        if (withId)
            orderDocument.setId("1234");
        return orderDocument;
    }

    public static OrderDto getOrderDtoToGivenRestaurant(RestaurantDto restaurantDto) {
        return OrderDto.builder()
                .restaurant(restaurantDto)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DTO)
                .courier(COURIER_DTO)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }

    public static OrderDto getOrderDtoGetNextStatusToGivenRestaurant(OrderStatus fromStatus, RestaurantDto restaurantDto) {
        return OrderDto.builder()
                .id("1234")
                .restaurant(restaurantDto)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(fromStatus.getNextStatus())
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DTO)
                .courier(COURIER_DTO)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }

    public static List<OrderDocument> getOrderDocumentList() {
        var list = new ArrayList<OrderDocument>();
        list.add(OrderDocument.builder()
                .id("1234")
                .restaurant(RESTAURANT_DOCUMENT)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .estimatedPreparationTime(ESTIMATED_DELIVERY_TIME)
                .guest(GUEST_DOCUMENT)
                .courier(COURIER_DOCUMENT)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build());
        return list;
    }
}
