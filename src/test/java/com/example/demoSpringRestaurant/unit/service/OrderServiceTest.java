package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderEntityNotFoundException;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
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
import org.springframework.web.server.ResponseStatusException;

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
    void deleteOrderShouldRemoveOneOrder() throws EntityNotFoundException {
        //arrange / given
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDto());
        when(orderRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(OrderFixture.getOrderEntity(true)));
        Mockito.doNothing().when(orderRepository).deleteById(Mockito.anyLong());

        //act / when
        var orderDto = orderService.deleteOrder(OrderFixture.getOrderEntity(true).getId());

        //assert / then
        assertThat(orderDto).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
        verify(orderRepository, times(1)).findById(any(Long.class));
        verify(orderRepository, times(1)).deleteById(any(Long.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void deleteOrderShouldThrowEntityNotFoundException() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderEntityNotFoundException.class, () ->
                orderService.deleteOrder(orderId));

        verify(orderRepository, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldSetSENTToAPPROVED() throws EntityNotFoundException {
        OrderStatus orderStatus = OrderStatus.SENT;
        var nextOrderDto = OrderFixture.getOrderDtoGetNextStatus(orderStatus);
        when(orderRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(OrderFixture.getOrderEntity(true)));
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        when(orderRepository.save(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderEntity(true));

        var orderDto = orderService.setNextState(nextOrderDto.getId());

        assertThat(orderDto.getOrderStatus()).isEqualTo(nextOrderDto.getOrderStatus());
        verify(orderRepository, times(1)).findById(any(Long.class));
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldSetAPPROVEDToSHIPPING() throws EntityNotFoundException {
        OrderStatus orderStatus = OrderStatus.APPROVED;
        var nextOrderDto = OrderFixture.getOrderDtoGetNextStatus(orderStatus);

        when(orderRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(OrderFixture.getOrderEntity(true)));
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        when(orderRepository.save(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderEntity(true));

        var orderDto = orderService.setNextState(nextOrderDto.getId());

        assertThat(orderDto.getOrderStatus()).isEqualTo(nextOrderDto.getOrderStatus());
        verify(orderRepository, times(1)).findById(any(Long.class));
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldSetSHIPPINGToSHIPPED() throws EntityNotFoundException {
        OrderStatus orderStatus = OrderStatus.SHIPPING;
        var nextOrderDto = OrderFixture.getOrderDtoGetNextStatus(orderStatus);

        when(orderRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(OrderFixture.getOrderEntity(true)));
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        when(orderRepository.save(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderEntity(true));

        var orderDto = orderService.setNextState(nextOrderDto.getId());

        assertThat(orderDto.getOrderStatus()).isEqualTo(nextOrderDto.getOrderStatus());
        verify(orderRepository, times(1)).findById(any(Long.class));
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldThrowResponseStatusException() {

        assertThrows(ResponseStatusException.class, () -> OrderFixture.getOrderDtoGetNextStatus(OrderStatus.SHIPPED));

        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldThrowEntityNotFoundException() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderEntityNotFoundException.class, () ->
                orderService.setNextState(orderId));

        verify(orderRepository, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(orderRepository);
    }

}