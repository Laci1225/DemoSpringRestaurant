package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.service.CourierService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "courier")
@Slf4j
public class CourierController {
    private final CourierService courierService;

    @GetMapping
    public List<CourierDto> getCouriers() {
        log.debug("Requested all couriers");
        var courierList = courierService.getCouriers();
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
}
