package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.fixtures.CourierFixture;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.mapper.CourierMapper;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import com.example.demoSpringRestaurant.persistance.repository.CourierRepository;
import com.example.demoSpringRestaurant.service.CourierService;
import com.example.demoSpringRestaurant.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    @Mock
    private CourierMapper courierMapper;
    @Mock
    private OrderMapper orderMapper;

    @Test
    void addOrderToCourier() throws CourierDocumentNotFoundException {

    }

    /*@Test
    void deleteCourier() throws DocumentNotFoundException {
        when(courierService.findById(anyString()))
                .thenReturn(Optional.of(CourierFixture.getCourierDocument(true)));
        Mockito.doNothing().when(orderService).deleteAllOrder(Mockito.anyList());

        //doNothing().when(orderService).deleteAllOrder(anyList());//TODO why null
        doNothing().when(courierService).deleteById(anyString());
        when(courierMapper.fromDocumentToCourierDto(CourierFixture.getCourierDocument(true)))
                .thenReturn(CourierFixture.getCourierDto());

        var courierDto = orderCourierFacade.deleteCourier(CourierFixture.getCourierDto().getId());
        assertThat(courierDto).usingRecursiveComparison().isEqualTo(CourierFixture.getCourierDto());
        verify(orderService, times(1)).deleteAllOrder(null);
        verify(courierService, times(1)).deleteById(anyString());
        verifyNoMoreInteractions();
        verifyNoMoreInteractions(orderService);
    }*/

    @Test
    void setOrderActive() throws CourierDocumentNotFoundException, OrderDocumentNotFoundException {
        when(courierService.findById(anyString()))
                .thenReturn(Optional.of(CourierFixture.getCourierDocument(true)));
        when(orderService.findById(anyString()))
                .thenReturn(Optional.of(OrderFixture.getOrderDocument(true)));
        when(courierMapper.fromDocumentToCourierDto(any(CourierDocument.class)))
                .thenReturn(CourierFixture.getCourierDto());

        var courierDto = orderCourierFacade.setOrderActive(CourierFixture.getCourierDto().getId(),
                OrderFixture.getOrderDto().getId());

        assertThat(courierDto).usingRecursiveComparison().isEqualTo(CourierFixture.getCourierDto());
        verify(orderService, times(1)).findById(anyString());
        verify(courierService, times(1)).findById(anyString());
        verifyNoMoreInteractions(orderService, courierService);
    }

    @Test
    void setCourierToOrder() throws CourierDocumentNotFoundException, OrderDocumentNotFoundException {
        when(courierService.findById(anyString()))
                .thenReturn(Optional.of(CourierFixture.getCourierDocument(true)));
        when(orderService.findById(anyString()))
                .thenReturn(Optional.of(OrderFixture.getOrderDocument(true)));
        when(orderMapper.fromDocumentToOrderDto(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDto());

        var orderDto = orderCourierFacade.setCourierToOrder(CourierFixture.getCourierDto().getId(),
                OrderFixture.getOrderDto().getId());

        assertThat(orderDto).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
        verify(orderService, times(1)).findById(anyString());
        verify(courierService, times(1)).findById(anyString());
        verifyNoMoreInteractions(orderService, courierService);

    }
}
