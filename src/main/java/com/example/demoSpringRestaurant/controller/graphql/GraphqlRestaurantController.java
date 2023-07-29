package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.mapper.controller.RestaurantControllerMapper;
import com.example.demoSpringRestaurant.model.service.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.controller.Restaurant;
import com.example.demoSpringRestaurant.model.service.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GraphqlRestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantOrderFacade restaurantOrderFacade;
    private final RestaurantControllerMapper restaurantControllerMapper;

    @QueryMapping("restaurants")
    public List<Restaurant> getRestaurants() {
        log.debug("Requested all restaurants");
        var restaurantList = restaurantService.getRestaurants().stream()
                .map(restaurantControllerMapper::fromRestaurantDtoToRestaurant).toList();
        log.debug("Restaurants returned successfully");
        return restaurantList;
    }

    @QueryMapping("restaurant")
    public Restaurant getRestaurantById(@Argument String id) {

        try {
            log.debug("Requested restaurant with ID: " + id);
            var restaurantDto = restaurantService.getRestaurant(id);
            var restaurant = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
            log.debug("Restaurant with ID: " + id + " returned successfully");
            return restaurant;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


    @MutationMapping("createRestaurant")
    public Restaurant createRestaurant(@Valid @RequestBody @Argument RestaurantCreationDto restaurant) {
        log.debug("Creating a new restaurant");
        var restaurantDto = restaurantService.createRestaurant(restaurant);
        var restaurantResponse = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
        log.debug("New restaurant created successfully");
        return restaurantResponse;
    }

    @MutationMapping("deleteRestaurant")
    public Restaurant deleteRestaurant(@Argument String id) {
        try {
            log.debug("Deleting a restaurant with ID: " + id);
            var restaurantDto = restaurantOrderFacade.deleteRestaurant(id);
            var restaurant = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
            log.debug("Restaurant with ID: " + id + " deleted successfully");
            return restaurant;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("updateRestaurant")
    public Restaurant updateRestaurant(@Argument String id, @Valid @RequestBody @Argument RestaurantUpdateDto restaurant) {
        try {
            log.debug("Updating restaurant with ID: " + id);
            var restaurantDto = restaurantService.updateRestaurant(id, restaurant);
            var restaurantResponse = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
            log.debug("Restaurant with ID: " + id + " updated successfully");
            return restaurantResponse;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @QueryMapping("vegan")
    public List<Restaurant> getVeganRestaurants() {
        log.debug("Requested vegan restaurants");
        var restaurantDto = restaurantService.getVeganRestaurant().stream()
                .map(restaurantControllerMapper::fromRestaurantDtoToRestaurant).toList();
        log.debug("Vegan restaurants returned successfully");
        return restaurantDto;
    }
}
