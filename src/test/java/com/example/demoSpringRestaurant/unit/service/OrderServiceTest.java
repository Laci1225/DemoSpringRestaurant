package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderEntityNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
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
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Test
    void getOrdersByRestaurantIdShouldReturnOneOrder() throws RestaurantEntityNotFoundException {
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDto());
        when(orderRepository.findAllByRestaurantId(anyLong()))
                .thenReturn(OrderFixture.getOrderEntityList());
        when(orderRepository.numberOfRestaurantFound(anyLong()))
                .thenReturn(1);

        var orderDtoList = orderService.getOrdersByRestaurantId(RestaurantFixture.getRestaurantDto().getId());

        assertThat(orderDtoList).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDtoList());
        assertThat(orderDtoList).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDtoList());
        verify(orderRepository, times(1)).findAllByRestaurantId(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void getOrdersByRestaurantIdShouldThrowRestaurantEntityNotFoundException(){
        when(orderRepository.numberOfRestaurantFound(anyLong()))
                .thenReturn(0);

        assertThrows(RestaurantEntityNotFoundException.class, () ->
                orderService.getOrdersByRestaurantId(1L));

        verify(orderRepository, times(1)).numberOfRestaurantFound(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void deleteOrderShouldRemoveOneOrder() throws EntityNotFoundException {
        //arrange / given
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDto());
        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(OrderFixture.getOrderEntity(true)));
        Mockito.doNothing().when(orderRepository).deleteById(Mockito.anyLong());

        //act / when
        var orderDto = orderService.deleteOrder(OrderFixture.getOrderEntity(true).getId());

        //assert / then
        assertThat(orderDto).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
        verify(orderRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void deleteOrderShouldThrowEntityNotFoundException() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderEntityNotFoundException.class, () ->
                orderService.deleteOrder(orderId));

        verify(orderRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldSetSENTToAPPROVED() throws EntityNotFoundException {
        OrderStatus orderStatus = OrderStatus.SENT;
        var nextOrderDto = OrderFixture.getOrderDtoGetNextStatus(orderStatus);
        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(OrderFixture.getOrderEntity(true)));
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        when(orderRepository.save(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderEntity(true));

        var orderDto = orderService.setNextState(nextOrderDto.getId());

        assertThat(orderDto.getOrderStatus()).isEqualTo(nextOrderDto.getOrderStatus());
        verify(orderRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldSetAPPROVEDToSHIPPING() throws EntityNotFoundException {
        OrderStatus orderStatus = OrderStatus.APPROVED;
        var nextOrderDto = OrderFixture.getOrderDtoGetNextStatus(orderStatus);

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(OrderFixture.getOrderEntity(true)));
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        when(orderRepository.save(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderEntity(true));

        var orderDto = orderService.setNextState(nextOrderDto.getId());

        assertThat(orderDto.getOrderStatus()).isEqualTo(nextOrderDto.getOrderStatus());
        verify(orderRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldSetSHIPPINGToSHIPPED() throws EntityNotFoundException {
        OrderStatus orderStatus = OrderStatus.SHIPPING;
        var nextOrderDto = OrderFixture.getOrderDtoGetNextStatus(orderStatus);

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(OrderFixture.getOrderEntity(true)));
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        when(orderRepository.save(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderEntity(true));

        var orderDto = orderService.setNextState(nextOrderDto.getId());

        assertThat(orderDto.getOrderStatus()).isEqualTo(nextOrderDto.getOrderStatus());
        verify(orderRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldThrowResponseStatusException() {

        assertThrows(UnsupportedOperationException.class, () -> OrderFixture.getOrderDtoGetNextStatus(OrderStatus.SHIPPED));

        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldThrowEntityNotFoundException() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderEntityNotFoundException.class, () ->
                orderService.setNextState(orderId));

        verify(orderRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }

}