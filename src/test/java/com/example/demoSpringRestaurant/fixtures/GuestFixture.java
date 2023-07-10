package com.example.demoSpringRestaurant.fixtures;

import com.example.demoSpringRestaurant.model.GuestCreationDto;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.model.GuestUpdateDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data

public class GuestFixture {
    private static final OrderDocument ACTIVE_ORDER = null;
    private static final OrderDto ACTIVE_ORDER_DTO = null;
    private static final boolean PAYED = true;
    @CreatedDate
    private static final LocalDateTime CREATED_DATE = LocalDateTime.now();
    @LastModifiedDate
    private static final LocalDateTime MODIFIED_DATE = LocalDateTime.now();

    public static GuestCreationDto getGuestCreationDto() {
        return GuestCreationDto.builder().
                activeOrder(ACTIVE_ORDER_DTO).
                payed(PAYED).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();

    }

    public static GuestUpdateDto getGuestUpdateDto() {
        return GuestUpdateDto.builder().
                activeOrder(ACTIVE_ORDER_DTO).
                payed(PAYED).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();

    }

    public static GuestUpdateDto getGuestUpdateDtoSetEverythingToNull() {
        return GuestUpdateDto.builder().
                activeOrder(ACTIVE_ORDER_DTO).
                payed(PAYED).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();

    }

    public static GuestDocument getGuestDocument(boolean withId) {

        var restaurantDocument = GuestDocument.builder().
                activeOrder(ACTIVE_ORDER).
                payed(PAYED).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();

        if (withId)
            restaurantDocument.setId("1234");
        return restaurantDocument;
    }

    public static GuestDto getGuestDto() {
        return GuestDto.builder().
                id("1234").
                activeOrder(ACTIVE_ORDER_DTO).
                payed(PAYED).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();
    }

    public static GuestDocument getGuestDocument(String id) {
        return GuestDocument.builder().
                activeOrder(ACTIVE_ORDER).
                payed(PAYED).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                id(id).
                build();

    }

    public static GuestDto getGuestDto(String id) {
        return GuestDto.builder().
                id("1234").
                activeOrder(ACTIVE_ORDER_DTO).
                payed(PAYED).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                id(id).
                build();

    }

    public static List<GuestDocument> getGuestDocumentList() {
        var list = new ArrayList<GuestDocument>();
        list.add(GuestDocument.builder().
                id("1234").

                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build());

        return list;
    }

    public static List<GuestDto> getGuestDtoList() {
        var list = new ArrayList<GuestDto>();
        list.add(GuestDto.builder().
                id("1234").
                activeOrder(ACTIVE_ORDER_DTO).
                payed(PAYED).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build());

        return list;
    }

    public static GuestUpdateDto getUpdatedGuestUpdateDto() {
        return GuestUpdateDto.builder().
                activeOrder(ACTIVE_ORDER_DTO).
                payed(PAYED).
                createdDate(CREATED_DATE).
                modifiedDate(MODIFIED_DATE).
                build();


    }
}
