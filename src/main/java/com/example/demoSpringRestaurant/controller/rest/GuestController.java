package com.example.demoSpringRestaurant.controller.rest;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.GuestDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderGuestFacade;
import com.example.demoSpringRestaurant.mapper.controller.GuestControllerMapper;
import com.example.demoSpringRestaurant.model.service.GuestCreationDto;
import com.example.demoSpringRestaurant.model.controller.Guest;
import com.example.demoSpringRestaurant.model.service.GuestUpdateDto;
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
@RequestMapping(path = "guests")
@Slf4j
public class GuestController {

    private final GuestService guestService;
    private final OrderGuestFacade orderGuestFacade;
    private final GuestControllerMapper guestControllerMapper;

    @Operation(summary = "Returns all guests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All guests found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Guest.class)))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Guest> getGuests() {
        log.debug("Requested all guests");
        var guestList = guestService.getGuests().stream()
                .map(guestControllerMapper::fromGuestDtoToGuest).toList();
        log.debug("Guests returned successfully");
        return guestList;
    }

    @Operation(summary = "Returns a guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A guest found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Guest.class))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("guest/{guestId}")
    public Guest getGuest(@PathVariable(value = "guestId") String id) {
        try {
            log.debug("Requested a guest");
            var guestDto = guestService.getGuest(id);
            var guest = guestControllerMapper.fromGuestDtoToGuest(guestDto);
            log.debug("Guest returned successfully");
            return guest;
        } catch (GuestDocumentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Creates a guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A guest created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Guest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{orderId}")
    public Guest createGuest(@Valid @RequestBody GuestCreationDto guestCreationDto,
                             @PathVariable("orderId") String orderId) {
        try {
            log.debug("Creating a guest");
            var guestDto = orderGuestFacade.createGuest(guestCreationDto, orderId);
            var guest = guestControllerMapper.fromGuestDtoToGuest(guestDto);
            log.debug("Created a guest successfully");
            return guest;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

    }

    @Operation(summary = "Deletes a guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "A guest deleted",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Guest.class))),
            @ApiResponse(responseCode = "404", description = "Guest/order not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{guestId}")
    public Guest deleteGuest(@PathVariable("guestId") String guestId) {
        try {
            log.debug("Deleting a guest");
            var guestDto = orderGuestFacade.deleteGuest(guestId);
            var guest = guestControllerMapper.fromGuestDtoToGuest(guestDto);
            log.debug("Guest with ID: " + guestId + " deleted successfully");
            return guest;
        } catch (DocumentNotFoundException e) {
            log.warn("Deleting a guest was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Operation(summary = "Updates a guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A guest updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Guest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Guest not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{guestId}")
    public Guest updateGuest(
            @PathVariable("guestId") String guestId,
            @Valid @RequestBody GuestUpdateDto guestUpdateDto) {
        try {
            log.debug("Updating guest with ID: " + guestId);
            var guestDto = guestService.updateGuest(guestId, guestUpdateDto);
            var guest = guestControllerMapper.fromGuestDtoToGuest(guestDto);
            log.debug("Updating guest with ID: " + guestId + " was successful");
            return guest;
        } catch (GuestDocumentNotFoundException e) {
            log.warn("Updating a guest was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
