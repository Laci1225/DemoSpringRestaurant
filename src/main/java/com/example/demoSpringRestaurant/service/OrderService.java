package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.OrderEntityNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getOrdersByRestaurantId(Long restaurantId) throws RestaurantEntityNotFoundException {
        log.trace("All orders with Restaurant ID: " + restaurantId + " listed.");
        if (orderRepository.isRestaurantExist(restaurantId).equals(0))
            throw new RestaurantEntityNotFoundException("Restaurant not found");
        return findAllByRestaurantId(restaurantId).stream()
                .map(orderMapper::fromEntityToOrderDto)
                .toList();
    }

    public OrderDto deleteOrder(Long orderId) throws OrderEntityNotFoundException {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderEntityNotFoundException("Order not found"));
        orderRepository.deleteById(orderId);
        log.trace("Order deleted with ID: " + orderId);
        return orderMapper.fromEntityToOrderDto(order);
    }

    public OrderDto setNextState(Long orderId) throws OrderEntityNotFoundException {

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderEntityNotFoundException("Order not found"));
        var logStatus = order.getOrderStatus();
        order.setOrderStatus(order.getOrderStatus().getNextStatus());
        orderRepository.save(order);
        log.trace("Order's status with ID: " + orderId + " changed from " +
                logStatus + "to" + order.getOrderStatus());
        return orderMapper.fromEntityToOrderDto(order);
    }


    public List<OrderEntity> findAllByRestaurantId(Long restaurantId) {
        return orderRepository.findAll()
                .stream()
                .filter(orderId -> orderId.getRestaurant().getId().equals(restaurantId))
                .toList();
    }

    public void saveOrder(OrderEntity orderEntity) {
        orderRepository.save(orderEntity);
    }

    public void deleteAllOrder(List<OrderEntity> orders) {
        orderRepository.deleteAll(orders);
    }
}
