package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "restaurants")
//@ComponentScan("com.example.demoSpringRestaurant")
public class RestaurantController {

    private final RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public List<RestaurantDto> getRestaurant() {
        return service.getRestaurants();
    }

    @PostMapping
    public RestaurantEntity addRestaurant(@RequestBody RestaurantCreationDto restaurant) {
       return service.addRestaurant(restaurant);
    }

    @DeleteMapping(path = "{restaurantId}")
    public void removeRestaurant(@PathVariable("restaurantId") Long id) {
        service.removeRestaurant(id);
    }

    @GetMapping("owner")
    public Map<String, List<String>> getRestaurantsByOwner() {
        return service.getRestaurantsByOwner();
    }

    @PutMapping(path = "{restaurantId}")
    public void updateRestaurant(
            @PathVariable("restaurantId") Long id,
            @Valid @RequestBody RestaurantUpdateDto restaurantUpdateDto) {
        service.updateRestaurant(id,restaurantUpdateDto);
    }

}
