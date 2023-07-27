package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.GuestDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderGuestFacade;
import com.example.demoSpringRestaurant.model.service.GuestCreationDto;
import com.example.demoSpringRestaurant.model.service.GuestDto;
import com.example.demoSpringRestaurant.model.service.GuestUpdateDto;
import com.example.demoSpringRestaurant.service.GuestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@AllArgsConstructor
public class GraphqlGuestController {
    private final GuestService guestService;
    private final OrderGuestFacade orderGuestFacade;

    @QueryMapping("guests")
    public List<GuestDto> getGuests() {
        System.out.println("Requested all guests");
        var guests = guestService.getGuests();
        System.out.println("Guests returned successfully");
        return guests;
    }

    @QueryMapping("guest")
    public GuestDto getGuest(@Argument String id) {
        try {
            System.out.println("Requested guest with ID: " + id);
            var guest = guestService.getGuest(id);
            System.out.println("Guest with ID: " + id + " returned successfully");
            return guest;
        } catch (GuestDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("createGuest")
    public GuestDto createGuest(@Argument @Valid @RequestBody GuestCreationDto guest, @Argument String id) {
        try {
            System.out.println("Creating a guest");
            var guestDto = orderGuestFacade.createGuest(guest, id);
            System.out.println("Guest created successfully");
            return guestDto;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("deleteGuest")
    public GuestDto deleteGuest(@Argument String id) {
        try {
            System.out.println("Deleting a guest with ID: " + id);
            var guestDto = orderGuestFacade.deleteGuest(id);
            System.out.println("Guest with ID: " + id + " deleted successfully");
            return guestDto;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("updateGuest")
    public GuestDto updateGuest(@Argument String id, @Valid @RequestBody @Argument GuestUpdateDto guest) {
        try {
            System.out.println("Updating guest with ID: " + id);
            var guestUpdate = guestService.updateGuest(id, guest);
            System.out.println("Guest with ID: " + id + " updated successfully");
            return guestUpdate;
        } catch (GuestDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
