package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantOrderFacade {
    private final OrderMapper orderMapper;
    private final RestaurantMapper restaurantMapper;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    public List<OrderDto> getOrdersByRestaurantId(Long restaurantId) {
        if (restaurantRepository.findById(restaurantId).isPresent()) {
            return orderRepository.findAllByRestaurantId(restaurantId)
                    .stream().map(orderMapper::fromEntityToOrderDto).toList();
        } else
            throw new IllegalStateException();
    }

    public OrderDto addOrder(OrderCreationDto orderCreationDto, Long restaurantId) throws RestaurantEntityNotFoundException {
        // TODO fix service and everything in this class
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantEntityNotFoundException("Restaurant not found"));
        orderCreationDto.setRestaurant(restaurant);
        var orderEntity = orderMapper.fromOrderCreationDtoToEntity(orderCreationDto);
        orderRepository.save(orderEntity);
        return orderMapper.fromEntityToOrderDto(orderEntity);
    }

    public RestaurantDto removeRestaurant(Long restaurantId) throws RestaurantEntityNotFoundException {
        var orders = orderRepository.findAllByRestaurantId(restaurantId);
        orderRepository.deleteAll(orders);
        var restaurantEntity = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantEntityNotFoundException("Restaurant not found"));
        restaurantRepository.delete(restaurantEntity);
        return restaurantMapper.fromEntityToRestaurantDto(restaurantEntity);
    }
}
