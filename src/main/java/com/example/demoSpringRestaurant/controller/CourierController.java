package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.service.CourierService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public List<CourierDto> getCouriers() {
        log.debug("Requested all couriers");
        var courierList = courierService.getCouriers();
        log.debug("Couriers returned successfully");
        return courierList;
    }

    @GetMapping(path = "{courierId}")
    public CourierDto getCourier(@PathVariable("courierId") String courierId) {
        log.debug("Requested all couriers");
        var courierList = courierService.getCourier(courierId);
        log.debug("Couriers returned successfully");
        return courierList;
    }

    @PostMapping
    public CourierDto createCourier(@Valid @RequestBody CourierCreationDto courierCreationDto) {
        log.debug("Creating a courier");
        var courier = courierService.createCourier(courierCreationDto);
        log.debug("Created a courier successfully");
        return courier;
    }

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
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "{courierId}")
    public CourierDto updatecourier(
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
