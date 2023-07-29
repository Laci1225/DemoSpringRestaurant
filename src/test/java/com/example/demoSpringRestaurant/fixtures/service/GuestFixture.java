package com.example.demoSpringRestaurant.fixtures.service;

import com.example.demoSpringRestaurant.model.service.GuestCreationDto;
import com.example.demoSpringRestaurant.model.service.GuestDto;
import com.example.demoSpringRestaurant.model.service.GuestUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GuestFixture {
    private static final String ACTIVE_ORDER = null;//OrderFixture.getOrderDocument(true);
    private static final String ACTIVE_ORDER_DTO = null;//OrderFixture.getOrderDto();
    private static final boolean PAYED = true;
    @CreatedDate
    private static final LocalDateTime CREATED_DATE = LocalDateTime.now();
    @LastModifiedDate
    private static final LocalDateTime MODIFIED_DATE = LocalDateTime.now();

    public static GuestCreationDto getGuestCreationDto() {
        return GuestCreationDto.builder()
                .activeOrder(ACTIVE_ORDER_DTO)
                .payed(PAYED)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }

    public static GuestUpdateDto getGuestUpdateDto() {
        return GuestUpdateDto.builder()
                .activeOrder(ACTIVE_ORDER_DTO)
                .payed(PAYED)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }


    public static GuestDocument getGuestDocument(boolean withId) {
        var restaurantDocument = GuestDocument.builder()
                .activeOrder(ACTIVE_ORDER)
                .payed(PAYED)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();

        if (withId)
            restaurantDocument.setId("1234");
        return restaurantDocument;
    }

    public static GuestDto getGuestDto() {
        return GuestDto.builder()
                .id("1234")
                .activeOrder(ACTIVE_ORDER_DTO)
                .payed(PAYED)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }
    public static GuestDto getGuestDtoWithActiveOrder() {
        return GuestDto.builder()
                .id("1234")
                .activeOrder("4321")
                .payed(PAYED)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }

    public static GuestDocument getGuestDocument(String id) {
        return GuestDocument.builder()
                .activeOrder(ACTIVE_ORDER)
                .payed(PAYED)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .id(id)
                .build();
    }

    public static GuestDto getGuestDto(String id) {
        return GuestDto.builder()
                .id("1234")
                .activeOrder(ACTIVE_ORDER_DTO)
                .payed(PAYED)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .id(id)
                .build();
    }

    public static List<GuestDocument> getGuestDocumentList() {
        var list = new ArrayList<GuestDocument>();
        list.add(GuestDocument.builder()
                .id("1234")
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build());

        return list;
    }

    public static List<GuestDto> getGuestDtoList() {
        var list = new ArrayList<GuestDto>();
        list.add(GuestDto.builder()
                .id("1234")
                .activeOrder(ACTIVE_ORDER_DTO)
                .payed(PAYED)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build());

        return list;
    }

    public static GuestUpdateDto getUpdatedGuestUpdateDto() {
        return GuestUpdateDto.builder()
                .activeOrder(ACTIVE_ORDER_DTO)
                .payed(PAYED)
                .createdDate(CREATED_DATE)
                .modifiedDate(MODIFIED_DATE)
                .build();
    }
}
