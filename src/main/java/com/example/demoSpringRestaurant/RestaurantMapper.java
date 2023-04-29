package com.example.demoSpringRestaurant;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    Restaurant fromRestaurantDtoToEntity(RestaurantDto restaurantDto);
    RestaurantDto fromEntityToRestaurantDto(Restaurant restaurant);

    RestaurantCretionDto fromEntityToRestaurantCreationDto(Restaurant restaurant);

    Restaurant fromRestaurantCreationDtoToEntity(RestaurantCretionDto restaurantCretionDto);

}
