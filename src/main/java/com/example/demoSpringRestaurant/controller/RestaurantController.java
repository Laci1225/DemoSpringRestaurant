package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * The RestaurantController class handles HTTP requests related to restaurants.
 * It provides endpoints for getting, creating, deleting, updating and  restaurants
 */
@RestController
@RequestMapping(path = "restaurants")
@AllArgsConstructor
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantOrderFacade restaurantOrderFacade;

    /**
     * Returns a list of orders by restaurant ID.
     *
     * @return A list of {@link RestaurantDto} objects representing the restaurants.
     * @throws ResponseStatusException If there is a server error with 500 status code.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<RestaurantDto> getRestaurant() {
        try {
            log.debug("Requested all restaurant");
            var restaurantList = restaurantService.getRestaurants();
            log.debug("Restaurants returned successfully");
            return restaurantList;
        } catch (Exception e) {
            log.error("Server error");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new restaurant.
     *
     * @param restaurantCreationDto The {@link RestaurantCreationDto} object containing the restaurant details.
     * @return The created {@link RestaurantDto} object representing the new restaurant.
     * @throws ResponseStatusException If there is a server error with 500 status code.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDto addRestaurant(@Valid @RequestBody RestaurantCreationDto restaurantCreationDto) {
        try {
            log.debug("Creating a restaurant");
            var restaurant = restaurantService.createRestaurant(restaurantCreationDto);
            log.debug("Created a restaurant successfully");
            return restaurant;
        } catch (Exception e) {
            log.error("Server error");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a restaurant by its ID.
     *
     * @param restaurantId The ID of the restaurant to be deleted.
     * @return The deleted {@link RestaurantDto} object representing the deleted restaurant.
     * @throws ResponseStatusException If the {@link RestaurantEntity} is not found with 404 status code
     *                                 or if there is a server error with 500 status code.
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "{restaurantId}")
    public RestaurantDto deleteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        try {
            log.debug("Deleting a restaurant");
            var restaurant = restaurantOrderFacade.deleteRestaurant(restaurantId);
            log.debug("Restaurant with ID: " + restaurantId + " deleted successfully");
            return restaurant;
        } catch (RestaurantEntityNotFoundException e) {
            log.warn("Deleting a restaurant was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error("Server error");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates a restaurant by its ID.
     *
     * @param restaurantId        The ID of the restaurant to be updated.
     * @param restaurantUpdateDto The {@link RestaurantUpdateDto} object containing the updated restaurant details.
     * @return The updated {@link RestaurantDto} object representing the updated restaurant.
     * @throws ResponseStatusException If the {@link RestaurantEntity} is not found with 404 status code
     *                                 or if there is a server error with 500 status code.
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "{restaurantId}")
    public RestaurantDto updateRestaurant(
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody RestaurantUpdateDto restaurantUpdateDto) {
        try {
            log.debug("Updating restaurant with ID: " + restaurantId);
            var restaurant = restaurantService.updateRestaurant(restaurantId, restaurantUpdateDto);
            log.debug("Updating restaurant with ID: " + restaurantId + " was successful");
            return restaurant;
        } catch (RestaurantEntityNotFoundException e) {
            log.warn("Updating a restaurant was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error("Server error");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates specific parameters of a restaurant by its ID.
     *
     * @param restaurantId        The ID of the restaurant.
     * @param restaurantUpdateDto The {@link RestaurantUpdateDto} object containing the updated restaurant parameters.
     * @return The updated {@link RestaurantDto} object representing the restaurant with updated parameters.
     * @throws ResponseStatusException If the {@link RestaurantEntity} is not found with 404 status code
     *                                 or if there is a server error with 500 status code.
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "{restaurantId}")
    public RestaurantDto updateParametersInRestaurant(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody RestaurantUpdateDto restaurantUpdateDto) {
        try {
            log.debug("Updating restaurant's parameter with ID: " + restaurantId);
            var restaurant = restaurantService.updateParametersInRestaurant(restaurantId, restaurantUpdateDto);
            log.debug("Updating restaurant's parameter with ID: " + restaurantId + " was successful");
            return restaurant;
        } catch (RestaurantEntityNotFoundException e) {
            log.warn("Updating a restaurant's parameter was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            log.error("Server error");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a list of all vegan restaurants.
     *
     * @return A list of {@link RestaurantDto} objects representing the vegan restaurants.
     * @throws ResponseStatusException If there is a server error.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("vegan")
    public List<RestaurantDto> getVeganRestaurants() {
        try {
            log.debug("Requested all vegan restaurant");
            var restaurantList = restaurantService.getVeganRestaurant();
            log.debug("Vegan restaurants returned successfully");
            return restaurantList;
        } catch (Exception e) {
            log.error("Server error");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
