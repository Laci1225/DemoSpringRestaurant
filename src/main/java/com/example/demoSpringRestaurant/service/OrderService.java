package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
    }


    public List<OrderEntity> getOrders(Long id) {
        var restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty())
            throw new IllegalStateException("Restaurant not found");
        if (restaurant.get().getId() == null)
            throw new IllegalStateException("RestaurantId not found");
        return orderRepository.findAllById(restaurant.get().getId());
    }

    public OrderEntity addOrder(Long id, OrderEntity orderEntity) {
        orderEntity.setRestaurantId(id);

        if (orderEntity.getRestaurantId() == null)
            throw new IllegalStateException("RestaurantId not found");
        if (!restaurantRepository.existsById(orderEntity.getRestaurantId()))
            throw new IllegalStateException("Restaurant not found");

        return orderRepository.save(orderEntity);
    }

    public List<OrderEntity> getOrdersByRestaurantId(Long id) {
        if (!restaurantRepository.existsById(id))
            throw new IllegalStateException("Restaurant not found");
        return orderRepository.getOrdersByRestaurantId(id);
    }
}
