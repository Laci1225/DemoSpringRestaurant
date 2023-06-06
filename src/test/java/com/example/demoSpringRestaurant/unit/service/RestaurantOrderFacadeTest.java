package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.service.OrderService;
import com.example.demoSpringRestaurant.service.RestaurantService;
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
    void getOrdersByRestaurantId() throws RestaurantEntityNotFoundException {
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDto());
        when(restaurantService.findRestaurantById(any(Long.class)))
                .thenReturn(Optional.of(RestaurantFixture.getRestaurantEntity(true)));
        when(orderService.findAllRestaurantById((any(Long.class))))
                .thenReturn(OrderFixture.getOrderEntityList());

        var orderDtoList = restaurantOrderFacade.getOrdersByRestaurantId(RestaurantFixture.getRestaurantDto().getId());

        assertThat(orderDtoList).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDtoList());
        verify(restaurantService, times(1)).findRestaurantById(any(Long.class));
        verify(orderService, times(1)).findAllRestaurantById(any(Long.class));
        verify(orderMapper, times(1)).fromEntityToOrderDto(any(OrderEntity.class));
    }//TODO nem volt facade teszt de nem is tudom itt mit kéne mert nincs repository

    @Test
    void getOrdersByRestaurantIdShouldThrowEntityNotFoundException() {
        Long restaurantId = 1L;
        when(restaurantService.findRestaurantById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantEntityNotFoundException.class, () ->
                restaurantOrderFacade.getOrdersByRestaurantId(restaurantId));

        verify(restaurantService, times(1)).findRestaurantById(any(Long.class));
    }
    @Test
    void createOrder() throws RestaurantEntityNotFoundException {
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDto());
        when(orderMapper.fromOrderCreationDtoToEntity(any(OrderCreationDto.class)))
                .thenReturn(OrderFixture.getOrderEntity(false));
        when(restaurantService.findRestaurantById(any(Long.class)))
                .thenReturn(Optional.of(RestaurantFixture.getRestaurantEntity(true)));
        Mockito.doNothing().when(orderService).saveOrder(Mockito.any(OrderEntity.class));

        var orderDto = restaurantOrderFacade.createOrder(OrderFixture.getOrderCreationDto(), 1L);

        assertThat(orderDto).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
        verify(restaurantService, times(1)).findRestaurantById(any(Long.class));
        verify(orderMapper, times(1)).fromEntityToOrderDto(any(OrderEntity.class));
        verify(orderMapper, times(1)).fromOrderCreationDtoToEntity(any(OrderCreationDto.class));
        verify(orderService, times(1)).saveOrder(any(OrderEntity.class));
    }

    @Test
    void deleteRestaurant() throws RestaurantEntityNotFoundException {
        when(restaurantMapper.fromEntityToRestaurantDto(any(RestaurantEntity.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(orderService.findAllRestaurantById(any(Long.class)))
                .thenReturn(OrderFixture.getOrderEntityList());
        when(restaurantService.findRestaurantById(any(Long.class)))
                .thenReturn(Optional.of(RestaurantFixture.getRestaurantEntity(true)));
        Mockito.doNothing().when(orderService).deleteAllOrder(Mockito.anyList());
        Mockito.doNothing().when(restaurantService).deleteRestaurant(Mockito.any(RestaurantEntity.class));

        var restaurantDto = restaurantOrderFacade.deleteRestaurant(1L);

        assertThat(restaurantDto).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantMapper, times(1)).fromEntityToRestaurantDto(any(RestaurantEntity.class));
        verify(orderService, times(1)).findAllRestaurantById(any(Long.class));
        verify(restaurantService, times(1)).findRestaurantById(any(Long.class));
        verify(orderService, times(1)).deleteAllOrder(anyList());
        verify(restaurantService, times(1)).deleteRestaurant(any(RestaurantEntity.class));
    }
}
