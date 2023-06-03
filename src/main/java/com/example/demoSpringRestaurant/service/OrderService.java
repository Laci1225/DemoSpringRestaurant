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


    public void saveOrder(OrderEntity orderEntity) {
        orderRepository.save(orderEntity);
    }

    public List<OrderEntity> findAllRestaurantById(Long restaurantId) {
        return orderRepository.findAllByRestaurantId(restaurantId);
    }

    public void deleteAllOrder(List<OrderEntity> orders) {
        orderRepository.deleteAll(orders);
    }
}
