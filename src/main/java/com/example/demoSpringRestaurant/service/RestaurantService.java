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
        log.trace("Creating restaurant " + restaurantCreationDto);
        var restaurantEntity = restaurantRepository.save(restaurantMapper.
                fromRestaurantCreationDtoToEntity(restaurantCreationDto));
        log.trace("Restaurant created with ID:" + restaurantEntity.getId());
        return restaurantMapper.fromEntityToRestaurantDto(restaurantEntity);
    }

    //@Transactional
    public RestaurantDto updateRestaurant(Long restaurantId, RestaurantUpdateDto restaurantUpdateDto) throws RestaurantEntityNotFoundException {
        log.trace("Updating restaurant with ID: " + restaurantId + "to " + restaurantUpdateDto);
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantEntityNotFoundException("Restaurant doesn't exist"));

        var updatedEntity = restaurantMapper.fromRestaurantUpdateDtoToEntity(restaurantUpdateDto);
        updatedEntity.setId(restaurantId);
        restaurantRepository.save(updatedEntity);
        log.trace("Restaurant with ID: " + updatedEntity.getId() + " updated as" + updatedEntity);
        return restaurantMapper.fromEntityToRestaurantDto(updatedEntity);
    }

    public RestaurantDto updateParametersInRestaurant(Long restaurantId, RestaurantUpdateDto restaurantUpdateDto) throws RestaurantEntityNotFoundException {
        log.trace("Updating restaurant's parameter with ID: " + restaurantId + "to " + restaurantUpdateDto);

        var restaurant = restaurantRepository.findById(restaurantId)
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
        var updatedEntity = restaurantRepository.save(restaurantMapper.fromRestaurantDtoToEntity(restaurant));
        log.trace("Restaurant's parameter with ID: " + updatedEntity.getId() + " updated as " + updatedEntity);
        return restaurantMapper.fromEntityToRestaurantDto(updatedEntity);

    }

    public List<RestaurantDto> getVeganRestaurant() {
        log.trace("Vegan restaurants listed");
        return restaurantRepository.findAllByIsVeganTrue()
                .stream().map(restaurantMapper::fromEntityToRestaurantDto).toList();
    }

    public RestaurantEntity findRestaurantById(Long restaurantId) throws RestaurantEntityNotFoundException {
        return restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new RestaurantEntityNotFoundException("Restaurant not found")
        );
    }

    public void deleteRestaurant(RestaurantEntity restaurantEntity) {
        restaurantRepository.delete(restaurantEntity);

    }

    public void restaurantExist(long restaurantId) throws RestaurantEntityNotFoundException {
        if (!restaurantRepository.existsById(restaurantId))
            throw new RestaurantEntityNotFoundException("Restaurant not found");
    }
}
