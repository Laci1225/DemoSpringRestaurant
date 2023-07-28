package com.example.demoSpringRestaurant.controller.rest;

import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.mapper.controller.RestaurantControllerMapper;
import com.example.demoSpringRestaurant.model.controller.Restaurant;
import com.example.demoSpringRestaurant.model.service.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.service.RestaurantDto;
import com.example.demoSpringRestaurant.model.service.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.RestaurantDocument;
import com.example.demoSpringRestaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final RestaurantControllerMapper restaurantControllerMapper;


    /**
     * Returns a list of orders by restaurant ID.
     *
     * @return A list of {@link RestaurantDto} objects representing the restaurants.
     * @throws ResponseStatusException If there is a server error with 500 status code.
     */
    @Operation(summary = "Returns all restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All restaurant found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Restaurant.class)))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Restaurant> getRestaurants() {
        log.debug("Requested all restaurant");
        var restaurantList = restaurantService.getRestaurants().stream()
                .map(restaurantControllerMapper::fromRestaurantDtoToRestaurant).toList();
        log.debug("Restaurants returned successfully");
        return restaurantList;
    }


    @Operation(summary = "Returns a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A restaurant found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "{restaurantId}")
    public Restaurant getRestaurant(@PathVariable("restaurantId") String id) {
        try {
            log.debug("Requested a restaurant");
            var restaurantDto = restaurantService.getRestaurant(id);
            var restaurant = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
            log.debug("Restaurants returned successfully");
            return restaurant;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new restaurant.
     *
     * @param restaurantCreationDto The {@link RestaurantCreationDto} object containing the restaurant details.
     * @return The created {@link RestaurantDto} object representing the new restaurant.
     * @throws ResponseStatusException If there is a server error with 500 status code.
     */
    @Operation(summary = "Creates a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A restaurant created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant createRestaurant(@Valid @RequestBody RestaurantCreationDto restaurantCreationDto) {
        log.debug("Creating a restaurant");
        var restaurantDto = restaurantService.createRestaurant(restaurantCreationDto);
        var restaurant = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
        log.debug("Created a restaurant successfully");
        return restaurant;
    }

    /**
     * Deletes a restaurant by its ID.
     *
     * @param restaurantId The ID of the restaurant to be deleted.
     * @return The deleted {@link RestaurantDto} object representing the deleted restaurant.
     * @throws ResponseStatusException If the {@link RestaurantDocument} is not found with 404 status code
     *                                 or if there is a server error with 500 status code.
     */
    @Operation(summary = "Deletes a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "A restaurant deleted",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "{restaurantId}")
    public Restaurant deleteRestaurant(@PathVariable("restaurantId") String restaurantId) {
        try {
            log.debug("Deleting a restaurant");
            var restaurantDto = restaurantOrderFacade.deleteRestaurant(restaurantId);
            var restaurant = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
            log.debug("Restaurant with ID: " + restaurantId + " deleted successfully");
            return restaurant;
        } catch (RestaurantDocumentNotFoundException e) {
            log.warn("Deleting a restaurant was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Updates a restaurant by its ID.
     *
     * @param restaurantId        The ID of the restaurant to be updated.
     * @param restaurantUpdateDto The {@link RestaurantUpdateDto} object containing the updated restaurant details.
     * @return The updated {@link RestaurantDto} object representing the updated restaurant.
     * @throws ResponseStatusException If the {@link RestaurantDocument} is not found with 404 status code
     *                                 or if there is a server error with 500 status code.
     */
    @Operation(summary = "Updates a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A restaurant updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "{restaurantId}")
    public Restaurant updateRestaurant(
            @PathVariable("restaurantId") String restaurantId,
            @Valid @RequestBody RestaurantUpdateDto restaurantUpdateDto) {
        try {
            log.debug("Updating restaurant with ID: " + restaurantId);
            var restaurantDto = restaurantService.updateRestaurant(restaurantId, restaurantUpdateDto);
            var restaurant = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
            log.debug("Updating restaurant with ID: " + restaurantId + " was successful");
            return restaurant;
        } catch (RestaurantDocumentNotFoundException e) {
            log.warn("Updating a restaurant was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Updates specific parameters of a restaurant by its ID.
     *
     * @param restaurantId        The ID of the restaurant.
     * @param restaurantUpdateDto The {@link RestaurantUpdateDto} object containing the updated restaurant parameters.
     * @return The updated {@link RestaurantDto} object representing the restaurant with updated parameters.
     * @throws ResponseStatusException If the {@link RestaurantDocument} is not found with 404 status code
     *                                 or if there is a server error with 500 status code.
     */

    @Operation(summary = "Updates a parameter in a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A restaurant parameter is  updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "{restaurantId}")
    public Restaurant updateParametersInRestaurant(
            @PathVariable("restaurantId") String restaurantId,
            @Valid @RequestBody RestaurantUpdateDto restaurantUpdateDto) {
        try {
            log.debug("Updating restaurant's parameter with ID: " + restaurantId);
            var restaurantDto = restaurantService.updateParametersInRestaurant(restaurantId, restaurantUpdateDto);
            var restaurant = restaurantControllerMapper.fromRestaurantDtoToRestaurant(restaurantDto);
            log.debug("Updating restaurant's parameter with ID: " + restaurantId + " was successful");
            return restaurant;
        } catch (RestaurantDocumentNotFoundException e) {
            log.warn("Updating a restaurant's parameter was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Retrieves a list of all vegan restaurants.
     *
     * @return A list of {@link RestaurantDto} objects representing the vegan restaurants.
     * @throws ResponseStatusException If there is a server error.
     */
    @Operation(summary = "Returns all vegan restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All vegan restaurant found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Restaurant.class)))),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("vegan")
    public List<Restaurant> getVeganRestaurants() {
        log.debug("Requested all vegan restaurant");
        var restaurantList = restaurantService.getVeganRestaurant()
                .stream()
                .map(restaurantControllerMapper::fromRestaurantDtoToRestaurant).toList();
        log.debug("Vegan restaurants returned successfully");
        return restaurantList;
    }
}
