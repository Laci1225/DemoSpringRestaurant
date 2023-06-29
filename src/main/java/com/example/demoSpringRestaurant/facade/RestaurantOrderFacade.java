package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
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

    public List<OrderDto> getOrdersByRestaurantId(String restaurantId) throws RestaurantDocumentNotFoundException {
        log.trace("Fetching orders for Restaurant ID: " + restaurantId);

        restaurantService.restaurantExist(restaurantId);

        log.trace("All orders with Restaurant ID: " + restaurantId + " listed.");
        return orderService.findAllByRestaurantId(restaurantId).stream()
                .map(orderMapper::fromDocumentToOrderDto)
                .toList();
    }



    public RestaurantDto deleteRestaurant(String restaurantId) throws RestaurantDocumentNotFoundException {
        log.trace("Deleting restaurant with ID: " + restaurantId);

        var orders = orderService.findAllByRestaurantId(restaurantId);
        orderService.deleteAllOrder(orders);
        var restaurantEntity = restaurantService.findRestaurantById(restaurantId);
        restaurantService.deleteRestaurant(restaurantEntity);

        log.trace("Restaurant with ID: " + restaurantId + " deleted");
        return restaurantMapper.fromDocumentToRestaurantDto(restaurantEntity);
    }
}
