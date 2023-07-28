package com.example.demoSpringRestaurant.fixtures.controller;

import com.example.demoSpringRestaurant.model.controller.Guest;
import com.example.demoSpringRestaurant.model.controller.Order;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GuestControllerFixture {
    private static final Order ACTIVE_ORDER = OrderControllerFixture.getOrder(true);
    private static final boolean PAYED = true;
    @CreatedDate
    private static final LocalDateTime CREATED_DATE = LocalDateTime.now();
    @LastModifiedDate
    private static final LocalDateTime MODIFIED_DATE = LocalDateTime.now();

    public static Guest getGuest(boolean withId) {
        var guest = Guest.builder()
                .activeOrder(ACTIVE_ORDER)
                .payed(PAYED)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
        if (withId)
            guest.setId("1234");
        return guest;
    }

    public static List<Guest> getGuestList() {
        var list = new ArrayList<Guest>();
        list.add(Guest.builder()
                .id("1234")
                .activeOrder(ACTIVE_ORDER)
                .payed(PAYED)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build());
        return list;
    }
}
