package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public List<OrderEntity> getOrders() {
       return orderRepository.findAll();
    }
    public OrderEntity addOrder(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }
}
