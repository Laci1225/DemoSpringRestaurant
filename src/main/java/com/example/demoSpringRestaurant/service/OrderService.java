package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<RestaurantEntity, List<OrderDto>> getOrdersByRestaurantId(Long id) {
        if (restaurantRepository.findById(id).isPresent()) {
            Map<RestaurantEntity, List<OrderDto>> restaurantEntityOrderListMap = new HashMap<>();
            var restaurant = restaurantRepository.findById(id).get();
            var orders = orderRepository.getOrdersByRestaurantId(id)
                    .stream().map(orderMapper::fromEntityToOrderDto).toList();
            restaurantEntityOrderListMap.put(restaurant, orders);
            return restaurantEntityOrderListMap;
        } else
            throw new IllegalStateException();
        /*if (!restaurantRepository.existsById(id))
            throw new IllegalStateException("Restaurant not found");
        return orderRepository.getOrdersByRestaurantId(id)
                .stream().map(orderMapper::fromEntityToOrderDto).toList();*/
    }

    public void removeOrder(Long restaurantId, Long orderId) {
        if (!restaurantRepository.existsById(restaurantId))
            throw new IllegalStateException("Restaurant not found");
        orderRepository.deleteById(orderId);
    }

    /*public void setNextState(Long restaurantId, Long orderId) {
        if (!restaurantRepository.existsById(restaurantId))
            throw new IllegalStateException("Restaurant not found");
        var order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            if (order.get().getRestaurantId().equals(restaurantId))
                order.get().setOrderStatus(order.get().getOrderStatus().getNextStatus());
        }
    }*/
}
