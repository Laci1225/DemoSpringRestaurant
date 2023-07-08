package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.CourierMapper;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.CourierDto;
import com.example.demoSpringRestaurant.model.OrderDto;
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
    private final OrderService orderServices;

    public CourierDto deleteCourier(String courierId) throws DocumentNotFoundException {
        var courier = courierService.findById(courierId)
                .orElseThrow(() -> new CourierDocumentNotFoundException("Courier not found"));
        var orders = courier.getOrders();
        orderServices.deleteAllOrder(orders);
        courierService.deleteById(courier.getId());
        return courierMapper.fromDocumentToCourierDto(courier);
    }

    public CourierDto addOrderToCourier(String courierId, String orderId) throws CourierDocumentNotFoundException, OrderDocumentNotFoundException {
        var courier = courierService.findById(courierId)
                .orElseThrow(() -> new CourierDocumentNotFoundException("Courier not found"));
        var order = orderServices.findById(orderId)
                .orElseThrow(() -> new OrderDocumentNotFoundException("Order not found"));
        var orders = courier.getOrders();
        orders.add(order);
        courier.setOrders(orders);
        return courierMapper.fromDocumentToCourierDto(courier);
    }

    public CourierDto setOrderActive(String courierId, String orderId) throws CourierDocumentNotFoundException, OrderDocumentNotFoundException {
        var courier = courierService.findById(courierId)
                .orElseThrow(() -> new CourierDocumentNotFoundException("Courier not found"));
        var order = orderServices.findById(orderId)
                .orElseThrow(() -> new OrderDocumentNotFoundException("Order not found"));
        courier.setActiveOrder(order);
        return courierMapper.fromDocumentToCourierDto(courier);
    }

    public OrderDto setCourierToOrder(String orderId, String courierId) throws CourierDocumentNotFoundException, OrderDocumentNotFoundException {
        var courier = courierService.findById(courierId)
                .orElseThrow(() -> new CourierDocumentNotFoundException("Courier not found"));
        var order = orderServices.findById(orderId)
                .orElseThrow(() -> new OrderDocumentNotFoundException("Order not found"));
        order.setCourierDocument(courier);
        return orderMapper.fromDocumentToOrderDto(order);
    }
}
