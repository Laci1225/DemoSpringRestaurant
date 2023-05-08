package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.service.OrderService;
import com.example.demoSpringRestaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "restaurants")
//@ComponentScan("com.example.demoSpringRestaurant")
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
        restaurantService.updateRestaurant(id,restaurantUpdateDto);
    }

    @GetMapping(path ="{restaurantId}")
    public List<OrderEntity> getOrdersByRestaurantId(@PathVariable("restaurantId") Long id) {
        return orderService.getOrdersByRestaurantId(id);
    }

    @GetMapping(path ="{restaurantId}/orders")
    public List<OrderEntity> getOrders(@PathVariable("restaurantId") Long id) {
        return orderService.getOrders(id);
    }

    @PostMapping(path ="{restaurantId}/orders")
    public OrderEntity addOrder(@PathVariable("restaurantId") Long id,@RequestBody OrderEntity orderEntity) {
        return orderService.addOrder(id,orderEntity);
    }

}
