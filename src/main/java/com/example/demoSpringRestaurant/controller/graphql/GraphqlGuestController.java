package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.GuestDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderGuestFacade;
import com.example.demoSpringRestaurant.model.*;
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
        var guests = guestService.getGuests();
        return guests;
    }

    @QueryMapping("guest")
    public GuestDto getGuest(@Argument String id) {
        try {
            var guest = guestService.getGuest(id);
            return guest;
        } catch (GuestDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }

    @MutationMapping("createGuest")
    public GuestDto createGuest(@Argument @Valid @RequestBody GuestCreationDto guest, @Argument String id) {
        try {
            var guestDto = orderGuestFacade.createGuest(guest, id);
            return guestDto;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }

    @MutationMapping("deleteGuest")
    public GuestDto deleteGuest(@Argument String id) {
        try {
            var guestDto = orderGuestFacade.deleteGuest(id);
            return guestDto;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }

    @MutationMapping("updateGuest")
    public GuestDto updateGuest(@Argument String id, @Valid @RequestBody @Argument GuestUpdateDto guest) {
        try {
            var guestUpdate = guestService.updateGuest(id, guest);
            return guestUpdate;
        } catch (GuestDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }

    }

}
