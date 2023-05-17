package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    public final RestaurantRepository restaurantRepository;
    public final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    public List<RestaurantDto> getRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::fromEntityToRestaurantDto).toList();
    }

    public RestaurantDto addRestaurant(RestaurantCreationDto restaurantCreationDto) {
        var restaurantEntity =restaurantRepository.save(restaurantMapper.
                fromRestaurantCreationDtoToEntity(restaurantCreationDto));
        return restaurantMapper.fromEntityToRestaurantDto(restaurantEntity);
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


    //@Transactional
    public RestaurantDto updateRestaurant(Long id, RestaurantUpdateDto restaurantUpdateDto) throws EntityNotFoundException {
        //var restaurant = repository.findById(id).stream().map(restaurantMapper::fromEntityToRestaurantDto).findFirst();
        if (restaurantRepository.existsById(id)) {
            var updatedEntity = restaurantMapper.fromRestaurantUpdateDtoToEntity(restaurantUpdateDto);
            updatedEntity.setId(id);
            //repository.save(restaurantMapper.fromRestaurantDtoToEntity(restaurant.get()));
            restaurantRepository.save(updatedEntity);
            return restaurantMapper.fromEntityToRestaurantDto(updatedEntity);
        }
        else throw new EntityNotFoundException("Entity doesn't exist");
    }
    public RestaurantDto updateParametersInRestaurant(Long id, RestaurantDto restaurantDto) throws EntityNotFoundException {
        var restaurantOptional = restaurantRepository.findById(id)
                .stream().map(restaurantMapper::fromEntityToRestaurantDto).findFirst();
        if (restaurantOptional.isPresent()) {
            var restaurant = restaurantOptional.get();
            restaurant.setId(restaurant.getId());
            if (restaurantDto.getName() != null) {
                restaurant.setName(restaurantDto.getName());
            }
            if (restaurantDto.getOwner() != null) {
                restaurant.setOwner(restaurantDto.getOwner());
            }
            if (restaurantDto.getEmail() != null) {
                restaurant.setEmail(restaurantDto.getEmail());
            }
            if (restaurantDto.getAddress() != null) {
                restaurant.setAddress(restaurantDto.getAddress());
            }
            if (restaurantDto.getPhoneNumber() != null) {
                restaurant.setPhoneNumber(restaurantDto.getPhoneNumber());
            }
            if (restaurantDto.getCanDeliver() != null) {
                restaurant.setCanDeliver(restaurantDto.getCanDeliver());
            }
            if (restaurantDto.getNumberOfTables() != null) {
                restaurant.setNumberOfTables(restaurantDto.getNumberOfTables());
            }
            if (restaurantDto.getIsVegan() != null) {
                restaurant.setIsVegan(restaurantDto.getIsVegan());
            }
            restaurantRepository.save(restaurantMapper.fromRestaurantDtoToEntity(restaurant));
            return restaurant;
        }
        else throw new EntityNotFoundException("Restaurant not found");

    }
}
