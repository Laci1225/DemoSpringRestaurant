package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    public OrderDto setNextState(String orderId) throws OrderDocumentNotFoundException {
        log.trace("Changing order status to order with ID: " + orderId);

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderDocumentNotFoundException("Order not found"));
        var logStatus = order.getOrderStatus();
        order.setOrderStatus(order.getOrderStatus().getNextStatus());
        orderRepository.save(order);
        log.trace("Order status changed for ID: " + orderId + ". Previous status: " + logStatus + ". " +
                "New status: " + order.getOrderStatus());
        return orderMapper.fromDocumentToOrderDto(order);
    }

    public OrderDto saveOrder(OrderDocument orderDocument) {
        return orderMapper.fromDocumentToOrderDto(orderRepository.save(orderDocument));
    }

    public void deleteAllOrder(List<OrderDocument> orders) {
        orderRepository.deleteAll(orders);
    }

    public List<OrderDocument> findAllByRestaurantId(String restaurantId) {
        return orderRepository.findAllByRestaurantId(restaurantId);
    }

    public Optional<OrderDocument> findById(String orderId) {
        return orderRepository.findById(orderId);
    }

    public void deleteById(String orderId) {
        orderRepository.deleteById(orderId);
    }

    // TODO unit test a guest courier
    // TODO audit createdBy (createdDate) modifiedBy (modifiedDate)
    // TODO java springboot config mongo configja ??
    // TODO REST & HTTP other communications
}
