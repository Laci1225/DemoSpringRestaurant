package com.example.demoSpringRestaurant.unit.controller;

import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.controller.OrderController;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
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
    void createOrderShouldReturnCreatedOrder() throws Exception {
        //given
        when(restaurantOrderFacade.createOrder(any(OrderCreationDto.class), anyString()))
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
    void createOrderShouldThrowOrderEntityNotFoundExceptionWith400() throws Exception {

        //given
        when(restaurantOrderFacade.createOrder(any(OrderCreationDto.class), anyString()))
                .thenThrow(RestaurantDocumentNotFoundException.class);
        //when
        //then
        this.mockMvc.perform(
                post("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantDocument(true)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createOrderShouldThrowOrderEntityNotFoundExceptionWith404() throws Exception {
        //given
        when(restaurantOrderFacade.createOrder(any(OrderCreationDto.class), anyString()))
                .thenThrow(RestaurantDocumentNotFoundException.class);
        //when
        //then
        this.mockMvc.perform(
                post("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixture.getOrderCreationDto()))
        ).andExpect(status().isNotFound());
    }


    @Test
    void getOrdersByRestaurantIdShouldReturnAllOrder() throws Exception {
        //given
        when(restaurantOrderFacade.getOrdersByRestaurantId(anyString()))
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
    void getOrdersByRestaurantIdShouldThrowOrderEntityNotFoundExceptionWith404() throws Exception {
        //given
        when(restaurantOrderFacade.getOrdersByRestaurantId(anyString()))
                .thenThrow(RestaurantDocumentNotFoundException.class);
        //when
        //then
        this.mockMvc.perform(
                get("/orders/1")
        ).andExpect(status().isNotFound());
    }

    @Test
    void deleteOrderShouldRemoveOneOrder() throws Exception {
        //given
        when(orderService.deleteOrder(anyString()))
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
    void deleteOrderShouldThrowOrderEntityNotFoundExceptionWith404() throws Exception {
        //given
        when(orderService.deleteOrder(anyString()))
                .thenThrow(OrderDocumentNotFoundException.class);
        //when
        //then
        this.mockMvc.perform(
                delete("/orders/1")
        ).andExpect(status().isNotFound());
    }

    @Test
    void setNextStateShouldSetToTheNextStateFromSENTTOAPPROVED() throws Exception {
        OrderStatus orderStatus = OrderStatus.SENT;
        when(orderService.setNextState(anyString()))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        //when
        //then
        this.mockMvc.perform(
                post("/orders/1/next-state")
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderFixture.getOrderDtoGetNextStatus(orderStatus)))
        );
    }

    @Test
    void setNextStateShouldSetToTheNextStateFromAPPROVEDToSHIPPING() throws Exception {
        OrderStatus orderStatus = OrderStatus.APPROVED;
        when(orderService.setNextState(anyString()))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        //when
        //then
        this.mockMvc.perform(
                post("/orders/1/next-state")
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderFixture.getOrderDtoGetNextStatus(orderStatus)))
        );
    }

    @Test
    void setNextStateShouldSetToTheNextStateFromSHIPPINGToSHIPPED() throws Exception {
        OrderStatus orderStatus = OrderStatus.SHIPPING;
        when(orderService.setNextState(anyString()))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));
        //when
        //then
        this.mockMvc.perform(
                post("/orders/1/next-state")
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderFixture.getOrderDtoGetNextStatus(orderStatus)))
        );
    }

    @Test
    void setNextStateShouldThrowResponseStatusException() throws Exception {
        when(orderService.setNextState(anyString()))
                .thenThrow(UnsupportedOperationException.class);
        //when
        //then
        this.mockMvc.perform(
                post("/orders/1/next-state")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void setNextStateShouldThrowEntityNotFoundExceptionWith404() throws Exception {
        when(orderService.setNextState(anyString()))
                .thenThrow(OrderDocumentNotFoundException.class);
        //when
        //then
        this.mockMvc.perform(
                post("/orders/1/next-state")
        ).andExpect(status().isNotFound());
    }
}