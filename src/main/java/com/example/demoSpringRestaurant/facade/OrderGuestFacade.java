package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.GuestMapper;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.model.GuestCreationDto;
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
        orderDto.setGuestDto(guestDto);
        guestDto.setActiveOrder(orderDto);


        var guest = guestService.saveGuest(guestDto);
        orderService.saveOrder(orderDto);

        log.trace("Guest created with ID:" + guestDto.getId());
        return guest;
    }

    public GuestDto deleteGuest(String guestId) throws DocumentNotFoundException {
        var guestDto = guestService.findGuestById(guestId);
        var order = guestDto.getActiveOrder();
        orderService.deleteById(order.getId());
        guestService.deleteById(guestDto.getId());
        return guestDto;
    }
}
