package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.OrderEntityNotFoundException;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
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


    public OrderDto deleteOrder(String orderId) throws OrderEntityNotFoundException {
        log.trace("Deleting order with ID: " + orderId);

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderEntityNotFoundException("Order not found"));
        orderRepository.deleteById(orderId);
        log.trace("Order deleted with ID: " + orderId);
        return orderMapper.fromDocumentToOrderDto(order);
    }

    public OrderDto setNextState(String orderId) throws OrderEntityNotFoundException {
        log.trace("Changing order status to order with ID: " + orderId);

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderEntityNotFoundException("Order not found"));
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

    //TODO nem kell integration és workflowban sem kiegészíteni guest modellel
    // TODO unit teszt a guest emberrre
    // TODO audit mező 4 mező /creadedby/ createdat /modifyedby/ modifyedat orderre restaurantra questre
    // TODO java springboot config mongo configja
    // TODO resten és httpn kívül mivel lehet kommunikálni
    //TODO esetleg druver mint futár
}
