package com.example.demoSpringRestaurant.unit.controller;

import com.example.demoSpringRestaurant.controller.OrderController;
import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private RestaurantOrderFacade restaurantOrderFacade;

    @Test
    void getOrdersByRestaurantId() {
        when(restaurantOrderFacade.getOrdersByRestaurantId(any(Long.class)))
                .thenReturn(OrderFixture.getOrderDtoList());

        var orderDto = orderController.getOrdersByRestaurantId(
                RestaurantFixture.getRestaurantDto().getId());

        assertThat(orderDto).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDtoList());
    }

    @Test
    void addOrderShouldCreateOneOrder() throws EntityNotFoundException {
        when(restaurantOrderFacade.addOrder(any(OrderCreationDto.class), any(Long.class)))
                .thenReturn(OrderFixture.getOrderDto());

        var orderDto = orderController.addOrder(
                OrderFixture.getOrderCreationDto(), RestaurantFixture.getRestaurantDto().getId());

        assertThat(orderDto).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
    }

    /*@Test
    void addOrderShouldThrowRestaurantEntityNotFoundException() {
        // TODO test handling exceptions
        String errorMessage = "Restaurant not found";
        when(restaurantOrderFacade.addOrder(OrderFixture.getOrderCreationDto(), 1L))
                .thenThrow(new RestaurantEntityNotFoundException(errorMessage));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            orderController.addOrder(OrderFixture.getOrderCreationDto(), 1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals(errorMessage, exception.getReason());
    }*/

    @Test
    void removeOrder() throws EntityNotFoundException {
        when(orderService.removeOrder(any(Long.class)))
                .thenReturn(OrderFixture.getOrderDto());

        var orderDto = orderController.removeOrder(
                OrderFixture.getOrderDto().getId());

        assertThat(orderDto).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
    }

    @Test
    void setNextState() throws EntityNotFoundException {
        when(orderService.setNextState(any(Long.class)))
                .thenReturn(OrderFixture.getOrderDto());

        var orderDto = orderController.setNextState(
                OrderFixture.getOrderDto().getId());

        assertThat(orderDto).usingRecursiveComparison().isEqualTo(OrderFixture.getOrderDto());
    }
}