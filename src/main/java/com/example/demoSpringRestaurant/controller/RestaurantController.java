package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public RestaurantEntity addRestaurant(@RequestBody RestaurantCreationDto restaurant) {
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
}
