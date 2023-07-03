package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.GuestMapper;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.model.GuestCreationDto;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.service.GuestService;
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

    public GuestDto createGuest(GuestCreationDto guestCreationDto, String orderId) {
        log.trace("Creating Guest " + guestCreationDto);
        var guestDocument = guestMapper.fromGuestCreationDtoToDocument(guestCreationDto);
        var orderDocument = orderService.findById(orderId)
                .orElseThrow();
        orderDocument.setGuestDocument(guestDocument);
        guestDocument.setActiveOrder(orderDocument);


        var guestDoc = guestService.saveGuest(guestDocument);
        orderService.saveOrder(orderDocument);

        log.trace("Guest created with ID:" + guestDocument.getId());
        return guestMapper.fromDocumentToGuestDto(guestDoc);
    }

    public GuestDto deleteGuest(String guestId) throws DocumentNotFoundException {
        var guest = guestService.findById(guestId)
                .orElseThrow(() -> new DocumentNotFoundException("Guest not found"));
        var order = guest.getActiveOrder();
        orderService.deleteById(order.getId());
        guestService.deleteById(guest.getId());
        return guestMapper.fromDocumentToGuestDto(guest);
    }
}
