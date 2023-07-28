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
public class GraphqlRestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantOrderFacade restaurantOrderFacade;
    private final RestaurantControllerMapper restaurantControllerMapper;

    @QueryMapping("restaurants")
    public List<Restaurant> getRestaurants() {
        System.out.println("Requested all restaurants");
        var restaurantList = restaurantService.getRestaurants().stream()
                .map(restaurantControllerMapper::fromRestaurantDtoToRestaurant).toList();
        System.out.println("Restaurants returned successfully");
        return restaurantList;
    }

    @QueryMapping("restaurant")
    public Restaurant getRestaurantById(@Argument String id) {

        try {
            System.out.println("Requested restaurant with ID: " + id);
            var restaurantDto = restaurantService.getRestaurant(id);
            var restaurant = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
            System.out.println("Restaurant with ID: " + id + " returned successfully");
            return restaurant;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


    @MutationMapping("createRestaurant")
    public Restaurant createRestaurant(@Valid @RequestBody @Argument RestaurantCreationDto restaurant) {
        System.out.println("Creating a new restaurant");
        var restaurantDto = restaurantService.createRestaurant(restaurant);
        var restaurantResponse = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
        System.out.println("New restaurant created successfully");
        return restaurantResponse;
    }

    @MutationMapping("deleteRestaurant")
    public Restaurant deleteRestaurant(@Argument String id) {
        try {
            System.out.println("Deleting a restaurant with ID: " + id);
            var restaurantDto = restaurantOrderFacade.deleteRestaurant(id);
            var restaurant = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
            System.out.println("Restaurant with ID: " + id + " deleted successfully");
            return restaurant;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("updateRestaurant")
    public Restaurant updateRestaurant(@Argument String id, @Valid @RequestBody @Argument RestaurantUpdateDto restaurant) {
        try {
            System.out.println("Updating restaurant with ID: " + id);
            var restaurantDto = restaurantService.updateRestaurant(id, restaurant);
            var restaurantResponse = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
            System.out.println("Restaurant with ID: " + id + " updated successfully");
            return restaurantResponse;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @QueryMapping("vegan")
    public List<Restaurant> getVeganRestaurants() {
        System.out.println("Requested vegan restaurants");
        var restaurantDto = restaurantService.getVeganRestaurant().stream()
                .map(restaurantControllerMapper::fromRestaurantDtoToRestaurant).toList();
        System.out.println("Vegan restaurants returned successfully");
        return restaurantDto;
    }
}
