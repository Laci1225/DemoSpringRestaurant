package com.example.demoSpringRestaurant.fixtures;

import com.example.demoSpringRestaurant.constant.Vehicle;
import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import com.example.demoSpringRestaurant.persistance.document.RestaurantDocument;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class CourierFixture {
    private static final String NAME = "Sam";
    private static final boolean IS_ACTIVE = true;
    private static final List<OrderDocument> ORDERS = null;
    private static final List<OrderDto> ORDERS_DTO = null;
    private static final OrderDocument ACTIVE_ORDER = null;
    private static final OrderDto ACTIVE_ORDER_DTO = null;
    private static final double PAYMENT_PER_ORDER = 12.0;
    private static final Vehicle VEHICLE = Vehicle.CAR;
    @CreatedDate
    private static final LocalDateTime CREATED_DATE = LocalDateTime.now();
    @LastModifiedDate
    private static final LocalDateTime MODIFIED_DATE = LocalDateTime.now();

    public static CourierCreationDto getCourierCreationDto() {
        return CourierCreationDto.builder().
                name(NAME).
                isActive(IS_ACTIVE).
                orders(ORDERS_DTO).
                activeOrder(ACTIVE_ORDER_DTO).
                paymentPerOrder(PAYMENT_PER_ORDER).
                vehicle(VEHICLE).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();

    }

    public static CourierUpdateDto getCourierUpdateDto() {
        return CourierUpdateDto.builder().
                name(NAME).
                isActive(IS_ACTIVE).
                orders(ORDERS_DTO).
                activeOrder(ACTIVE_ORDER_DTO).
                paymentPerOrder(PAYMENT_PER_ORDER).
                vehicle(VEHICLE).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();

    }

    public static CourierUpdateDto getCourierUpdateDtoSetEverythingToNull() {
        return CourierUpdateDto.builder().
                name(NAME).
                isActive(IS_ACTIVE).
                orders(ORDERS_DTO).
                activeOrder(ACTIVE_ORDER_DTO).
                paymentPerOrder(PAYMENT_PER_ORDER).
                vehicle(VEHICLE).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();

    }

    public static CourierDocument getCourierDocument(boolean withId) {

        var courierDocument = CourierDocument.builder().
                name(NAME).
                isActive(IS_ACTIVE).
                orders(ORDERS).
                activeOrder(ACTIVE_ORDER).
                paymentPerOrder(PAYMENT_PER_ORDER).
                vehicle(VEHICLE).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();

        if (withId)
            courierDocument.setId("1234");
        return courierDocument;
    }

    public static CourierDto getCourierDto() {
        return CourierDto.builder().
                id("1234").
                name(NAME).
                isActive(IS_ACTIVE).
                orders(ORDERS_DTO).
                activeOrder(ACTIVE_ORDER_DTO).
                paymentPerOrder(PAYMENT_PER_ORDER).
                vehicle(VEHICLE).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();
    }

    public static CourierDto getCourierDto(String id) {
        return CourierDto.builder().
                id("1234").
                name(NAME).
                isActive(IS_ACTIVE).
                orders(ORDERS_DTO).
                activeOrder(ACTIVE_ORDER_DTO).
                paymentPerOrder(PAYMENT_PER_ORDER).
                vehicle(VEHICLE).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                id(id).
                build();
    }
    public static CourierDocument getCourierDocument(String id) {
        return CourierDocument.builder().
                name(NAME).
                isActive(IS_ACTIVE).
                orders(ORDERS).
                activeOrder(ACTIVE_ORDER).
                paymentPerOrder(PAYMENT_PER_ORDER).
                vehicle(VEHICLE).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                id(id).
                build();

    }

    public static List<CourierDocument> getCourierDocumentList() {
        var list = new ArrayList<CourierDocument>();
        list.add(CourierDocument.builder().
                id("1234").
                name(NAME).
                isActive(IS_ACTIVE).
                orders(ORDERS).
                activeOrder(ACTIVE_ORDER).
                paymentPerOrder(PAYMENT_PER_ORDER).
                vehicle(VEHICLE).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build());

        return list;
    }

    public static List<CourierDto> getCourierDtoList() {
        var list = new ArrayList<CourierDto>();
        list.add(CourierDto.builder().
                id("1234").
                name(NAME).
                isActive(IS_ACTIVE).
                orders(ORDERS_DTO).
                activeOrder(ACTIVE_ORDER_DTO).
                paymentPerOrder(PAYMENT_PER_ORDER).
                vehicle(VEHICLE).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build());

        return list;
    }

    public static CourierUpdateDto getUpdatedCourierUpdateDto() {
        return CourierUpdateDto.builder().
                name(NAME).
                isActive(IS_ACTIVE).
                orders(ORDERS_DTO).
                activeOrder(ACTIVE_ORDER_DTO).
                paymentPerOrder(PAYMENT_PER_ORDER).
                vehicle(VEHICLE).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();


    }

    public static Optional<CourierDto> getOptionalCourierDto() {
        return Optional.of(CourierDto.builder().
                name(NAME).
                isActive(IS_ACTIVE).
                orders(ORDERS_DTO).
                activeOrder(ACTIVE_ORDER_DTO).
                paymentPerOrder(PAYMENT_PER_ORDER).
                vehicle(VEHICLE).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build());

    }
}
