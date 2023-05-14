package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantOrderFacade restaurantOrderFacade;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, RestaurantOrderFacade restaurantOrderFacade) {
        this.restaurantService = restaurantService;
        this.restaurantOrderFacade = restaurantOrderFacade;
    }

    @GetMapping
    public List<RestaurantDto> getRestaurant() {
        return restaurantService.getRestaurants();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDto addRestaurant(@RequestBody RestaurantCreationDto restaurant) {
        return restaurantService.addRestaurant(restaurant);
    }

    @DeleteMapping(path = "{restaurantId}")
    public void removeRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        restaurantOrderFacade.removeRestaurant(restaurantId);
    }

    @GetMapping("owner")
    public Map<String, List<String>> getRestaurantsByOwner() {
        return restaurantService.getRestaurantsByOwner();
    }

    @PutMapping(path = "{restaurantId}")
    public void updateRestaurant(
            @PathVariable("restaurantId") Long id,
            @Valid @RequestBody RestaurantUpdateDto restaurantUpdateDto) {
        restaurantService.updateRestaurant(id, restaurantUpdateDto);
    }
    @PatchMapping(path = "{restaurantId}")
    public RestaurantDto updateParametersInRestaurant(
            @PathVariable("restaurantId") Long id,
            @RequestBody RestaurantDto restaurantDto) {
        try {
            return restaurantService.updateParametersInRestaurant(id,restaurantDto);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        }
    }

}
