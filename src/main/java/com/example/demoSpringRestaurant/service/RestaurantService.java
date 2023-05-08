package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import jakarta.transaction.Transactional;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    public final RestaurantRepository restaurantRepository;
    public final OrderRepository orderRepository;
    public final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, OrderRepository orderRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
        this.restaurantMapper = restaurantMapper;
    }


    public List<RestaurantDto> getRestaurants() {
        return restaurantRepository.findAll().stream().map(restaurantMapper::fromEntityToRestaurantDto).toList();
    }

    public RestaurantEntity addRestaurant(RestaurantCreationDto restaurantCreationDto) {
        return restaurantRepository.save(restaurantMapper.fromRestaurantCreationDtoToEntity(restaurantCreationDto));
    }

    public void removeRestaurant(Long id) {
        var orders= restaurantRepository.getOrdersByRestaurantId(id);
        orderRepository.deleteAll(orders);
        restaurantRepository.deleteById(id);
    }

    public Map<String, List<String>> getRestaurantsByOwner() {
        /*var a = com.example.demoSpringRestaurant.repository.getRestaurantsByOwner();
        List<Map<String, String>> restaurants = new ArrayList<>();
        for (var s : a) {
            Map<String, String> temp = new HashMap<>();
            temp.put("owner", s.split(",")[0]);
            temp.put("name", s.split(",")[1]);
            restaurants.add(temp);
        }
        var restaurantsByOwner = restaurants.stream().collect(Collectors
                .groupingBy(r -> r.get("owner"),
                        Collectors.mapping(com.example.demoSpringRestaurant.restaurant -> com.example.demoSpringRestaurant.restaurant.get("name"), Collectors.toList())));
        restaurantsByOwner.forEach((owner, restaurantList) ->
                System.out.println(owner + " owns: " + String.join(", ", restaurantList)));
        return restaurantsByOwner;*/
        if (restaurantRepository.getRestaurantsByOwner().isPresent())
            return restaurantRepository.getRestaurantsByOwner().get().stream()
                    .map(s -> s.split(","))
                    .collect(Collectors.groupingBy(
                            arr -> arr[0],
                            Collectors.mapping(arr -> arr[1], Collectors.toList())));
        else
            throw new IllegalStateException();
    }


    @Transactional
    public void updateRestaurant(Long id, RestaurantUpdateDto restaurantUpdateDto) {
        //var restaurant = repository.findById(id).stream().map(restaurantMapper::fromEntityToRestaurantDto).findFirst();
        if (restaurantRepository.existsById(id)) {
            var updatedEntity = restaurantMapper.fromRestaurantUpdateDtoToEntity(restaurantUpdateDto);
            updatedEntity.setId(id);
            //repository.save(restaurantMapper.fromRestaurantDtoToEntity(restaurant.get()));
            restaurantRepository.save(updatedEntity);
        }
    }

    public List<OrderEntity> getOrdersByRestaurantId(Long id) {
        if (!restaurantRepository.existsById(id))
            throw new IllegalStateException("Restaurant not found");
       return restaurantRepository.getOrdersByRestaurantId(id);
    }
}
