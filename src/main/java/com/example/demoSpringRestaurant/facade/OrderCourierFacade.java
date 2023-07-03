package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.CourierMapper;
import com.example.demoSpringRestaurant.model.CourierDto;
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
    private final OrderService orderServices;

    public CourierDto deleteCourier(String courierId) throws DocumentNotFoundException {
        var courier = courierService.findById(courierId)
                .orElseThrow(() -> new DocumentNotFoundException("Courier not found"));
        var orders = courier.getOrders();
        orderServices.deleteAllOrder(orders);
        courierService.deleteById(courier.getId());
        return courierMapper.fromDocumentToCourierDto(courier);
    }
}
