package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.service.CourierMapper;
import com.example.demoSpringRestaurant.mapper.service.OrderMapper;
import com.example.demoSpringRestaurant.model.service.CourierDto;
import com.example.demoSpringRestaurant.model.service.OrderDto;
import com.example.demoSpringRestaurant.service.CourierService;
import com.example.demoSpringRestaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCourierFacade {
    private final CourierService courierService;
    private final CourierMapper courierMapper;
    private final OrderMapper orderMapper;
    private final OrderService orderService;

    public CourierDto deleteCourier(String courierId) throws DocumentNotFoundException {
        var courier = courierService.findCourierById(courierId);
        var order = orderService.findOrderById(courier.getActiveOrder().getId());
        order.setCourier(null);
        var orders = courier.getOrders();
        orders.forEach(orderDto -> orderDto.setCourier(null));
        courierService.deleteById(courier.getId());
        return courier;
    }

    public CourierDto addOrderToCourier(String courierId, String orderId) throws CourierDocumentNotFoundException, OrderDocumentNotFoundException {
        var courier = courierService.findCourierById(courierId);
        var order = orderService.findOrderById(orderId);
        var orders = courier.getOrders();
        orders.add(order);
        courier.setOrders(orders);
        return courier;
    }

    public CourierDto setOrderActive(String courierId, String orderId) throws CourierDocumentNotFoundException, OrderDocumentNotFoundException {
        var courier = courierService.findCourierById(courierId);
        var order = orderService.findOrderById(orderId);
        courier.setActiveOrder(order);
        return courier;
    }

    public OrderDto setCourierToOrder(String orderId, String courierId) throws CourierDocumentNotFoundException, OrderDocumentNotFoundException {
        var courier = courierService.findCourierById(courierId);
        var order = orderService.findOrderById(orderId);
        order.setCourier(courier);
        return order;
    }
}
