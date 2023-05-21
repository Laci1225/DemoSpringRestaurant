package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void removeOrderShouldRemoveOneOrder() throws EntityNotFoundException {
        //arrange / given
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDto());
        when(orderRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(OrderFixture.getOrderEntity(true)));
        Mockito.doNothing().when(orderRepository).deleteById(Mockito.anyLong());

        //act / when
        var orderDto = orderService.removeOrder(OrderFixture.getOrderEntity(true).getId());

        //assert / then
        assertThat(orderDto).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
        verify(orderRepository, times(1)).findById(any(Long.class));
        verify(orderRepository, times(1)).deleteById(any(Long.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldSetOrderStatusToNextStatus() throws EntityNotFoundException {
        when(orderRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(OrderFixture.getOrderEntity(true)));
        when(orderMapper.fromEntityToOrderDto(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus());
        when(orderRepository.save(any(OrderEntity.class)))
                .thenReturn(OrderFixture.getOrderEntity(true));

        var orderDto = orderService.setNextState(OrderFixture.getOrderDto().getId());

        assertThat(orderDto).usingRecursiveComparison().isNotEqualTo(OrderFixture.getOrderDto());
        assertThat(orderDto.getOrderStatus()).isEqualTo(OrderFixture.getOrderDtoGetNextStatus().getOrderStatus());
        verify(orderRepository, times(1)).findById(any(Long.class));
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void addOrderShouldCreateOneOrder() {
    }
}