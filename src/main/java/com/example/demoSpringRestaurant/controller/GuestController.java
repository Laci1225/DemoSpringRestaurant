package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.GuestDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderGuestFacade;
import com.example.demoSpringRestaurant.model.GuestCreationDto;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.model.GuestUpdateDto;
import com.example.demoSpringRestaurant.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Operation(summary = "Returns all guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All guest found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GuestDto.class)))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<GuestDto> getGuests() {
        log.debug("Requested all guests");
        var guestList = guestService.getGuests();
        log.debug("Guests returned successfully");
        return guestList;
    }
    @Operation(summary = "Returns a guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A guest found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GuestDto.class))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("guest/{guestId}")
    public GuestDto getGuest(@PathVariable(value = "guestId") String id) {
        log.debug("Requested a guests");
        var guestList = guestService.getGuest(id);
        log.debug("Guests returned successfully");
        return guestList;
    }

    @Operation(summary = "Creates a guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A guest created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GuestDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{orderId}")
    public GuestDto createGuest(@Valid @RequestBody GuestCreationDto GuestCreationDto,
                                @PathVariable("orderId") String orderId) {
        try {
            log.debug("Creating a guest");
            GuestDto guest = orderGuestFacade.createGuest(GuestCreationDto, orderId);
            log.debug("Created a guest successfully");
            return guest;
        } catch (OrderDocumentNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    @Operation(summary = "Deletes a guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "A guest deleted",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GuestDto.class))),
            @ApiResponse(responseCode = "404", description = "Guest/order not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.ACCEPTED)
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
    @Operation(summary = "Updates a guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A guest updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GuestDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Guest not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "{guestId}")
    public GuestDto updateGuest(
            @PathVariable("guestId") String guestId,
            @Valid @RequestBody GuestUpdateDto guestUpdateDto) {
        try {
            log.debug("Updating guest with ID: " + guestId);
            var guest = guestService.updateGuest(guestId, guestUpdateDto);
            log.debug("Updating guest with ID: " + guestId + " was successful");
            return guest;
        } catch (GuestDocumentNotFoundException e) {
            log.warn("Updating a guest was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

}
