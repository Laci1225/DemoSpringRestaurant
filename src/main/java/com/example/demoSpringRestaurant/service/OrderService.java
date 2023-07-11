package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.OrderUpdateDto;
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

    public OrderDto getOrderById(String orderId) throws OrderDocumentNotFoundException {
        log.trace("Fetching an order with ID: " + orderId);
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderDocumentNotFoundException("Order not found"));
        log.trace("An order with  ID: " + orderId + " returned.");
        return orderMapper.fromDocumentToOrderDto(order);
    }


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

    public OrderDto updateOrder(String orderId, OrderUpdateDto orderUpdateDto) throws OrderDocumentNotFoundException {
        log.trace("Updating order with ID: " + orderId + "to " + orderUpdateDto);
        orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderDocumentNotFoundException("order doesn't exist"));

        var updatedDocument = orderMapper.fromOrderUpdateDtoToDocument(orderUpdateDto);
        updatedDocument.setId(orderId);
        orderRepository.save(updatedDocument);
        log.trace("order with ID: " + updatedDocument.getId() + " updated as" + updatedDocument);
        return orderMapper.fromDocumentToOrderDto(updatedDocument);
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

    public OrderDto findOrderById(String orderId) throws OrderDocumentNotFoundException {

        var a = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderDocumentNotFoundException("Order not found"));
        return orderMapper.fromDocumentToOrderDto(a);
    }

    public void deleteById(String id) throws OrderDocumentNotFoundException {
        if (orderRepository.existsById(id))
            orderRepository.deleteById(id);
        else throw new OrderDocumentNotFoundException("Order not found");
    }

    // TODO REST & HTTP other communications
}
