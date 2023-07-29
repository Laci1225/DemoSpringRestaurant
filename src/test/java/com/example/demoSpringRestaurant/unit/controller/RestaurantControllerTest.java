package com.example.demoSpringRestaurant.unit.controller;

import com.example.demoSpringRestaurant.controller.rest.RestaurantController;
import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.fixtures.controller.RestaurantControllerFixture;
import com.example.demoSpringRestaurant.fixtures.service.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.controller.RestaurantControllerMapper;
import com.example.demoSpringRestaurant.model.service.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.service.RestaurantDto;
import com.example.demoSpringRestaurant.model.service.RestaurantUpdateDto;
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

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private RestaurantOrderFacade restaurantOrderFacade;


    @MockBean
    private RestaurantControllerMapper restaurantControllerMapper;

    @Test
    void getRestaurantsShouldReturnAllRestaurant() throws Exception {
        when(restaurantControllerMapper.fromRestaurantDtoToRestaurant(any(RestaurantDto.class)))
                .thenReturn(RestaurantControllerFixture.getRestaurant(true));
        when(restaurantService.getRestaurants())
                .thenReturn(RestaurantFixture.getRestaurantDtoList());

        this.mockMvc.perform(
                get("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantControllerFixture.getRestaurantList()))
        );
    }

    @Test
    void getRestaurantShouldReturnOneRestaurant() throws Exception {
        when(restaurantControllerMapper.fromRestaurantDtoToRestaurant(any(RestaurantDto.class)))
                .thenReturn(RestaurantControllerFixture.getRestaurant(true));
        when(restaurantService.getRestaurant(anyString()))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        this.mockMvc.perform(
                get("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantControllerFixture.getRestaurant(true)))
        );
    }

    @Test
    void createRestaurantShouldReturnCreatedRestaurant() throws Exception {
        when(restaurantControllerMapper.fromRestaurantDtoToRestaurant(any(RestaurantDto.class)))
                .thenReturn(RestaurantControllerFixture.getRestaurant(true));
        when(restaurantService.createRestaurant(any(RestaurantCreationDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        this.mockMvc.perform(
                post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantCreationDto()))
        ).andExpect(status().isCreated()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantControllerFixture.getRestaurant(true)))
        );
    }

    @Test
    void deleteRestaurantShouldRemoveOneRestaurant() throws Exception {
        when(restaurantControllerMapper.fromRestaurantDtoToRestaurant(any(RestaurantDto.class)))
                .thenReturn(RestaurantControllerFixture.getRestaurant(true));
        when(restaurantOrderFacade.deleteRestaurant(anyString()))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        this.mockMvc.perform(
                delete("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isAccepted()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantControllerFixture.getRestaurant(true)))
        );
    }

    @Test
    void deleteRestaurantShouldThrowOrderEntityNotFoundExceptionWith404() throws Exception {
        when(restaurantOrderFacade.deleteRestaurant(anyString()))
                .thenThrow(RestaurantDocumentNotFoundException.class);

        this.mockMvc.perform(
                delete("/restaurants/1")
        ).andExpect(status().isNotFound());
    }

    @Test
    void updateRestaurantShouldUpdateOneRestaurant() throws Exception {
        when(restaurantControllerMapper.fromRestaurantDtoToRestaurant(any(RestaurantDto.class)))
                .thenReturn(RestaurantControllerFixture.getRestaurant(true));
        when(restaurantService.updateRestaurant(anyString(), any(RestaurantUpdateDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        this.mockMvc.perform(
                put("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantUpdateDto()))
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantControllerFixture.getRestaurant(true)))
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
                        .content(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantDocumentList()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateParametersInRestaurantShouldUpdateOneRestaurantsParameter() throws Exception {
        when(restaurantControllerMapper.fromRestaurantDtoToRestaurant(any(RestaurantDto.class)))
                .thenReturn(RestaurantControllerFixture.getRestaurant(true));
        when(restaurantService.updateParametersInRestaurant(anyString(), any(RestaurantUpdateDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        this.mockMvc.perform(
                patch("/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantUpdateDto()))
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantControllerFixture.getRestaurant(true)))
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
                        .content(objectMapper.writeValueAsString(RestaurantFixture.getRestaurantDocumentList()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void getVeganRestaurants() throws Exception {
        when(restaurantControllerMapper.fromRestaurantDtoToRestaurant(any(RestaurantDto.class)))
                .thenReturn(RestaurantControllerFixture.getRestaurant(true));
        when(restaurantService.getVeganRestaurant())
                .thenReturn(RestaurantFixture.getRestaurantDtoList());
        this.mockMvc.perform(
                get("/restaurants/vegan")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(RestaurantControllerFixture.getRestaurantList()))
        );
    }
}