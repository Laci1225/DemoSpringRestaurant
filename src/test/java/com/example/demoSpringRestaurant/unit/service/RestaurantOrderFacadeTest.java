package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import com.example.demoSpringRestaurant.persistance.document.RestaurantDocument;
import com.example.demoSpringRestaurant.service.OrderService;
import com.example.demoSpringRestaurant.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantOrderFacadeTest {
    @InjectMocks
    private RestaurantOrderFacade restaurantOrderFacade;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private OrderService orderService;
    @Mock
    private RestaurantService restaurantService;

    @Test
    void getOrdersByRestaurantIdShouldReturnOneOrder() throws RestaurantDocumentNotFoundException {
        when(orderMapper.fromDocumentToOrderDto(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDto());
        when(orderService.findAllByRestaurantId(anyString()))
                .thenReturn(OrderFixture.getOrderDocumentList());

        var orderDtoList = restaurantOrderFacade.getOrdersByRestaurantId(RestaurantFixture.getRestaurantDto().getId());

        assertThat(orderDtoList).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDtoList());
        assertThat(orderDtoList).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDtoList());
        verify(orderService, times(1)).findAllByRestaurantId(anyString());
    }

    @Test
    void getOrdersByRestaurantIdShouldThrowRestaurantDocumentNotFoundException() throws RestaurantDocumentNotFoundException {
        Mockito.doThrow(RestaurantDocumentNotFoundException.class).when(restaurantService).restaurantExist(anyString());

        assertThrows(RestaurantDocumentNotFoundException.class, () ->
                restaurantOrderFacade.getOrdersByRestaurantId("1L"));

        verify(restaurantService, times(1)).restaurantExist(anyString());
    }

    /*@Test
    void createOrderShouldCreateOneOrderDocument() throws RestaurantDocumentNotFoundException {
        when(orderMapper.fromDocumentToOrderDto(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDto());
        when(orderMapper.fromOrderCreationDtoToDocument(any(OrderCreationDto.class)))
                .thenReturn(OrderFixture.getOrderDocument(false));
        when(restaurantService.findRestaurantById(anyString()))
                .thenReturn((RestaurantFixture.getRestaurantDocument(true)));
        when(orderService.saveOrder(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDto());

        var orderDto = restaurantOrderFacade.createOrder(OrderFixture.getOrderCreationDto(), "1L");

        assertThat(orderDto).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
        verify(restaurantService, times(1)).findRestaurantById(anyString());
        verify(orderMapper, times(1)).fromDocumentToOrderDto(any(OrderDocument.class));
        verify(orderMapper, times(1)).fromOrderCreationDtoToDocument(any(OrderCreationDto.class));
        verify(orderService, times(1)).saveOrder(any(OrderDocument.class));
    }

    @Test
    void createOrderShouldThrowRestaurantNotFoundException() throws RestaurantDocumentNotFoundException {
        when(restaurantService.findRestaurantById(anyString()))
                .thenThrow(RestaurantDocumentNotFoundException.class);

        assertThrows(RestaurantDocumentNotFoundException.class, () ->
                restaurantOrderFacade.createOrder(OrderFixture.getOrderCreationDto(), "1L"));

        verify(restaurantService, times(1)).findRestaurantById(anyString());
        verifyNoMoreInteractions(restaurantService);
    }*/

    @Test
    void deleteRestaurantShouldRemoveARestaurantAndItsOrders() throws RestaurantDocumentNotFoundException {
        when(restaurantMapper.fromDocumentToRestaurantDto(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(orderService.findAllByRestaurantId(anyString()))
                .thenReturn(OrderFixture.getOrderDocumentList());
        when(restaurantService.findRestaurantById(anyString()))
                .thenReturn(RestaurantFixture.getRestaurantDocument(true));
        Mockito.doNothing().when(orderService).deleteAllOrder(Mockito.anyList());
        Mockito.doNothing().when(restaurantService).deleteRestaurant(Mockito.any(RestaurantDocument.class));

        var restaurantDto = restaurantOrderFacade.deleteRestaurant("1L");

        assertThat(restaurantDto).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantMapper, times(1)).fromDocumentToRestaurantDto(any(RestaurantDocument.class));
        verify(orderService, times(1)).findAllByRestaurantId(anyString());
        verify(restaurantService, times(1)).findRestaurantById(anyString());
        verify(orderService, times(1)).deleteAllOrder(anyList());
        verify(restaurantService, times(1)).deleteRestaurant(any(RestaurantDocument.class));
    }

    @Test
    void deleteRestaurantShouldThrowRestaurantNotFoundException() throws RestaurantDocumentNotFoundException {
        when(restaurantService.findRestaurantById(anyString()))
                .thenThrow(RestaurantDocumentNotFoundException.class);

        assertThrows(RestaurantDocumentNotFoundException.class, () ->
                restaurantOrderFacade.deleteRestaurant("1L"));

        verify(restaurantService, times(1)).findRestaurantById(anyString());
        verifyNoMoreInteractions(restaurantService);
    }
}
