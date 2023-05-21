package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderEntityNotFoundException;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderDto removeOrder(Long orderId) throws EntityNotFoundException {
        var order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            orderRepository.deleteById(orderId);
            return orderMapper.fromEntityToOrderDto(order.get());
        } else
            throw new OrderEntityNotFoundException("Order not found");
    }

    public OrderDto setNextState(Long orderId) throws EntityNotFoundException {

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderEntityNotFoundException("Order not found"));
        order.setOrderStatus(order.getOrderStatus().getNextStatus());
        orderRepository.save(order);
        return orderMapper.fromEntityToOrderDto(order);
    }
}
