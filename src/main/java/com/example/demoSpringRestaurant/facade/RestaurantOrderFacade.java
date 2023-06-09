package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.service.OrderService;
import com.example.demoSpringRestaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantOrderFacade {
    private final OrderMapper orderMapper;
    private final RestaurantMapper restaurantMapper;
    private final OrderService orderService;
    private final RestaurantService restaurantService;


    public OrderDto createOrder(OrderCreationDto orderCreationDto, Long restaurantId) throws RestaurantEntityNotFoundException {
        var restaurantEntity = restaurantService.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RestaurantEntityNotFoundException("Restaurant not found"));
        orderCreationDto.setRestaurant(restaurantEntity);
        orderCreationDto.setCreateDate(LocalDateTime.now());
        var orderEntity = orderMapper.fromOrderCreationDtoToEntity(orderCreationDto);
        orderService.saveOrder(orderEntity);
        log.trace("Order with Restaurant ID: " + restaurantId + " created");
        return orderMapper.fromEntityToOrderDto(orderEntity);
    }

    public RestaurantDto deleteRestaurant(Long restaurantId) throws RestaurantEntityNotFoundException {
        var orders = orderService.findAllByRestaurantId(restaurantId);
        orderService.deleteAllOrder(orders);
        var restaurantEntity = restaurantService.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RestaurantEntityNotFoundException("Restaurant not found"));
        restaurantService.deleteRestaurant(restaurantEntity);
        log.trace("Restaurant with ID: " + restaurantId + " deleted");
        return restaurantMapper.fromEntityToRestaurantDto(restaurantEntity);
    }
}
