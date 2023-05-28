package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantOrderFacade restaurantOrderFacade;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<RestaurantDto> getRestaurant() {
        return restaurantService.getRestaurants();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDto addRestaurant(@RequestBody RestaurantCreationDto restaurant) {
        return restaurantService.addRestaurant(restaurant);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "{restaurantId}")
    public RestaurantDto removeRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        try {
            return restaurantOrderFacade.removeRestaurant(restaurantId);
        } catch (RestaurantEntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping(path = "{restaurantId}")
    public RestaurantDto updateRestaurant(
            @PathVariable("restaurantId") Long id,
            @Valid @RequestBody RestaurantUpdateDto restaurantUpdateDto) {
        try {
            return restaurantService.updateRestaurant(id, restaurantUpdateDto);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PatchMapping(path = "{restaurantId}")
    public RestaurantDto updateParametersInRestaurant(
            @PathVariable("restaurantId") Long id,
            @RequestBody RestaurantUpdateDto restaurantUpdateDto) {
        try {
            return restaurantService.updateParametersInRestaurant(id, restaurantUpdateDto);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("vegan")
    public List<RestaurantDto> getVeganRestaurants(){
        return restaurantService.getVeganRestaurant();
    }

}
