package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }


    public OrderDto removeOrder(Long orderId) throws EntityNotFoundException {
        var order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            orderRepository.deleteById(orderId);
            return orderMapper.fromEntityToOrderDto(order.get());
        } else
            throw new EntityNotFoundException("Order not found");
    }

    public OrderDto setNextState(Long orderId) throws EntityNotFoundException {

        var orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            var order = orderOptional.get();
            order.setOrderStatus(order.getOrderStatus().getNextStatus());
            orderRepository.save(order);
            return orderMapper.fromEntityToOrderDto(order);
        } else
            throw new EntityNotFoundException("Order not found");

    }
}
