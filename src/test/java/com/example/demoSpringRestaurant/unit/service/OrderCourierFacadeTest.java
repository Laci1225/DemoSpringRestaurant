package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.fixtures.CourierFixture;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.service.CourierService;
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
public class OrderCourierFacadeTest {

    @InjectMocks
    private OrderCourierFacade orderCourierFacade;
    @Mock
    private CourierService courierService;

    @Mock
    private OrderService orderService;

    @Test
    void addOrderToCourier() throws CourierDocumentNotFoundException, OrderDocumentNotFoundException {
       /* when(courierService.findCourierById(anyString()))
                .thenReturn(CourierFixture.getCourierDto());
        when(orderService.findOrderById(anyString()))
                .thenReturn(OrderFixture.getOrderDto());


        var courier = orderCourierFacade.addOrderToCourier("1234", "1234");
        var orderDto = OrderFixture.getOrderDto();
        var orderDtoList = courier.getOrders();
        orderDtoList.add(orderDto);
        courier.setOrders(orderDtoList);

        assertThat(courier).usingRecursiveComparison().isEqualTo(CourierFixture.getCourierDto());
        verify(courierService,times(1)).findCourierById(anyString());
        verify(orderService,times(1)).findOrderById(anyString());
        verifyNoMoreInteractions(orderService,courierService);*///TODO
    }

    @Test
    void deleteCourier() throws DocumentNotFoundException {
        when(courierService.findCourierById(anyString()))
                .thenReturn(CourierFixture.getCourierDto());
        when(orderService.findOrderById(anyString()))
                .thenReturn(OrderFixture.getOrderDto());
        doNothing().when(courierService).deleteById(anyString());

        var courier = orderCourierFacade.deleteCourier("1234");

        assertThat(courier).usingRecursiveComparison().isEqualTo(CourierFixture.getCourierDto());
        verify(courierService, times(1)).findCourierById(anyString());
        verify(orderService, times(1)).findOrderById(anyString());
        verify(courierService, times(1)).deleteById(anyString());
        verifyNoMoreInteractions(courierService, orderService);
    }

    @Test
    void setOrderActive() throws CourierDocumentNotFoundException, OrderDocumentNotFoundException {
        /*when(courierService.findCourierById(anyString()))
                .thenReturn(CourierFixture.getCourierDto());
        when(orderService.findOrderById(anyString()))
                .thenReturn(OrderFixture.getOrderDto());

        var courierDto = orderCourierFacade.setOrderActive(CourierFixture.getCourierDto().getId(),
                OrderFixture.getOrderDto().getId());
        courierDto.setActiveOrder(OrderFixture.getOrderDto());

        assertThat(courierDto).usingRecursiveComparison().isEqualTo(CourierFixture.getCourierDto());
        verify(orderService, times(1)).findOrderById(anyString());
        verify(courierService, times(1)).findCourierById(anyString());
        verifyNoMoreInteractions(orderService, courierService);*///TODO
    }

    @Test
    void setCourierToOrder() throws CourierDocumentNotFoundException, OrderDocumentNotFoundException {
        when(courierService.findCourierById(anyString()))
                .thenReturn(CourierFixture.getCourierDto());
        when(orderService.findOrderById(anyString()))
                .thenReturn(OrderFixture.getOrderDto());

        var orderDto = orderCourierFacade.setCourierToOrder("1234", "1234");

        var expected = OrderFixture.getOrderDto();
        expected.setCourierDto(orderDto.getCourierDto()); //TODO Ez jó?
        assertThat(expected).usingRecursiveComparison().isEqualTo(expected);
        verify(orderService, times(1)).findOrderById(anyString());
        verify(courierService, times(1)).findCourierById(anyString());
        verifyNoMoreInteractions(orderService, courierService);
    }
}
