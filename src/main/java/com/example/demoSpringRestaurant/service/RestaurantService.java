package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    public final RestaurantRepository restaurantRepository;
    public final RestaurantMapper restaurantMapper;

    public List<RestaurantDto> getRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::fromEntityToRestaurantDto).toList();
    }

    public RestaurantDto addRestaurant(RestaurantCreationDto restaurantCreationDto) {
        var restaurantEntity = restaurantRepository.save(restaurantMapper.
                fromRestaurantCreationDtoToEntity(restaurantCreationDto));
        return restaurantMapper.fromEntityToRestaurantDto(restaurantEntity);
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
        } else throw new RestaurantEntityNotFoundException("Entity doesn't exist");
    }

    public RestaurantDto updateParametersInRestaurant(Long id, RestaurantUpdateDto restaurantUpdateDto) throws EntityNotFoundException {
        var restaurant = restaurantRepository.findById(id)
                .stream().map(restaurantMapper::fromEntityToRestaurantDto).findFirst()
                .orElseThrow(() -> new RestaurantEntityNotFoundException("Restaurant not found"));
        restaurant.setId(restaurant.getId());
        if (restaurantUpdateDto.getName() != null) {
            restaurant.setName(restaurantUpdateDto.getName());
        }
        if (restaurantUpdateDto.getOwner() != null) {
            restaurant.setOwner(restaurantUpdateDto.getOwner());
        }
        if (restaurantUpdateDto.getEmail() != null) {
            restaurant.setEmail(restaurantUpdateDto.getEmail());
        }
        if (restaurantUpdateDto.getAddress() != null) {
            restaurant.setAddress(restaurantUpdateDto.getAddress());
        }
        if (restaurantUpdateDto.getPhoneNumber() != null) {
            restaurant.setPhoneNumber(restaurantUpdateDto.getPhoneNumber());
        }
        if (restaurantUpdateDto.getCanDeliver() != null) {
            restaurant.setCanDeliver(restaurantUpdateDto.getCanDeliver());
        }
        if (restaurantUpdateDto.getNumberOfTables() != null) {
            restaurant.setNumberOfTables(restaurantUpdateDto.getNumberOfTables());
        }
        if (restaurantUpdateDto.getIsVegan() != null) {
            restaurant.setIsVegan(restaurantUpdateDto.getIsVegan());
        }
        restaurantRepository.save(restaurantMapper.fromRestaurantDtoToEntity(restaurant));
        return restaurant;
    }

    public List<RestaurantDto> getVeganRestaurant() {
        return restaurantRepository.findAllByIsVeganTrue()
                .stream().map(restaurantMapper::fromEntityToRestaurantDto).toList();
    }
}
