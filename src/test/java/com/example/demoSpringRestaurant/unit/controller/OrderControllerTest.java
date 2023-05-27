package com.example.demoSpringRestaurant.unit.controller;

import com.example.demoSpringRestaurant.controller.OrderController;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    @MockBean
    private RestaurantOrderFacade restaurantOrderFacade;


    @Test
    void addOrderShouldReturnCreatedOrder() throws Exception {
        //given
        when(restaurantOrderFacade.addOrder(any(OrderCreationDto.class), any(Long.class)))
                .thenReturn(OrderFixture.getOrderDto());

        //when
        //then
        this.mockMvc.perform(
                post("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixture.getOrderCreationDto()))
        ).andExpect(status().isCreated()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderFixture.getOrderDto()))
        );
        //todo facade exception throws what?

    }


    /*@Test
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

    *//*@Test
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
    }*//*

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
    }*/
}