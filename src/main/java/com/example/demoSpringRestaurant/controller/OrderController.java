package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.facade.OrderGuestCourierFacade;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.facade.RestaurantOrderGuestCourierFacade;
import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.service.OrderService;
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
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;

import java.util.List;

/**
 * The OrderController class handles HTTP requests related to orders.
 * It provides endpoints for getting, creating, deleting, and updating orders
 */
@RestController
@RequestMapping(path = "orders")
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final RestaurantOrderFacade restaurantOrderFacade;
    private final RestaurantOrderGuestCourierFacade restaurantOrderGuestCourierFacade;
    private final OrderGuestCourierFacade orderGuestCourierFacade;
    private final OrderCourierFacade orderCourierFacade;

    /**
     * Returns a list of orders by restaurant ID.
     *
     * @param restaurantId The ID of the restaurant.
     * @return A list of OrderDto objects representing the orders.
     * @throws ResponseStatusException If the {@link OrderDocument} is not found with 404 status code
     *                                 or if there is a server error with 500 status code.
     */
    @Operation(summary = "Returns all orders in a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All order in a restaurant found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "{restaurantId}")
    public List<OrderDto> getOrdersByRestaurantId(@PathVariable("restaurantId") String restaurantId) {
        try {
            log.debug("Requested all order");
            var orderList = restaurantOrderFacade.getOrdersByRestaurantId(restaurantId);
            log.debug("Orders returned successfully");
            return orderList;
        } catch (RestaurantDocumentNotFoundException e) {
            log.warn("Getting orders were unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Operation(summary = "Returns an order in a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "An order in a restaurant found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "order/{orderId}")
    public OrderDto getOrder(@PathVariable("orderId") String orderId) {
        try {
            log.debug("Requested an order");
            var order = orderService.getOrderById(orderId);
            log.debug("An order returned successfully");
            return order;
        } catch (OrderDocumentNotFoundException e) {
            log.warn("Getting an order were unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
    /**
     * Creates a new order for a given restaurant.
     * <p>
     * //@param orderCreationDto The OrderCreationDto object containing the order details.
     *
     * @param restaurantId The ID of the restaurant.
     * @return The created OrderDto object representing the new order.
     * @throws ResponseStatusException If the {@link OrderDocument} is not found with 404 status code
     *                                 or if there is a server error with 500 status code.
     */
    @Operation(summary = "Creates an order in a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "An order in a restaurant is created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{restaurantId}")
    public OrderDto createOrder(@RequestBody OrderCreationDto orderCreationDto,
                                @PathVariable("restaurantId") String restaurantId) {
        try {
            log.debug("Creating an order");
            var order = restaurantOrderGuestCourierFacade.createOrder(
                    orderCreationDto, restaurantId);
            log.debug("Created an order successfully");
            return order;
        } catch (DocumentNotFoundException e) {
            log.warn("Creating an order was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Deletes an order from a restaurant by its ID.
     *
     * @param orderId The ID of the order to be deleted.
     * @return The deleted OrderDto object representing the deleted order.
     * @throws ResponseStatusException If the {@link OrderDocument} is not found with 404 status code
     *                                 or if there is a server error with 500 status code.
     */
    @Operation(summary = "Deletes an order from a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "An order from a restaurant is deleted",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "{orderId}")
    public OrderDto deleteOrder(@PathVariable("orderId") String orderId) {
        try {
            log.debug("Deleting an order");
            var order = orderGuestCourierFacade.deleteOrder(orderId);
            log.debug("Order with ID: " + orderId + " deleted successfully");
            return order;
        } catch (DocumentNotFoundException e) {
            log.warn("Deleting an order were unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Sets the next state of an order by its ID.
     *
     * @param orderId The ID of the order.
     * @return The updated OrderDto object representing the order with the next state.
     * @throws ResponseStatusException If the {@link OrderDocument} is not found with 404 status code
     *                                 or if there is a server error with 500 status code.
     */
    @Operation(summary = "Sets an order state to the next state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "An order's state is set",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "No more state to set",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "{orderId}/next-state")
    public OrderDto setNextState(@PathVariable("orderId") String orderId) {
        try {
            log.debug("Setting order with ID: " + orderId + " to the next status");
            var order = orderService.setNextState(orderId);
            log.debug("Setting order with ID: " + orderId + " was successful");
            return order;
        } catch (OrderDocumentNotFoundException e) {
            log.warn("Setting an order's next stage was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (UnsupportedOperationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "{orderId}")
    public OrderDto updateOrder(
            @PathVariable("orderId") String orderId,
            @Valid @RequestBody OrderUpdateDto orderUpdateDto) {
        try {
            log.debug("Updating order with ID: " + orderId);
            var order = orderService.updateOrder(orderId, orderUpdateDto);
            log.debug("Updating order with ID: " + orderId + " was successful");
            return order;
        } catch (OrderDocumentNotFoundException e) {
            log.warn("Updating a order was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
    @PostMapping("set/{orderId}/{courierId}")
    public OrderDto setCourierToOrder(@PathVariable(value = "orderId") String orderId,
                                      @PathVariable(value = "courierId") String courierId){
        try {
            return orderCourierFacade.setCourierToOrder(orderId,courierId);
        } catch (CourierDocumentNotFoundException | OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }
}
