package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {
    OrderService orderService;
    RestaurantOrderFacade restaurantOrderFacade;

    @GetMapping(path = "orders/{restaurantId}")
    public List<OrderDto> getOrdersByRestaurantId(@PathVariable("restaurantId") Long id) {
        return restaurantOrderFacade.getOrdersByRestaurantId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "orders/{restaurantId}")
    public OrderDto addOrder(@RequestBody OrderCreationDto orderCreationDto, @PathVariable("restaurantId") Long restaurantId) {
        try {
            return restaurantOrderFacade.addOrder(orderCreationDto, restaurantId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }

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
