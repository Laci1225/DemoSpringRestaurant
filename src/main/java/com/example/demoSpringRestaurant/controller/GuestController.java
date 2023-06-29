package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.facade.OrderGuestFacade;
import com.example.demoSpringRestaurant.model.GuestCreationDto;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.service.GuestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "guest")
@Slf4j
public class GuestController {

    private final GuestService guestService;
    private final OrderGuestFacade orderGuestFacade;

    @GetMapping
    public List<GuestDto> getGuests() {
        log.debug("Requested all guests");
        var guestList = guestService.getGuests();
        log.debug("Guests returned successfully");
        return guestList;
    }

    @PostMapping("{orderId}")
    public GuestDto createGuest(@Valid @RequestBody GuestCreationDto GuestCreationDto,
                                @PathVariable("orderId") String orderId) {
        log.debug("Creating a guest");
        var guest = orderGuestFacade.createGuest(GuestCreationDto, orderId);
        log.debug("Created a guest successfully");
        return guest;
    }
}
