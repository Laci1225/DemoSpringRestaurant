package com.example.demoSpringRestaurant.fixtures.controller;

import com.example.demoSpringRestaurant.constant.Vehicle;
import com.example.demoSpringRestaurant.model.controller.Courier;
import com.example.demoSpringRestaurant.model.controller.Order;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourierControllerFixture {
    private static final String NAME = "Sam";
    private static final boolean ACTIVE = true;
    private static final List<Order> ORDERS = OrderControllerFixture.getOrderList();
    private static final Order ACTIVE_ORDER = OrderControllerFixture.getOrder(true);
    private static final double PAYMENT_PER_ORDER = 12.0;
    private static final Vehicle VEHICLE = Vehicle.CAR;
    @CreatedDate
    private static final LocalDateTime CREATED_DATE = LocalDateTime.now();
    @LastModifiedDate
    private static final LocalDateTime MODIFIED_DATE = LocalDateTime.now();

    public static Courier getCourier(boolean withId) {
        var courier = Courier.builder()
                .name(NAME)
                .active(ACTIVE)
                .orders(ORDERS)
                .activeOrder(ACTIVE_ORDER)
                .paymentPerOrder(PAYMENT_PER_ORDER)
                .vehicle(VEHICLE)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();

        if (withId)
            courier.setId("1234");
        return courier;
    }

    public static List<Courier> getCourierList() {
        var list = new ArrayList<Courier>();
        list.add(Courier.builder()
                .id("1234")
                .name(NAME)
                .active(ACTIVE)
                .orders(ORDERS)
                .activeOrder(ACTIVE_ORDER)
                .paymentPerOrder(PAYMENT_PER_ORDER)
                .vehicle(VEHICLE)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build());
        return list;
    }
}
