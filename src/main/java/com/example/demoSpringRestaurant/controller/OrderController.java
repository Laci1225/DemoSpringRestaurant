package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {
    OrderService orderService;
    RestaurantOrderFacade restaurantOrderFacade;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "orders/{restaurantId}")
    public List<OrderDto> getOrdersByRestaurantId(@PathVariable("restaurantId") Long id) {
        try {
            return restaurantOrderFacade.getOrdersByRestaurantId(id);
        } catch (RestaurantEntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "orders/{restaurantId}")
    public OrderDto addOrder(@Valid @RequestBody OrderCreationDto orderCreationDto, @PathVariable("restaurantId") Long restaurantId) {
        try {
            return restaurantOrderFacade.addOrder(orderCreationDto, restaurantId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e){ //TODO swaggert nézegetni meg
            // TODO mongot
            log.error("Server error"); //TODO mindenhova
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "orders/{orderId}")
    public OrderDto removeOrder(@PathVariable("orderId") Long orderId) {
        try {
            return orderService.removeOrder(orderId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(path = "orders/{orderId}/next-state")
    public OrderDto setNextState(@PathVariable("orderId") Long orderId) {
        try {
            return orderService.setNextState(orderId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
