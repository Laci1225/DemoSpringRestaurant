package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import com.example.demoSpringRestaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantOrderFacade {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestaurantRepository restaurantRepository;

    public List<OrderDto> getOrdersByRestaurantId(Long restaurantId) {
        if (restaurantRepository.findById(restaurantId).isPresent()) {
            return orderRepository.findAllByRestaurantId(restaurantId)
                    .stream().map(orderMapper::fromEntityToOrderDto).toList();
        } else
            throw new IllegalStateException();
    }

    public OrderCreationDto addOrder(OrderCreationDto orderCreationDto, Long restaurantId) {
        // TODO fix service and everything in this class
        return orderService.addOrder(orderCreationDto,restaurantId);
    }

    public void removeRestaurant(Long restaurantId) {
        var orders = orderRepository.findAllByRestaurantId(restaurantId);
        orderRepository.deleteAll(orders);
        restaurantRepository.deleteById(restaurantId);
    }

    //create order
    //remove restaurant
}
