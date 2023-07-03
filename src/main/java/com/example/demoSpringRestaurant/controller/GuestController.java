package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderGuestFacade;
import com.example.demoSpringRestaurant.model.GuestCreationDto;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.service.GuestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("{guestId}")
    public GuestDto createGuest(@Valid @RequestBody GuestCreationDto GuestCreationDto,
                                @PathVariable("guestId") String guestId) {
        log.debug("Creating a guest");
        var guest = orderGuestFacade.createGuest(GuestCreationDto, guestId);
        log.debug("Created a guest successfully");
        return guest;
    }

    @DeleteMapping(path = "{guestId}")
    public GuestDto deleteGuest(@PathVariable("guestId") String guestId) {
        try {
            log.debug("Deleting an guest");
            var guest = orderGuestFacade.deleteGuest(guestId);
            log.debug("guest with ID: " + guestId + " deleted successfully");
            return guest;
        } catch (DocumentNotFoundException e) {
            log.warn("Deleting an guest were unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

}
