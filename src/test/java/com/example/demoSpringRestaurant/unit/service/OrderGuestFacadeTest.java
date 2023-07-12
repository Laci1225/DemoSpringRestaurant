package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderGuestFacade;
import com.example.demoSpringRestaurant.fixtures.GuestFixture;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.mapper.GuestMapper;
import com.example.demoSpringRestaurant.model.GuestCreationDto;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.service.GuestService;
import com.example.demoSpringRestaurant.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@ExtendWith(MockitoExtension.class)
public class OrderGuestFacadeTest {

    @InjectMocks
    private OrderGuestFacade orderGuestFacade;
    @Mock
    private GuestMapper guestMapper;
    @Mock
    private OrderService orderService;
    @Mock
    private GuestService guestService;

    @Test
    void createGuestShouldCreateAGuestToAnOrder() throws OrderDocumentNotFoundException {
        when(guestMapper.fromCreationDtoToDto(any(GuestCreationDto.class)))
                .thenReturn(GuestFixture.getGuestDto());
        when(orderService.findOrderById(anyString()))
                .thenReturn(OrderFixture.getOrderDto());
        when(guestService.saveGuest(any(GuestDto.class)))
                .thenReturn(GuestFixture.getGuestDto());
        when(orderService.saveOrder(any(OrderDto.class)))
                .thenReturn(OrderFixture.getOrderDto());

        var guest = orderGuestFacade.createGuest(GuestFixture.getGuestCreationDto(), "1234");

        assertThat(guest).usingRecursiveComparison().isEqualTo(GuestFixture.getGuestDto());
        verify(orderService, times(1)).findOrderById(anyString());
        verify(guestService, times(1)).saveGuest(any(GuestDto.class));
        verifyNoMoreInteractions(orderService, guestService);
    }

    @Test
    void deleteGuest() throws DocumentNotFoundException {
        when(guestService.findGuestById(anyString()))
                .thenReturn(GuestFixture.getGuestDto());
        doNothing().when(orderService).deleteById(anyString());
        doNothing().when(guestService).deleteById(anyString());

        var guest = orderGuestFacade.deleteGuest("1234");

        assertThat(guest).usingRecursiveComparison().isEqualTo(GuestFixture.getGuestDto());
        verify(guestService, times(1)).findGuestById(anyString());
        verify(guestService, times(1)).deleteById(anyString());
        verify(orderService, times(1)).deleteById(anyString());
    }
}
