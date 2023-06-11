package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.OrderEntityNotFoundException;
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


    public OrderDto deleteOrder(Long orderId) throws OrderEntityNotFoundException {
        log.trace("Deleting order with ID: " + orderId);

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderEntityNotFoundException("Order not found"));
        orderRepository.deleteById(orderId);
        log.trace("Order deleted with ID: " + orderId);
        return orderMapper.fromEntityToOrderDto(order);
    }

    public OrderDto setNextState(Long orderId) throws OrderEntityNotFoundException {
        log.trace("Changing order status to order with ID: " + orderId);

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderEntityNotFoundException("Order not found"));
        var logStatus = order.getOrderStatus();
        order.setOrderStatus(order.getOrderStatus().getNextStatus());
        orderRepository.save(order);
        log.trace("Order status changed for ID: " + orderId + ". Previous status: " + logStatus + ". " +
                "New status: " + order.getOrderStatus());
        return orderMapper.fromEntityToOrderDto(order);
    }

    public OrderDto saveOrder(OrderEntity orderEntity) {
        return orderMapper.fromEntityToOrderDto(orderRepository.save(orderEntity));
    }

    public void deleteAllOrder(List<OrderEntity> orders) {
        orderRepository.deleteAll(orders);
    }

    public List<OrderEntity> findAllByRestaurantId(Long restaurantId) {
        return orderRepository.findAllByRestaurantId(restaurantId);
    }
}
