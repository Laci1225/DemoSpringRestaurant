package com.example.demoSpringRestaurant.controller;

import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderEntity> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping
    public OrderEntity addOrder(@RequestBody OrderEntity orderEntity) {
        return orderService.addOrder(orderEntity);
    }
}
