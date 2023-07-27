package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.model.service.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.service.RestaurantDto;
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

        @QueryMapping("restaurants")
        public List<RestaurantDto> getRestaurants() {
            // Logging
            System.out.println("Requested all restaurants");
            var restaurantList = restaurantService.getRestaurants();
            System.out.println("Restaurants returned successfully");
            return restaurantList;
        }
        /*@QueryMapping("restaurant") //TODO model.graphql.GraphqlRestaurantDto
            // TODO mapper.graphql.GraphqlRestaurantMapper
            public RestaurantDto getRestaurantById(@Argument String id) {
                var restaurant = restaurantService.getRestaurant(id);
                return restaurant;
                //TODO hibakezelés rossz idra graphql hiba máshogy nézzen ki + default szépen nem kell teszt
                // springboot error handling
            }*/
        @QueryMapping("restaurant")
        public RestaurantDto getRestaurantById(@Argument String id) {
            // Logging
            System.out.println("Requested restaurant with ID: " + id);
            var restaurant = restaurantService.getRestaurant(id);
            System.out.println("Restaurant with ID: " + id + " returned successfully");
            return restaurant;
        }

        @MutationMapping("createRestaurant")
        public RestaurantDto createRestaurant(@Valid @RequestBody @Argument RestaurantCreationDto restaurant) {
            // Logging
            System.out.println("Creating a new restaurant");
            var restaurantDto = restaurantService.createRestaurant(restaurant);
            System.out.println("New restaurant created successfully");
            return restaurantDto;
        }

        @MutationMapping("deleteRestaurant")
        public RestaurantDto deleteRestaurant(@Argument String id) {
            try {
                // Logging
                System.out.println("Deleting a restaurant with ID: " + id);
                var restaurantDto = restaurantOrderFacade.deleteRestaurant(id);
                System.out.println("Restaurant with ID: " + id + " deleted successfully");
                return restaurantDto;
            } catch (RestaurantDocumentNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
            }
        }

        @MutationMapping("updateRestaurant")
        public RestaurantDto updateRestaurant(@Argument String id, @Valid @RequestBody @Argument RestaurantUpdateDto restaurant) {
            try {
                // Logging
                System.out.println("Updating restaurant with ID: " + id);
                var restaurantUpdate = restaurantService.updateRestaurant(id, restaurant);
                System.out.println("Restaurant with ID: " + id + " updated successfully");
                return restaurantUpdate;
            } catch (RestaurantDocumentNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
            }
        }

        @QueryMapping("vegan")
        public List<RestaurantDto> getVeganRestaurants() {
            // Logging
            System.out.println("Requested vegan restaurants");
            var restaurantDto = restaurantService.getVeganRestaurant();
            System.out.println("Vegan restaurants returned successfully");
            return restaurantDto;
        }
    }
