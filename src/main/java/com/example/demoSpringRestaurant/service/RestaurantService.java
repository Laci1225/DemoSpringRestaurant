package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.model.service.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.service.RestaurantDto;
import com.example.demoSpringRestaurant.model.service.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.RestaurantDocument;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import com.example.demoSpringRestaurant.mapper.service.RestaurantMapper;
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
                .map(restaurantMapper::fromDocumentToRestaurantDto).toList();
    }

    public RestaurantDto getRestaurant(String id) throws RestaurantDocumentNotFoundException {
        log.trace("A restaurant ");
        return restaurantRepository.findById(id).map(restaurantMapper::fromDocumentToRestaurantDto)
                .orElseThrow(() -> new RestaurantDocumentNotFoundException("Restaurant not found"));
    }

    public RestaurantDto createRestaurant(RestaurantCreationDto restaurantCreationDto) {
        log.trace("Creating restaurant " + restaurantCreationDto);
        var restaurantDocument = restaurantRepository.save(restaurantMapper.
                fromRestaurantCreationDtoToDocument(restaurantCreationDto));
        log.trace("Restaurant created with ID:" + restaurantDocument.getId());
        return restaurantMapper.fromDocumentToRestaurantDto(restaurantDocument);
    }

    //@Transactional
    public RestaurantDto updateRestaurant(String restaurantId, RestaurantUpdateDto restaurantUpdateDto) throws RestaurantDocumentNotFoundException {
        log.trace("Updating restaurant with ID: " + restaurantId + "to " + restaurantUpdateDto);
        var cDate = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantDocumentNotFoundException("Restaurant doesn't exist"));

        var updatedDocument = restaurantMapper.fromRestaurantUpdateDtoToDocument(restaurantUpdateDto);
        updatedDocument.setId(restaurantId);
        updatedDocument.setCreatedDate(cDate.getCreatedDate());
        restaurantRepository.save(updatedDocument);
        log.trace("Restaurant with ID: " + updatedDocument.getId() + " updated as" + updatedDocument);
        return restaurantMapper.fromDocumentToRestaurantDto(updatedDocument);
    }

    public List<RestaurantDto> getVeganRestaurant() {
        log.trace("Vegan restaurants listed");
        return restaurantRepository.findAllByIsVeganTrue()
                .stream().map(restaurantMapper::fromDocumentToRestaurantDto).toList();
    }

    public RestaurantDto findRestaurantById(String restaurantId) throws RestaurantDocumentNotFoundException {
        var restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new RestaurantDocumentNotFoundException("Restaurant not found"));
        return restaurantMapper.fromDocumentToRestaurantDto(restaurant);
    }

    public RestaurantDto updateParametersInRestaurant(String restaurantId, RestaurantUpdateDto restaurantUpdateDto) throws RestaurantDocumentNotFoundException {
        log.trace("Updating restaurant's parameter with ID: " + restaurantId + "to " + restaurantUpdateDto);

        var restaurant = restaurantRepository.findById(restaurantId)
                .stream().map(restaurantMapper::fromDocumentToRestaurantDto).findFirst()
                .orElseThrow(() -> new RestaurantDocumentNotFoundException("Restaurant not found"));
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
        var updatedDocument = restaurantRepository.save(restaurantMapper.fromRestaurantDtoToDocument(restaurant));
        log.trace("Restaurant's parameter with ID: " + updatedDocument.getId() + " updated as " + updatedDocument);
        return restaurantMapper.fromDocumentToRestaurantDto(updatedDocument);

    }

    public void deleteRestaurant(RestaurantDocument restaurantDocument) {
        restaurantRepository.delete(restaurantDocument);

    }

    public boolean restaurantExist(String restaurantId) throws RestaurantDocumentNotFoundException {
        if (!restaurantRepository.existsById(restaurantId))
            throw new RestaurantDocumentNotFoundException("Restaurant not found");
        return true;
    }


}
