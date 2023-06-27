package com.example.demoSpringRestaurant.fixtures;

import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.OrderUpdateDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import com.example.demoSpringRestaurant.persistance.document.RestaurantDocument;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderFixture {

    private static RestaurantMapper restaurantMapper;
    private static final RestaurantDocument RESTAURANT_DOCUMENT = new RestaurantDocument();
    private static final RestaurantDto RESTAURANT_DTO = restaurantMapper.fromDocumentToRestaurantDto(RESTAURANT_DOCUMENT);
    private static final MealType MEAL_TYPE = MealType.RICEANDFISH;
    private static final DrinkType DRINK_TYPE = DrinkType.COLA;
    private static final double PRICE = MEAL_TYPE.getValue() + DRINK_TYPE.getValue();
    private static final String DELIVERY_ADDRESS = "Budapest Heroes' Square";
    private static final LocalDateTime CREATE_DATE = LocalDateTime.now();
    private static final OrderStatus ORDER_STATUS = OrderStatus.SENT;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private static final LocalTime ESTIMATED_DELIVERY_TIME = CREATE_DATE.toLocalTime()
            .plusMinutes(30);


    public static OrderDto getOrderDto() {
        return OrderDto.builder()
                .id("1L")
                .restaurant(RESTAURANT_DTO)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .estimatedDeliveryTime(ESTIMATED_DELIVERY_TIME)
                .build();
    }

    public static OrderDto getOrderDtoGetNextStatus(OrderStatus fromStatus) {
        return OrderDto.builder()
                .id("1L")
                .restaurant(RESTAURANT_DTO)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(fromStatus.getNextStatus())
                .estimatedDeliveryTime(ESTIMATED_DELIVERY_TIME)
                .build();
    }

    public static OrderDocument getOrderDocument(boolean withId) {
        var orderDocument = OrderDocument.builder()
                .restaurant(RESTAURANT_DOCUMENT)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .estimatedDeliveryTime(ESTIMATED_DELIVERY_TIME)
                .build();
        if (withId)
            orderDocument.setId("1L");
        return orderDocument;
    }

    public static OrderCreationDto getOrderCreationDto() {
        return OrderCreationDto.builder()
                .restaurant(RESTAURANT_DTO)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .estimatedDeliveryTime(ESTIMATED_DELIVERY_TIME)
                .build();
    }

    public static List<OrderDto> getOrderDtoList() {
        var list = new ArrayList<OrderDto>();
        list.add(OrderDto.builder()
                .id("1L")
                .restaurant(RESTAURANT_DTO)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .estimatedDeliveryTime(ESTIMATED_DELIVERY_TIME)
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
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .estimatedDeliveryTime(ESTIMATED_DELIVERY_TIME)
                .build();
    }

    public static OrderDocument getOrderDocumentToGivenRestaurant(boolean withId, RestaurantDocument restaurantDocument) {
        var orderDocument = OrderDocument.builder()
                .restaurant(restaurantDocument)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .estimatedDeliveryTime(ESTIMATED_DELIVERY_TIME)
                .build();
        if (withId)
            orderDocument.setId("1L");
        return orderDocument;
    }

    public static OrderDto getOrderDtoToGivenRestaurant(RestaurantDto restaurantDto) {
        return OrderDto.builder()
                .restaurant(restaurantDto)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .estimatedDeliveryTime(ESTIMATED_DELIVERY_TIME)
                .build();
    }

    public static OrderDto getOrderDtoGetNextStatusToGivenRestaurant(OrderStatus fromStatus, RestaurantDto restaurantDto) {
        return OrderDto.builder()
                .id("1L")
                .restaurant(restaurantDto)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(fromStatus.getNextStatus())
                .estimatedDeliveryTime(ESTIMATED_DELIVERY_TIME)
                .build();
    }

    public static List<OrderDocument> getOrderDocumentList() {
        var list = new ArrayList<OrderDocument>();
        list.add(OrderDocument.builder()
                .id("1L")
                .restaurant(RESTAURANT_DOCUMENT)
                .mealType(MEAL_TYPE)
                .drinkType(DRINK_TYPE)
                .price(PRICE)
                .deliveryAddress(DELIVERY_ADDRESS)
                .createDate(CREATE_DATE)
                .orderStatus(ORDER_STATUS)
                .estimatedDeliveryTime(ESTIMATED_DELIVERY_TIME)
                .build());
        return list;
    }
}
