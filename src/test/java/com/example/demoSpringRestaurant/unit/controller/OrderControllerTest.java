package com.example.demoSpringRestaurant.unit.controller;

import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.controller.rest.OrderController;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.facade.OrderGuestCourierFacade;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.facade.RestaurantOrderGuestCourierFacade;
import com.example.demoSpringRestaurant.fixtures.controller.OrderControllerFixture;
import com.example.demoSpringRestaurant.fixtures.service.OrderFixture;
import com.example.demoSpringRestaurant.mapper.controller.OrderControllerMapper;
import com.example.demoSpringRestaurant.model.service.OrderCreationDto;
import com.example.demoSpringRestaurant.model.service.OrderDto;
import com.example.demoSpringRestaurant.model.service.OrderUpdateDto;
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
    private RestaurantOrderFacade orderOrderFacade;
    @MockBean
    private RestaurantOrderGuestCourierFacade orderOrderGuestCourierFacade;
    @MockBean
    private OrderGuestCourierFacade orderGuestCourierFacade;
    @MockBean
    private OrderCourierFacade orderCourierFacade;

    @MockBean
    private OrderControllerMapper orderControllerMapper;

    @Test
    void createOrderShouldReturnCreatedOrder() throws Exception {
        when(orderControllerMapper.fromOrderDtoToOrder(any(OrderDto.class)))
                .thenReturn(OrderControllerFixture.getOrder(true));
        when(orderOrderGuestCourierFacade.createOrder(any(OrderCreationDto.class), anyString()))
                .thenReturn(OrderFixture.getOrderDto());

        this.mockMvc.perform(
                post("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixture.getOrderCreationDto()))
        ).andExpect(status().isCreated()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderControllerFixture.getOrder(true)))
        );
    }

    @Test
    void createOrderShouldThrowOrderDocumentNotFoundExceptionWith400() throws Exception {
        when(orderOrderGuestCourierFacade.createOrder(any(OrderCreationDto.class), anyString()))
                .thenThrow(RestaurantDocumentNotFoundException.class);

        this.mockMvc.perform(
                post("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixture.getOrderDtoList()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createOrderShouldThrowOrderDocumentNotFoundExceptionWith404() throws Exception {
        when(orderOrderGuestCourierFacade.createOrder(any(OrderCreationDto.class), anyString()))
                .thenThrow(RestaurantDocumentNotFoundException.class);

        this.mockMvc.perform(
                post("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixture.getOrderCreationDto()))
        ).andExpect(status().isNotFound());
    }

    @Test
    void getOrdersByRestaurantIdShouldReturnAllOrder() throws Exception {
        when(orderControllerMapper.fromOrderDtoToOrder(any(OrderDto.class)))
                .thenReturn(OrderControllerFixture.getOrder(true));
        when(orderOrderFacade.getOrdersByRestaurantId(anyString()))
                .thenReturn(OrderFixture.getOrderDtoList());

        this.mockMvc.perform(
                get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderControllerFixture.getOrderList()))
        );
    }

    @Test
    void getOrdersByRestaurantIdShouldThrowOrderDocumentNotFoundExceptionWith404() throws Exception {
        when(orderOrderFacade.getOrdersByRestaurantId(anyString()))
                .thenThrow(RestaurantDocumentNotFoundException.class);

        this.mockMvc.perform(
                get("/orders/1")
        ).andExpect(status().isNotFound());
    }

    @Test
    void deleteOrderShouldRemoveOneOrder() throws Exception {
        when(orderControllerMapper.fromOrderDtoToOrder(any(OrderDto.class)))
                .thenReturn(OrderControllerFixture.getOrder(true));
        when(orderGuestCourierFacade.deleteOrder(anyString()))
                .thenReturn(OrderFixture.getOrderDto());

        this.mockMvc.perform(
                delete("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isAccepted()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderControllerFixture.getOrder(true)))
        );
    }

    @Test
    void deleteOrderShouldThrowOrderDocumentNotFoundExceptionWith404() throws Exception {
        when(orderGuestCourierFacade.deleteOrder(anyString()))
                .thenThrow(OrderDocumentNotFoundException.class);

        this.mockMvc.perform(
                delete("/orders/1")
        ).andExpect(status().isNotFound());
    }

    @Test
    void setNextStateShouldSetToTheNextStateFromSENTTOAPPROVED() throws Exception {
        OrderStatus orderStatus = OrderStatus.SENT;
        when(orderControllerMapper.fromOrderDtoToOrder(any(OrderDto.class)))
                .thenReturn(OrderControllerFixture.getOrder(orderStatus.getNextStatus()));
        when(orderService.setNextState(anyString()))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));

        this.mockMvc.perform(
                post("/orders/1/next-state")
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderControllerFixture.getOrderGetNextStatus(orderStatus)))
        );
    }

    @Test
    void setNextStateShouldSetToTheNextStateFromAPPROVEDToSHIPPING() throws Exception {
        OrderStatus orderStatus = OrderStatus.APPROVED;
        when(orderControllerMapper.fromOrderDtoToOrder(any(OrderDto.class)))
                .thenReturn(OrderControllerFixture.getOrder(orderStatus.getNextStatus()));
        when(orderService.setNextState(anyString()))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));

        this.mockMvc.perform(
                post("/orders/1/next-state")
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderControllerFixture.getOrderGetNextStatus(orderStatus)))
        );
    }

    @Test
    void setNextStateShouldSetToTheNextStateFromSHIPPINGToSHIPPED() throws Exception {
        OrderStatus orderStatus = OrderStatus.SHIPPING;
        when(orderControllerMapper.fromOrderDtoToOrder(any(OrderDto.class)))
                .thenReturn(OrderControllerFixture.getOrder(orderStatus.getNextStatus()));
        when(orderService.setNextState(anyString()))
                .thenReturn(OrderFixture.getOrderDtoGetNextStatus(orderStatus));

        this.mockMvc.perform(
                post("/orders/1/next-state")
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderControllerFixture.getOrderGetNextStatus(orderStatus)))
        );
    }

    @Test
    void setNextStateShouldThrowResponseStatusException() throws Exception {
        when(orderService.setNextState(anyString()))
                .thenThrow(UnsupportedOperationException.class);

        this.mockMvc.perform(
                post("/orders/1/next-state")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void setNextStateShouldThrowDocumentNotFoundExceptionWith404() throws Exception {
        when(orderService.setNextState(anyString()))
                .thenThrow(OrderDocumentNotFoundException.class);

        this.mockMvc.perform(
                post("/orders/1/next-state")
        ).andExpect(status().isNotFound());
    }

    @Test
    void updateOrderShouldUpdateOneOrder() throws Exception {
        when(orderControllerMapper.fromOrderDtoToOrder(any(OrderDto.class)))
                .thenReturn(OrderControllerFixture.getOrder(true));
        when(orderService.updateOrder(anyString(), any(OrderUpdateDto.class)))
                .thenReturn(OrderFixture.getOrderDto());

        this.mockMvc.perform(
                put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixture.getOrderUpdateDto()))
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderControllerFixture.getOrder(true)))
        );
    }

    @Test
    void updateOrderShouldThrowOrderDocumentNotFoundExceptionWith404() throws Exception {
        when(orderService.updateOrder(anyString(), any(OrderUpdateDto.class)))
                .thenThrow(OrderDocumentNotFoundException.class);

        this.mockMvc.perform(
                put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixture.getOrderUpdateDto()))

        ).andExpect(status().isNotFound());
    }

    @Test
    void updateOrderShouldThrowOrderDocumentNotFoundExceptionWith400() throws Exception {
        when(orderService.updateOrder(anyString(), any(OrderUpdateDto.class)))
                .thenThrow(OrderDocumentNotFoundException.class);

        this.mockMvc.perform(
                put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixture.getOrderDocumentList()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void getOrderShouldReturnOneOrder() throws Exception {
        when(orderControllerMapper.fromOrderDtoToOrder(any(OrderDto.class)))
                .thenReturn(OrderControllerFixture.getOrder(true));
        when(orderService.getOrderById(anyString()))
                .thenReturn(OrderFixture.getOrderDto());

        this.mockMvc.perform(
                get("/orders/order/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderControllerFixture.getOrder(true)))
        );
    }

    @Test
    void getOrderShouldThrowOrderDocumentNotFoundExceptionWith400() throws Exception {
        when(orderService.getOrderById(anyString()))
                .thenThrow(OrderDocumentNotFoundException.class);

        this.mockMvc.perform(
                get("/orders/order/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void setCourierToOrderShouldSetACourierToAnOrder() throws Exception {
        when(orderControllerMapper.fromOrderDtoToOrder(any(OrderDto.class)))
                .thenReturn(OrderControllerFixture.getOrder(true));
        when(orderCourierFacade.setCourierToOrder(anyString(),anyString()))
                .thenReturn(OrderFixture.getOrderDto());

        this.mockMvc.perform(
                post("/orders/set/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(OrderControllerFixture.getOrder(true)))
        );
    }

    @Test
    void setCourierToOrderShouldThrowOrderDocumentNotFoundExceptionWith400() throws Exception {
        when(orderCourierFacade.setCourierToOrder(anyString(),anyString()))
                .thenThrow(OrderDocumentNotFoundException.class);

        this.mockMvc.perform(
                post("/orders/set/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}