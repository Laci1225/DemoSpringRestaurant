package com.example.demoSpringRestaurant.mapper;

import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantEntity fromRestaurantDtoToEntity(RestaurantDto restaurantDto);
    RestaurantDto fromEntityToRestaurantDto(RestaurantEntity restaurantEntity);

    RestaurantCreationDto fromEntityToRestaurantCreationDto(RestaurantEntity restaurantEntity);

    RestaurantEntity fromRestaurantCreationDtoToEntity(RestaurantCreationDto restaurantCreationDto);
    RestaurantUpdateDto fromEntityToRestaurantUpdateDto(RestaurantEntity restaurantEntity);

    RestaurantEntity fromRestaurantUpdateDtoToEntity(RestaurantUpdateDto restaurantUpdateDto);

}
