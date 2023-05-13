package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantOrderFacade {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantOrderFacade(OrderRepository orderRepository, OrderMapper orderMapper, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.restaurantRepository = restaurantRepository;
    }

    public List<OrderDto> getOrdersByRestaurantId(Long restaurantId) {
        if (restaurantRepository.findById(restaurantId).isPresent()) {
            return orderRepository.findAllByRestaurantId(restaurantId)
                    .stream().map(orderMapper::fromEntityToOrderDto).toList();
        } else
            throw new IllegalStateException();
    }

    public OrderCreationDto addOrder(OrderCreationDto orderCreationDto, Long restaurantId) {

        var restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            orderCreationDto.setRestaurant(restaurant.get());
            orderRepository.save(orderMapper.fromOrderCreationDtoToEntity(orderCreationDto));
            return orderCreationDto;
        } else
            throw new IllegalStateException("Restaurant not found");
    }

    public void removeRestaurant(Long restaurantId) {
        var orders = orderRepository.findAllByRestaurantId(restaurantId);
        orderRepository.deleteAll(orders);
        restaurantRepository.deleteById(restaurantId);
    }

    //create order
    //remove restaurant
}
