package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.service.CourierService;
import com.example.demoSpringRestaurant.service.GuestService;
import com.example.demoSpringRestaurant.service.OrderService;
import com.example.demoSpringRestaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderGuestCourierFacade {
    private final OrderMapper orderMapper;
    private final CourierService courierService;
    private final GuestService guestService;
    private final OrderService orderService;
    private final RestaurantService restaurantService;

    public OrderDto deleteOrder(String orderId) throws DocumentNotFoundException {
        log.trace("Deleting order with ID: " + orderId);

        var order = orderService.findById(orderId)
                .orElseThrow(() -> new OrderDocumentNotFoundException("Order not found"));

        var guest = guestService.findGuestDocumentByActiveOrder_Id(order.getId())
                .orElseThrow(() -> new DocumentNotFoundException("Guest not found"));

        var courier = courierService.findCourierDocumentByActiveOrder_Id(order.getId())
                .orElseThrow(() -> new DocumentNotFoundException("Courier not found"));

        guestService.deleteByOrderId(guest.getId());
        courierService.deleteByOrderId(courier.getId());
        orderService.deleteById(order.getId());
        log.trace("Order deleted with ID: " + orderId);
        return orderMapper.fromDocumentToOrderDto(order);
    }
}
