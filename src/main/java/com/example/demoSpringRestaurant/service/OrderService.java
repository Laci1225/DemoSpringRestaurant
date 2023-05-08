package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
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
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderMapper = orderMapper;
    }

    public OrderEntity addOrder(Long id, OrderCreationDto orderCreationDto) {
        orderCreationDto.setRestaurantId(id);

        if (orderCreationDto.getRestaurantId() == null)
            throw new IllegalStateException("RestaurantId not found");
        if (!restaurantRepository.existsById(orderCreationDto.getRestaurantId()))
            throw new IllegalStateException("Restaurant not found");

        return orderRepository.save(orderMapper.fromOrderCreationDtoToEntity(orderCreationDto));
    }

    public List<OrderDto> getOrdersByRestaurantId(Long id) {
        if (!restaurantRepository.existsById(id))
            throw new IllegalStateException("Restaurant not found");
        return orderRepository.getOrdersByRestaurantId(id)
                .stream().map(orderMapper::fromEntityToOrderDto).toList();
    }

    public void removeOrder(Long restaurantId, Long orderId) {
        if (!restaurantRepository.existsById(restaurantId))
            throw new IllegalStateException("Restaurant not found");
        orderRepository.deleteById(orderId);
    }
}
