package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.GuestDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderGuestFacade;
import com.example.demoSpringRestaurant.mapper.controller.GuestControllerMapper;
import com.example.demoSpringRestaurant.model.service.GuestCreationDto;
import com.example.demoSpringRestaurant.model.controller.Guest;
import com.example.demoSpringRestaurant.model.service.GuestUpdateDto;
import com.example.demoSpringRestaurant.service.GuestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class GraphqlGuestController {
    private final GuestService guestService;
    private final OrderGuestFacade orderGuestFacade;
    private final GuestControllerMapper guestControllerMapper;

    @QueryMapping("guests")
    public List<Guest> getGuests() {
        log.debug("Requested all guests");
        var guests = guestService.getGuests().stream()
                .map(guestControllerMapper::fromGuestDtoToGuest).toList();
        log.debug("Guests returned successfully");
        return guests;
    }

    @QueryMapping("guest")
    public Guest getGuest(@Argument String id) {
        try {
            log.debug("Requested guest with ID: " + id);
            var guestDto = guestService.getGuest(id);
            var guest = guestControllerMapper.fromGuestDtoToGuest(guestDto);
            log.debug("Guest with ID: " + id + " returned successfully");
            return guest;
        } catch (GuestDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("createGuest")
    public Guest createGuest(@Argument @Valid @RequestBody GuestCreationDto guest, @Argument String id) {
        try {
            log.debug("Creating a guest");
            var guestDto = orderGuestFacade.createGuest(guest, id);
            var guestResponse = guestControllerMapper.fromGuestDtoToGuest(guestDto);
            log.debug("Guest created successfully");
            return guestResponse;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("deleteGuest")
    public Guest deleteGuest(@Argument String id) {
        try {
            log.debug("Deleting a guest with ID: " + id);
            var guestDto = orderGuestFacade.deleteGuest(id);
            var guest = guestControllerMapper.fromGuestDtoToGuest(guestDto);
            log.debug("Guest with ID: " + id + " deleted successfully");
            return guest;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("updateGuest")
    public Guest updateGuest(@Argument String id, @Valid @RequestBody @Argument GuestUpdateDto guest) {
        try {
            log.debug("Updating guest with ID: " + id);
            var guestDto = guestService.updateGuest(id, guest);
            var guestResponse = guestControllerMapper.fromGuestDtoToGuest(guestDto);
            log.debug("Guest with ID: " + id + " updated successfully");
            return guestResponse;
        } catch (GuestDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
