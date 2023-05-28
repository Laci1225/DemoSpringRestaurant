package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.service.OrderService;
import com.example.demoSpringRestaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantOrderFacade {
    private final OrderMapper orderMapper;
    private final RestaurantMapper restaurantMapper;

    private final OrderService orderService;

    private final RestaurantService restaurantService;

    public List<OrderDto> getOrdersByRestaurantId(Long restaurantId) throws RestaurantEntityNotFoundException {
        restaurantService.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RestaurantEntityNotFoundException("Restaurant not found"));

        return orderService.findAllRestaurantById(restaurantId)
                .stream().map(orderMapper::fromEntityToOrderDto).toList();
    }

    public OrderDto addOrder(OrderCreationDto orderCreationDto, Long restaurantId) throws RestaurantEntityNotFoundException {
        var restaurantEntity = restaurantService.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RestaurantEntityNotFoundException("Restaurant not found"));
        orderCreationDto.setRestaurant(restaurantEntity);
        var orderEntity = orderMapper.fromOrderCreationDtoToEntity(orderCreationDto);
        orderService.saveOrder(orderEntity);
        return orderMapper.fromEntityToOrderDto(orderEntity);
    }

    public RestaurantDto removeRestaurant(Long restaurantId) throws RestaurantEntityNotFoundException {
        var orders = orderService.findAllRestaurantById(restaurantId);
        orderService.deleteAllOrder(orders);
        var restaurantEntity = restaurantService.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RestaurantEntityNotFoundException("Restaurant not found"));
        restaurantService.removeRestaurant(restaurantEntity);

        return restaurantMapper.fromEntityToRestaurantDto(restaurantEntity);
    }
}
