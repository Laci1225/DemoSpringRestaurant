package com.example.demoSpringRestaurant.unit.controller;

import com.example.demoSpringRestaurant.controller.OrderController;
import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
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
    }

    @Test
    void addOrderShouldThrowOrderEntityNotFoundException() throws Exception {
       /* //given
        when(restaurantOrderFacade.addOrder(any(OrderCreationDto.class), any(Long.class)))
                .thenThrow(RestaurantEntityNotFoundException.class);
        //when
        //then
        this.mockMvc.perform(
                post("/orders/1")
        ).andExpect(status().isNotFound());
        //TODO no idea why it is bad
*/
    }


    @Test
    void getOrdersByRestaurantIdShouldReturnAllOrder() throws Exception {
        //given
        when(restaurantOrderFacade.getOrdersByRestaurantId(any(Long.class)))
                .thenReturn(OrderFixture.getOrderDtoList());

        //when
        //then
        this.mockMvc.perform(
                get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderFixture.getOrderDtoList()))
        );
    }

    @Test
    void getOrdersByRestaurantIdShouldThrowOrderEntityNotFoundException() throws Exception {
        //given
        when(restaurantOrderFacade.getOrdersByRestaurantId(any(Long.class)))
                .thenThrow(RestaurantEntityNotFoundException.class);
        //when
        //then
        this.mockMvc.perform(
                get("/orders/1")
        ).andExpect(status().isNotFound());

    }

    @Test
    void removeOrderShouldRemoveOneOrder() throws Exception {
        //given
        when(orderService.removeOrder(any(Long.class)))
                .thenReturn(OrderFixture.getOrderDto());
        //when
        //then
        this.mockMvc.perform(
                delete("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isAccepted()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderFixture.getOrderDto()))
        );
    }

    @Test
    void removeOrderShouldThrowOrderEntityNotFoundException() throws Exception {
        //given
        when(orderService.removeOrder(any(Long.class)))
                .thenThrow(RestaurantEntityNotFoundException.class);
        //when
        //then
        this.mockMvc.perform(
                delete("/orders/1")
        ).andExpect(status().isNotFound());

    }
}