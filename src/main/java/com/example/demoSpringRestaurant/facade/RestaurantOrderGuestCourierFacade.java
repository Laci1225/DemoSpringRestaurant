package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.CourierMapper;
import com.example.demoSpringRestaurant.mapper.GuestMapper;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.service.CourierService;
import com.example.demoSpringRestaurant.service.GuestService;
import com.example.demoSpringRestaurant.service.OrderService;
import com.example.demoSpringRestaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantOrderGuestCourierFacade {
    private final CourierService courierService;
    private final CourierMapper courierMapper;
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final GuestService guestService;
    private final GuestMapper guestMapper;
    private final OrderMapper orderMapper;
    private final OrderService orderService;

    public OrderDto createOrder(OrderCreationDto orderCreationDto,
                                String restaurantId) throws RestaurantDocumentNotFoundException {
        log.trace("Creating order " + orderCreationDto + "with Restaurant ID: " + restaurantId);

        var restaurantDocument = restaurantService.findRestaurantById(restaurantId);
        orderCreationDto.setRestaurant(restaurantMapper.fromDocumentToRestaurantDto(restaurantDocument));
        //orderCreationDto.setCreateDate(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS));
        //var estimated = orderCreationDto.getCreatedDate().plusMinutes(30).toLocalTime();
        //orderCreationDto.setEstimatedPreparationTime(estimated);
        var orderDocument = orderMapper.fromOrderCreationDtoToDocument(orderCreationDto);

        var orderDto = orderService.saveOrder(orderDocument);

        log.trace("Order" + orderDto + " with Restaurant ID: " + orderDto.getId() + " created");
        return orderMapper.fromDocumentToOrderDto(orderDocument);
    }
}
