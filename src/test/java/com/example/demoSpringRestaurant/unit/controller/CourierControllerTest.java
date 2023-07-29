package com.example.demoSpringRestaurant.unit.controller;

import com.example.demoSpringRestaurant.controller.rest.CourierController;
import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.fixtures.controller.CourierControllerFixture;
import com.example.demoSpringRestaurant.fixtures.service.CourierFixture;
import com.example.demoSpringRestaurant.mapper.controller.CourierControllerMapper;
import com.example.demoSpringRestaurant.model.service.CourierCreationDto;
import com.example.demoSpringRestaurant.model.service.CourierDto;
import com.example.demoSpringRestaurant.model.service.CourierUpdateDto;
import com.example.demoSpringRestaurant.service.CourierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourierController.class)
public class CourierControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CourierService courierService;
    @MockBean
    private OrderCourierFacade orderCourierFacade;
    @MockBean
    private CourierControllerMapper courierControllerMapper;

    @Test
    void updateCourierShouldUpdateOneCourier() throws Exception {
        when(courierControllerMapper.fromCourierDtoToCourier(any(CourierDto.class)))
                .thenReturn(CourierControllerFixture.getCourier(true));
        when(courierService.updateCourier(anyString(), any(CourierUpdateDto.class)))
                .thenReturn(CourierFixture.getCourierDto());

        this.mockMvc.perform(
                put("/couriers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CourierFixture.getCourierUpdateDto()))
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(CourierControllerFixture.getCourier(true)))
        );
    }

    @Test
    void updateCourierShouldThrowCourierEntityNotFoundExceptionWith404() throws Exception {
        when(courierService.updateCourier(anyString(), any(CourierUpdateDto.class)))
                .thenThrow(CourierDocumentNotFoundException.class);

        this.mockMvc.perform(
                put("/couriers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CourierFixture.getCourierUpdateDto()))

        ).andExpect(status().isNotFound());
    }

    @Test
    void updateCourierShouldThrowCourierEntityNotFoundExceptionWith400() throws Exception {
        when(courierService.updateCourier(anyString(), any(CourierUpdateDto.class)))
                .thenThrow(CourierDocumentNotFoundException.class);

        this.mockMvc.perform(
                put("/couriers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CourierFixture.getCourierDocumentList()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addOrderToCourierShouldSetAnOrderToACourier() throws Exception {
        when(courierControllerMapper.fromCourierDtoToCourier(any(CourierDto.class)))
                .thenReturn(CourierControllerFixture.getCourier(true));
        when(orderCourierFacade.addOrderToCourier(anyString(), anyString()))
                .thenReturn(CourierFixture.getCourierDto());

        this.mockMvc.perform(
                post("/couriers/add/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(CourierControllerFixture.getCourier(true)))
        );
    }

    @Test
    void addOrderToCourierShouldThrowCourierEntityNotFoundExceptionWith404() throws Exception {
        when(orderCourierFacade.addOrderToCourier(anyString(), anyString()))
                .thenThrow(CourierDocumentNotFoundException.class);

        this.mockMvc.perform(
                post("/couriers/add/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void setOrderActiveShouldSetACouriersActiveOrder() throws Exception {
        when(courierControllerMapper.fromCourierDtoToCourier(any(CourierDto.class)))
                .thenReturn(CourierControllerFixture.getCourier(true));
        when(orderCourierFacade.setOrderActive(anyString(), anyString()))
                .thenReturn(CourierFixture.getCourierDto());

        this.mockMvc.perform(
                post("/couriers/set/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(CourierControllerFixture.getCourier(true)))
        );
    }
    @Test
    void setOrderActiveShouldThrowCourierEntityNotFoundExceptionWith404() throws Exception {
        when(orderCourierFacade.setOrderActive(anyString(), anyString()))
                .thenThrow(CourierDocumentNotFoundException.class);

        this.mockMvc.perform(
                post("/couriers/set/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void getCourierShouldReturnOneCourier() throws Exception {
        when(courierControllerMapper.fromCourierDtoToCourier(any(CourierDto.class)))
                .thenReturn(CourierControllerFixture.getCourier(true));
        when(courierService.getCourier(anyString()))
                .thenReturn(CourierFixture.getCourierDto());

        this.mockMvc.perform(
                get("/couriers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(CourierControllerFixture.getCourier(true)))
        );
    }

    @Test
    void createCourierShouldReturnCreatedCourier() throws Exception {
        when(courierControllerMapper.fromCourierDtoToCourier(any(CourierDto.class)))
                .thenReturn(CourierControllerFixture.getCourier(true));
        when(courierService.createCourier(any(CourierCreationDto.class)))
                .thenReturn(CourierFixture.getCourierDto());

        this.mockMvc.perform(
                post("/couriers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CourierFixture.getCourierCreationDto()))
        ).andExpect(status().isCreated()).andExpect(content()
                .json(objectMapper.writeValueAsString(CourierControllerFixture.getCourier(true)))
        );
    }

    @Test
    void getCouriersShouldReturnAllCourier() throws Exception {
        when(courierControllerMapper.fromCourierDtoToCourier(any(CourierDto.class)))
                .thenReturn(CourierControllerFixture.getCourier(true));
        when(courierService.getCouriers())
                .thenReturn(CourierFixture.getCourierDtoList());

        this.mockMvc.perform(
                get("/couriers")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(CourierControllerFixture.getCourierList()))
        );

    }

    @Test
    void deleteCourierShouldRemoveOneCourier() throws Exception {
        when(courierControllerMapper.fromCourierDtoToCourier(any(CourierDto.class)))
                .thenReturn(CourierControllerFixture.getCourier(true));
        when(orderCourierFacade.deleteCourier(anyString()))
                .thenReturn(CourierFixture.getCourierDto());

        this.mockMvc.perform(
                delete("/couriers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isAccepted()).andExpect(content()
                .json(objectMapper.writeValueAsString(CourierControllerFixture.getCourier(true)))
        );
    }

    @Test
    void deleteCourierShouldThrowOrderEntityNotFoundExceptionWith404() throws Exception {
        //given
        when(orderCourierFacade.deleteCourier(anyString()))
                .thenThrow(CourierDocumentNotFoundException.class);
        //when
        //then
        this.mockMvc.perform(
                delete("/couriers/1")
        ).andExpect(status().isNotFound());
    }

}
