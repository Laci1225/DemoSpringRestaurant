package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.model.CourierCreationDto;
import com.example.demoSpringRestaurant.model.CourierDto;
import com.example.demoSpringRestaurant.model.CourierUpdateDto;
import com.example.demoSpringRestaurant.service.CourierService;
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
@RequestMapping(path = "courier")
@Slf4j
public class CourierController {
    private final CourierService courierService;
    private final OrderCourierFacade orderCourierFacade;
    @Operation(summary = "Returns all courier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All courier found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CourierDto.class)))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CourierDto> getCouriers() {
        log.debug("Requested all couriers");
        var courierList = courierService.getCouriers();
        log.debug("Couriers returned successfully");
        return courierList;
    }
    @Operation(summary = "Returns a courier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A courier found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CourierDto.class))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "{courierId}")
    public CourierDto getCourier(@PathVariable("courierId") String courierId) {
        log.debug("Requested all couriers");
        var courierList = courierService.getCourier(courierId);
        log.debug("Couriers returned successfully");
        return courierList;
    }
    @Operation(summary = "Creates a courier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A courier created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CourierDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CourierDto createCourier(@Valid @RequestBody CourierCreationDto courierCreationDto) {
        log.debug("Creating a courier");
        var courier = courierService.createCourier(courierCreationDto);
        log.debug("Created a courier successfully");
        return courier;
    }
    @Operation(summary = "Add order to courier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "An order added to a courier",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CourierDto.class))),
            @ApiResponse(responseCode = "404", description = "Courier not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "add/{courierId}/{orderId}")
    public CourierDto addOrderToCourier(@PathVariable(value = "courierId") String courierId
    ,@PathVariable(value = "orderId") String orderId){
        try {
            return orderCourierFacade.addOrderToCourier(courierId,orderId);
        } catch (CourierDocumentNotFoundException | OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
    @Operation(summary = "Add order set to active")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "An order set active",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CourierDto.class))),
            @ApiResponse(responseCode = "404", description = "Courier not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "set/{courierId}/{orderId}")
    public CourierDto setOrderActive(@PathVariable(value = "courierId") String courierId
    ,@PathVariable(value = "orderId") String orderId){
        try {
            return orderCourierFacade.setOrderActive(courierId,orderId);
        } catch (CourierDocumentNotFoundException | OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
   /* @Operation(summary = "Deletes a courier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "A courier deleted",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CourierDto.class))),
            @ApiResponse(responseCode = "404", description = "Courier not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "{courierId}")
    public CourierDto deleteCourier(@PathVariable("courierId") String courierId) {
        try {
            log.debug("Deleting an courier");
            var courier = orderCourierFacade.deleteCourier(courierId);
            log.debug("Courier with ID: " + courierId + " deleted successfully");
            return courier;
        } catch (DocumentNotFoundException e) {
            log.warn("Deleting a courier were unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }*/

    @Operation(summary = "Updates a courier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A courier updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CourierDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Guest not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "{courierId}")
    public CourierDto updateCourier(
            @PathVariable("courierId") String courierId,
            @Valid @RequestBody CourierUpdateDto courierUpdateDto) {
        try {
            log.debug("Updating courier with ID: " + courierId);
            var courier = courierService.updateCourier(courierId, courierUpdateDto);
            log.debug("Updating courier with ID: " + courierId + " was successful");
            return courier;
        } catch (DocumentNotFoundException e) {
            log.warn("Updating a courier was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
