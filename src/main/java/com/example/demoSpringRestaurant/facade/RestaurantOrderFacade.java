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

    public List<OrderDto> getOrdersByRestaurantId(String restaurantId) throws RestaurantEntityNotFoundException {
        log.trace("Fetching orders for Restaurant ID: " + restaurantId);

        restaurantService.restaurantExist(restaurantId);

        log.trace("All orders with Restaurant ID: " + restaurantId + " listed.");
        return orderService.findAllByRestaurantId(restaurantId).stream()
                .map(orderMapper::fromDocumentToOrderDto)
                .toList();
    }

    public OrderDto createOrder(OrderCreationDto orderCreationDto, String restaurantId) throws RestaurantEntityNotFoundException {
        log.trace("Creating order " + orderCreationDto + "with Restaurant ID: " + restaurantId);

        var restaurantDocument = restaurantService.findRestaurantById(restaurantId);
        orderCreationDto.setRestaurant(restaurantMapper.fromDocumentToRestaurantDto(restaurantDocument));
        orderCreationDto.setCreateDate(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS));
        var estimated = orderCreationDto.getCreateDate().plusMinutes(30).toLocalTime();
        orderCreationDto.setEstimatedDeliveryTime(estimated);
        var orderEntity = orderMapper.fromOrderCreationDtoToDocument(orderCreationDto);
        var orderDto = orderService.saveOrder(orderEntity);

        log.trace("Order" + orderDto + " with Restaurant ID: " + orderDto.getId() + " created");
        return orderMapper.fromDocumentToOrderDto(orderEntity);
    }

    public RestaurantDto deleteRestaurant(String restaurantId) throws RestaurantEntityNotFoundException {
        log.trace("Deleting restaurant with ID: " + restaurantId);

        var orders = orderService.findAllByRestaurantId(restaurantId);
        orderService.deleteAllOrder(orders);
        var restaurantEntity = restaurantService.findRestaurantById(restaurantId);
        restaurantService.deleteRestaurant(restaurantEntity);

        log.trace("Restaurant with ID: " + restaurantId + " deleted");
        return restaurantMapper.fromDocumentToRestaurantDto(restaurantEntity);
    }
}
