package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderGuestCourierFacade;
import com.example.demoSpringRestaurant.fixtures.CourierFixture;
import com.example.demoSpringRestaurant.fixtures.GuestFixture;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.service.CourierMapper;
import com.example.demoSpringRestaurant.mapper.service.GuestMapper;
import com.example.demoSpringRestaurant.mapper.service.OrderMapper;
import com.example.demoSpringRestaurant.model.service.CourierDto;
import com.example.demoSpringRestaurant.model.service.GuestDto;
import com.example.demoSpringRestaurant.model.service.OrderCreationDto;
import com.example.demoSpringRestaurant.model.service.OrderDto;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import com.example.demoSpringRestaurant.service.CourierService;
import com.example.demoSpringRestaurant.service.GuestService;
import com.example.demoSpringRestaurant.service.OrderService;
import com.example.demoSpringRestaurant.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)

public class RestaurantOrderGuestCourierFacadeTest {

    @InjectMocks
    private RestaurantOrderGuestCourierFacade restaurantOrderGuestCourierFacade;
    @Mock
    private RestaurantService restaurantService;

    @Mock
    private OrderService orderService;
    @Mock
    private CourierMapper courierMapper;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private GuestService guestService;
    @Mock
    private GuestMapper guestMapper;
    @Mock
    private CourierService courierService;

    @Test
    void createOrder() throws DocumentNotFoundException {
        when(restaurantService.findRestaurantById(anyString()))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(courierService.findCourierById(anyString()))
                .thenReturn(CourierFixture.getCourierDto());
        when(guestService.findGuestById(anyString()))
                .thenReturn(GuestFixture.getGuestDto());
        when(orderMapper.fromOrderCreationDtoToDto(any(OrderCreationDto.class),any(CourierDto.class),any(GuestDto.class)))
                .thenReturn(OrderFixture.getOrderDto());
        when(orderMapper.fromOrderDtoToDocument(any(OrderDto.class)))
                .thenReturn(OrderFixture.getOrderDocument(true));
        when(orderMapper.fromDocumentToOrderDto(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDto());
        when(courierMapper.fromCourierDtoToDocument(any(CourierDto.class)))
                .thenReturn(CourierFixture.getCourierDocument(true));
        when(guestMapper.fromGuestDtoToDocument(any(GuestDto.class)))
                .thenReturn(GuestFixture.getGuestDocument(true));
        when(orderService.saveOrder(any(OrderDto.class)))
                .thenReturn(OrderFixture.getOrderDto());

        var order = restaurantOrderGuestCourierFacade.
                createOrder(OrderFixture.getOrderCreationDto(),"1234");

        assertThat(order).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
        verify(restaurantService,times(1)).findRestaurantById(anyString());
        verify(courierService,times(1)).findCourierById(anyString());
        verify(guestService,times(1)).findGuestById(anyString());
        verify(orderService,times(1)).saveOrder(any(OrderDto.class));
        verifyNoMoreInteractions(guestService,restaurantService,courierService,orderService);
    }
}
