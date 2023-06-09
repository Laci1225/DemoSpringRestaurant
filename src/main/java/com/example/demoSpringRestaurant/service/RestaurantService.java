package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RestaurantService {

    public final RestaurantRepository restaurantRepository;
    public final RestaurantMapper restaurantMapper;

    public List<RestaurantDto> getRestaurants() {
        log.trace("All restaurants listed");
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::fromEntityToRestaurantDto).toList();
    }

    public RestaurantDto createRestaurant(RestaurantCreationDto restaurantCreationDto) {
        var restaurantEntity = restaurantRepository.save(restaurantMapper.
                fromRestaurantCreationDtoToEntity(restaurantCreationDto));
        log.trace("Restaurant created");
        return restaurantMapper.fromEntityToRestaurantDto(restaurantEntity);
    }

    //@Transactional
    public RestaurantDto updateRestaurant(Long id, RestaurantUpdateDto restaurantUpdateDto) throws RestaurantEntityNotFoundException {
        //var restaurant = repository.findById(id).stream().map(restaurantMapper::fromEntityToRestaurantDto).findFirst();
        if (restaurantRepository.existsById(id)) {
            var updatedEntity = restaurantMapper.fromRestaurantUpdateDtoToEntity(restaurantUpdateDto);
            updatedEntity.setId(id);
            //repository.save(restaurantMapper.fromRestaurantDtoToEntity(restaurant.get()));
            restaurantRepository.save(updatedEntity);
            log.trace("Restaurant with ID: " + id + " updated");
            return restaurantMapper.fromEntityToRestaurantDto(updatedEntity);

        } else throw new RestaurantEntityNotFoundException("Entity doesn't exist");
    }

    public RestaurantDto updateParametersInRestaurant(Long id, RestaurantUpdateDto restaurantUpdateDto) throws RestaurantEntityNotFoundException {
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
        log.trace("Restaurant's parameter with ID: " + id + " updated");
        return restaurant;
    }

    public List<RestaurantDto> getVeganRestaurant() {
        log.trace("Vegan restaurants listed");
        return restaurantRepository.findAllByIsVeganTrue()
                .stream().map(restaurantMapper::fromEntityToRestaurantDto).toList();
    }

    public Optional<RestaurantEntity> findRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    public void deleteRestaurant(RestaurantEntity restaurantEntity) {
        restaurantRepository.delete(restaurantEntity);

    }
}
