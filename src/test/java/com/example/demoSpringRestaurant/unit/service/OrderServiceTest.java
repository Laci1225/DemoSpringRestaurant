package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import com.example.demoSpringRestaurant.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
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
    void setNextStateShouldSetSENTToAPPROVED() throws OrderDocumentNotFoundException {
        OrderStatus orderStatus = OrderStatus.SENT;
        var nextOrderDto = OrderFixture.getOrderDtoGetNextStatus(orderStatus);
        when(orderRepository.findById(anyString()))
                .thenReturn(Optional.of(OrderFixture.getOrderDocument(true)));
        when(orderMapper.fromDocumentToOrderDto(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        when(orderRepository.save(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDocument(true));

        var orderDto = orderService.setNextState(nextOrderDto.getId());

        assertThat(orderDto.getOrderStatus()).isEqualTo(nextOrderDto.getOrderStatus());
        verify(orderRepository, times(1)).findById(anyString());
        verify(orderRepository, times(1)).save(any(OrderDocument.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldSetAPPROVEDToSHIPPING() throws OrderDocumentNotFoundException {
        OrderStatus orderStatus = OrderStatus.APPROVED;
        var nextOrderDto = OrderFixture.getOrderDtoGetNextStatus(orderStatus);

        when(orderRepository.findById(anyString()))
                .thenReturn(Optional.of(OrderFixture.getOrderDocument(true)));
        when(orderMapper.fromDocumentToOrderDto(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        when(orderRepository.save(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDocument(true));

        var orderDto = orderService.setNextState(nextOrderDto.getId());

        assertThat(orderDto.getOrderStatus()).isEqualTo(nextOrderDto.getOrderStatus());
        verify(orderRepository, times(1)).findById(anyString());
        verify(orderRepository, times(1)).save(any(OrderDocument.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldSetSHIPPINGToSHIPPED() throws OrderDocumentNotFoundException {
        OrderStatus orderStatus = OrderStatus.SHIPPING;
        var nextOrderDto = OrderFixture.getOrderDtoGetNextStatus(orderStatus);

        when(orderRepository.findById(anyString()))
                .thenReturn(Optional.of(OrderFixture.getOrderDocument(true)));
        when(orderMapper.fromDocumentToOrderDto(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        when(orderRepository.save(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDocument(true));

        var orderDto = orderService.setNextState(nextOrderDto.getId());

        assertThat(orderDto.getOrderStatus()).isEqualTo(nextOrderDto.getOrderStatus());
        verify(orderRepository, times(1)).findById(anyString());
        verify(orderRepository, times(1)).save(any(OrderDocument.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldThrowResponseStatusException() {

        assertThrows(UnsupportedOperationException.class, () -> OrderFixture.getOrderDtoGetNextStatus(OrderStatus.SHIPPED));

        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void setNextStateShouldOrderDocumentNotFoundException() {
        String orderId = "1L";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderDocumentNotFoundException.class, () ->
                orderService.setNextState(orderId));

        verify(orderRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void updateOrderShouldUpdateOneOrder() throws DocumentNotFoundException {
        //arrange / given
        when(orderMapper.fromDocumentToOrderDto(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDto());
        when(orderMapper.fromOrderUpdateDtoToDocument(any(OrderUpdateDto.class)))
                .thenReturn(OrderFixture.getOrderDocument(false));
        when(orderRepository.save(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDocument(true));
        when(orderRepository.findById(anyString())).thenReturn(Optional.of(OrderFixture.getOrderDocument(true)));
        //act / when
        var orderUpdate = orderService.updateOrder("1L", OrderFixture.getOrderUpdateDto());

        //assert / then
        assertThat(orderUpdate).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
        verify(orderRepository, times(1)).findById(anyString());
        verify(orderRepository, times(1)).save(any(OrderDocument.class));
        verifyNoMoreInteractions(orderRepository);
    }


    @Test
    void getOrderById() throws OrderDocumentNotFoundException {
        when(orderRepository.findById(anyString()))
                .thenReturn(Optional.of(OrderFixture.getOrderDocument(false)));
        when( orderMapper.fromDocumentToOrderDto(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDto());

        var order = orderService.getOrderById("1234");

        assertThat(order).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
        verify(orderRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void deleteByIdShouldThrowOrderDocumentNotFoundException() throws OrderDocumentNotFoundException {
        when(orderRepository.existsById(anyString()))
                .thenReturn(false);

        assertThrows(OrderDocumentNotFoundException.class, () ->
                orderService.deleteById(OrderFixture.getOrderDto().getId()));
    }

    @Test
    void deleteByIdShouldDeleteOneOrder() throws OrderDocumentNotFoundException {
        doNothing().when(orderRepository).deleteById(anyString());
        when(orderRepository.existsById(anyString()))
                .thenReturn(true);

        orderService.deleteById(OrderFixture.getOrderDto().getId());

        verify(orderRepository, times(1)).deleteById(anyString());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void saveOrderShouldSaveOneOrder() {
        when(orderRepository.save(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDocument(false));
        when(orderMapper.fromDocumentToOrderDto(any(OrderDocument.class)))
                .thenReturn(OrderFixture.getOrderDto());

        var order = orderService.saveOrder(OrderFixture.getOrderDocument("1234"));

        assertThat(order).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());

        verify(orderRepository, times(1)).save(any(OrderDocument.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void deleteAllOrder() {
        doNothing().when(orderRepository).deleteAll(OrderFixture.getOrderDocumentList());

        orderService.deleteAllOrder(OrderFixture.getOrderDocumentList());

        verify(orderRepository, times(1)).deleteAll(anyList());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void findAllByRestaurantId() {
        when(orderRepository.findAllByRestaurantId(anyString()))
                .thenReturn(OrderFixture.getOrderDocumentList());

        var order = orderService.findAllByRestaurantId("1234");

        assertThat(order).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDocumentList());
        verify(orderRepository, times(1)).findAllByRestaurantId(anyString());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void findByIdShouldReturnOneOrder() {
        when(orderRepository.findById(anyString())).
                thenReturn(Optional.of(OrderFixture.getOrderDocument(true)));

        var orderDocumentOptional = orderService.findById("1234");

        assertThat(orderDocumentOptional).usingRecursiveComparison().isEqualTo(
                Optional.of(OrderFixture.getOrderDocument("1234")));
        verify(orderRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(orderRepository);
    }

}