package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.service.GuestMapper;
import com.example.demoSpringRestaurant.mapper.service.OrderMapper;
import com.example.demoSpringRestaurant.model.service.GuestDto;
import com.example.demoSpringRestaurant.model.service.GuestCreationDto;
import com.example.demoSpringRestaurant.service.GuestService;
import com.example.demoSpringRestaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderGuestFacade {
    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final GuestMapper guestMapper;
    private final GuestService guestService;

    public GuestDto createGuest(GuestCreationDto guestCreationDto, String orderId) throws OrderDocumentNotFoundException {
        log.trace("Creating Guest " + guestCreationDto);

        var guestDto = guestMapper.fromCreationDtoToDto(guestCreationDto);
        var orderDto = orderService.findOrderById(orderId);

        var guest = guestService.saveGuest(guestDto);
        guest.setActiveOrder(orderId);
        var guestWithActiveOrderId = guestService.saveGuest(guest);
        orderDto.setGuest(guestWithActiveOrderId);
        orderService.saveOrder(orderDto);

        log.trace("Guest created with ID:" + guestDto.getId());

        return guestWithActiveOrderId;
    }

    public GuestDto deleteGuest(String guestId) throws DocumentNotFoundException {
        var guestDto = guestService.findGuestById(guestId);
        var orderId = guestDto.getActiveOrder();
        orderService.deleteById(orderId);
        guestService.deleteById(guestDto.getId());
        return guestDto;
    }
}
