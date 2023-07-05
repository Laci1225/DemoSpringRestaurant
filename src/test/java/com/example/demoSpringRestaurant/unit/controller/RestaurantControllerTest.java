package com.example.demoSpringRestaurant.unit.controller;

import com.example.demoSpringRestaurant.controller.RestaurantController;
import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.service.RestaurantService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    /*@Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private RestaurantOrderFacade restaurantOrderFacade;


    @Test
    void getRestaurantShouldReturnAllRestaurant() throws Exception {
        when(restaurantService.getRestaurants())
                .thenReturn(RestaurantFixture.getRestaurantDtoList());

        this.mockMvc.perform(
                get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantDtoList()))
        );
    }

    @Test
    void createRestaurantShouldReturnCreatedRestaurant() throws Exception {
        when(restaurantService.createRestaurant(any(RestaurantCreationDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        this.mockMvc.perform(
                post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantCreationDto()))
        ).andExpect(status().isCreated()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantCreationDto()))
        );
    }

    @Test
    void deleteRestaurantShouldRemoveOneRestaurant() throws Exception {
        when(restaurantOrderFacade.deleteRestaurant(anyString()))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        this.mockMvc.perform(
                delete("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isAccepted()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantCreationDto()))
        );
    }

    @Test
    void deleteRestaurantShouldThrowOrderEntityNotFoundExceptionWith404() throws Exception {
        //given
        when(restaurantOrderFacade.deleteRestaurant(anyString()))
                .thenThrow(RestaurantDocumentNotFoundException.class);
        //when
        //then
        this.mockMvc.perform(
                delete("/orders/1")
        ).andExpect(status().isNotFound());
    }

    @Test
    void updateRestaurantShouldUpdateOneRestaurant() throws Exception {
        when(restaurantService.updateRestaurant(anyString(), any(RestaurantUpdateDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        this.mockMvc.perform(
                put("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantUpdateDto()))
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantDto()))
        );
    }

    @Test
    void updateRestaurantShouldThrowOrderEntityNotFoundExceptionWith404() throws Exception {
        when(restaurantService.updateRestaurant(anyString(), any(RestaurantUpdateDto.class)))
                .thenThrow(RestaurantDocumentNotFoundException.class);

        this.mockMvc.perform(
                put("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantUpdateDto()))

        ).andExpect(status().isNotFound());
    }
    @Test
    void updateRestaurantShouldThrowOrderEntityNotFoundExceptionWith400() throws Exception {
        when(restaurantService.updateRestaurant(anyString(), any(RestaurantUpdateDto.class)))
                .thenThrow(RestaurantDocumentNotFoundException.class);

        this.mockMvc.perform(
                put("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixture.getOrderCreationDto()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateParametersInRestaurantShouldUpdateOneRestaurantsParameter() throws Exception {
        when(restaurantService.updateParametersInRestaurant(anyString(), any(RestaurantUpdateDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        this.mockMvc.perform(
                patch("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantUpdateDto()))
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantDto()))
        );
    }

    @Test
    void updateParametersInRestaurantShouldThrowOrderEntityNotFoundExceptionWith404() throws Exception {
        when(restaurantService.updateParametersInRestaurant(anyString(), any(RestaurantUpdateDto.class)))
                .thenThrow(RestaurantDocumentNotFoundException.class);

        this.mockMvc.perform(
                patch("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantUpdateDto()))

        ).andExpect(status().isNotFound());
    }
    @Test
    void updateParametersInRestaurantShouldThrowOrderEntityNotFoundExceptionWith400() throws Exception {
        when(restaurantService.updateParametersInRestaurant(anyString(), any(RestaurantUpdateDto.class)))
                .thenThrow(RestaurantDocumentNotFoundException.class);

        this.mockMvc.perform(
                patch("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixture.getOrderDtoList()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void getVeganRestaurants() throws Exception {
        when(restaurantService.getVeganRestaurant())
                .thenReturn(RestaurantFixture.getRestaurantDtoList());
        this.mockMvc.perform(
                get("/restaurants/vegan")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantDtoList()))
        );
    }
*/
}