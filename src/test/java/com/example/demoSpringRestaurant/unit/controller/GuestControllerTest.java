package com.example.demoSpringRestaurant.unit.controller;

import com.example.demoSpringRestaurant.controller.rest.GuestController;
import com.example.demoSpringRestaurant.exception.GuestDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderGuestFacade;
import com.example.demoSpringRestaurant.fixtures.controller.GuestControllerFixture;
import com.example.demoSpringRestaurant.fixtures.service.GuestFixture;
import com.example.demoSpringRestaurant.mapper.controller.GuestControllerMapper;
import com.example.demoSpringRestaurant.model.service.GuestCreationDto;
import com.example.demoSpringRestaurant.model.service.GuestDto;
import com.example.demoSpringRestaurant.model.service.GuestUpdateDto;
import com.example.demoSpringRestaurant.service.GuestService;
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

@WebMvcTest(GuestController.class)
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GuestService guestService;
    @MockBean
    private OrderGuestFacade orderGuestFacade;

    @MockBean
    private GuestControllerMapper guestControllerMapper;

    @Test
    void updateGuestShouldUpdateOneGuest() throws Exception {
        when(guestControllerMapper.fromGuestDtoToGuest(any(GuestDto.class)))
                .thenReturn(GuestControllerFixture.getGuest(true));
        when(guestService.updateGuest(anyString(), any(GuestUpdateDto.class)))
                .thenReturn(GuestFixture.getGuestDto());

        this.mockMvc.perform(
                put("/guests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(GuestFixture.getGuestUpdateDto()))
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(GuestControllerFixture.getGuest(true)))
        );
    }

    @Test
    void updateGuestShouldThrowGuestEntityNotFoundExceptionWith404() throws Exception {
        when(guestService.updateGuest(anyString(), any(GuestUpdateDto.class)))
                .thenThrow(GuestDocumentNotFoundException.class);

        this.mockMvc.perform(
                put("/guests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(GuestFixture.getGuestUpdateDto()))

        ).andExpect(status().isNotFound());
    }

    @Test
    void updateGuestShouldThrowGuestEntityNotFoundExceptionWith400() throws Exception {
        when(guestService.updateGuest(anyString(), any(GuestUpdateDto.class)))
                .thenThrow(GuestDocumentNotFoundException.class);

        this.mockMvc.perform(
                put("/guests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(GuestFixture.getGuestDtoList()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deleteGuestShouldRemoveOneGuest() throws Exception {
        when(guestControllerMapper.fromGuestDtoToGuest(any(GuestDto.class)))
                .thenReturn(GuestControllerFixture.getGuest(true));
        when(orderGuestFacade.deleteGuest(anyString()))
                .thenReturn(GuestFixture.getGuestDto());

        this.mockMvc.perform(
                delete("/guests/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isAccepted()).andExpect(content()
                .json(objectMapper.writeValueAsString(GuestControllerFixture.getGuest(true)))
        );
    }

    @Test
    void deleteGuestShouldThrowGuestEntityNotFoundExceptionWith404() throws Exception {
        when(orderGuestFacade.deleteGuest(anyString()))
                .thenThrow(GuestDocumentNotFoundException.class);

        this.mockMvc.perform(
                delete("/guests/1")
        ).andExpect(status().isNotFound());
    }

    @Test
    void createGuestShouldReturnCreatedGuest() throws Exception {
        when(guestControllerMapper.fromGuestDtoToGuest(any(GuestDto.class)))
                .thenReturn(GuestControllerFixture.getGuest(true));
        when(orderGuestFacade.createGuest(any(GuestCreationDto.class), anyString()))
                .thenReturn(GuestFixture.getGuestDto());

        this.mockMvc.perform(
                post("/guests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(GuestFixture.getGuestCreationDto()))
        ).andExpect(status().isCreated()).andExpect(content()
                .json(objectMapper.writeValueAsString(GuestControllerFixture.getGuest(true)))
        );
    }

    @Test
    void createGuestShouldThrowGuestEntityNotFoundExceptionWith404() throws Exception {
        when(orderGuestFacade.createGuest(any(GuestCreationDto.class), anyString()))
                .thenThrow(OrderDocumentNotFoundException.class);

        this.mockMvc.perform(
                post("/guests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(GuestFixture.getGuestCreationDto()))
        ).andExpect(status().isNotFound());
    }

    @Test
    void getGuestShouldReturnOneGuest() throws Exception {
        when(guestControllerMapper.fromGuestDtoToGuest(any(GuestDto.class)))
                .thenReturn(GuestControllerFixture.getGuest(true));
        when(guestService.getGuest(anyString()))
                .thenReturn(GuestFixture.getGuestDto());

        this.mockMvc.perform(
                get("/guests/guest/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(GuestControllerFixture.getGuest(true)))
        );
    }

    @Test
    void getGuestsShouldReturnAllGuest() throws Exception {
        when(guestControllerMapper.fromGuestDtoToGuest(any(GuestDto.class)))
                .thenReturn(GuestControllerFixture.getGuest(true));
        when(guestService.getGuests())
                .thenReturn(GuestFixture.getGuestDtoList());

        this.mockMvc.perform(
                get("/guests")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .json(objectMapper.writeValueAsString(GuestControllerFixture.getGuestList()))
        );
    }
}
