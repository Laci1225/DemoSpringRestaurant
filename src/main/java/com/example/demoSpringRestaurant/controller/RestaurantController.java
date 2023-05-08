package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.service.OrderService;
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
    private final OrderService orderService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, OrderService orderService) {
        this.restaurantService = restaurantService;
        this.orderService = orderService;
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
    public void removeRestaurant(@PathVariable("restaurantId") Long id) {
        restaurantService.removeRestaurant(id);
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




    @GetMapping(path = "{restaurantId}/orders")
    public Map<RestaurantEntity, List<OrderDto>> getOrdersByRestaurantId(@PathVariable("restaurantId") Long id) {
        return orderService.getOrdersByRestaurantId(id);
    }
    @PostMapping(path = "{restaurantId}/orders")
    public OrderEntity addOrder(@PathVariable("restaurantId") Long id,
                                     @RequestBody OrderCreationDto orderCreationDto) {
        return orderService.addOrder(id, orderCreationDto);
    }
    @DeleteMapping(path = "{restaurantId}/orders/{orderId}")
    public void removeOrder(@PathVariable("restaurantId") Long restaurantId,
                            @PathVariable("orderId") Long orderId) {
        orderService.removeOrder(restaurantId,orderId);
    }

    /*@GetMapping(path = "{restaurantId}/orders/{orderId}")
    public void setNextState(@PathVariable("restaurantId") Long restaurantId,
                             @PathVariable("orderId") Long orderId){
        orderService.setNextState(restaurantId,orderId);
    }*/


}
