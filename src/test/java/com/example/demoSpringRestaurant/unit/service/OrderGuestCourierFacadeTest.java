package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderGuestCourierFacade;
import com.example.demoSpringRestaurant.fixtures.service.CourierFixture;
import com.example.demoSpringRestaurant.fixtures.service.GuestFixture;
import com.example.demoSpringRestaurant.fixtures.service.OrderFixture;
import com.example.demoSpringRestaurant.service.CourierService;
import com.example.demoSpringRestaurant.service.GuestService;
import com.example.demoSpringRestaurant.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@ExtendWith(MockitoExtension.class)
public class OrderGuestCourierFacadeTest {

    @InjectMocks
    private OrderGuestCourierFacade orderGuestCourierFacade;
    @Mock
    private CourierService courierService;
    @Mock
    private OrderService orderService;
    @Mock
    private GuestService guestService;

    @Test
    void deleteOrder() throws DocumentNotFoundException {
        when(orderService.findOrderById(anyString()))
                .thenReturn(OrderFixture.getOrderDto());
        when(guestService.findGuestDtoByActiveOrder_Id(anyString()))
                .thenReturn(GuestFixture.getGuestDto());
        when(courierService.findCourierDtoByActiveOrder_Id(anyString()))
                .thenReturn(CourierFixture.getCourierDto());
        doNothing().when(orderService).deleteById(anyString());
        doNothing().when(courierService).deleteById(anyString());
        doNothing().when(guestService).deleteById(anyString());

        var order = orderGuestCourierFacade.deleteOrder("1234");

        assertThat(order).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
        verify(orderService,times(1)).findOrderById(anyString());
        verify(guestService,times(1)).findGuestDtoByActiveOrder_Id(anyString());
        verify(courierService,times(1)).findCourierDtoByActiveOrder_Id(anyString());
        verify(orderService,times(1)).deleteById(anyString());
        verify(guestService,times(1)).deleteById(anyString());
        verify(courierService,times(1)).deleteById(anyString());
        verifyNoMoreInteractions(orderService,guestService,courierService);
    }
}
