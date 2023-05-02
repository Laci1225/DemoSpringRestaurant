package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import jakarta.transaction.Transactional;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@ComponentScan({"com.example.demoSpringRestaurant"})//,"com.example.demoSpringRestaurant.repository"})
@Service
public class RestaurantService {

    public final RestaurantRepository repository;
    public final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantService(RestaurantRepository repository, RestaurantMapper restaurantMapper) {
        this.repository = repository;
        this.restaurantMapper = restaurantMapper;
    }


    public List<RestaurantDto> getRestaurants() {
        return repository.findAll().stream().map(restaurantMapper::fromEntityToRestaurantDto).toList();
    }

    public RestaurantEntity addRestaurant(RestaurantCreationDto restaurantCreationDto) {
        return repository.save(restaurantMapper.fromRestaurantCreationDtoToEntity(restaurantCreationDto));
    }

    public void removeRestaurant(Long id) {
        repository.deleteById(id);
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
        if (repository.getRestaurantsByOwner().isPresent())
            return repository.getRestaurantsByOwner().get().stream()
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
        if (repository.existsById(id)) {
            var updatedEntity = restaurantMapper.fromRestaurantUpdateDtoToEntity(restaurantUpdateDto);
            updatedEntity.setId(id);
            //repository.save(restaurantMapper.fromRestaurantDtoToEntity(restaurant.get()));
            repository.save(updatedEntity);
        }
    }

}
