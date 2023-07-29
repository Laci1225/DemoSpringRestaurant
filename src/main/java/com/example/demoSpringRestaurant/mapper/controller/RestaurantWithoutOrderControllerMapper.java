package com.example.demoSpringRestaurant.mapper.controller;

import com.example.demoSpringRestaurant.model.controller.Restaurant;
import com.example.demoSpringRestaurant.model.service.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.service.RestaurantDto;
import com.example.demoSpringRestaurant.model.service.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.RestaurantDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantWithoutOrderControllerMapper {
    @Mapping(target = "orders", ignore = true)
    Restaurant fromRestaurantDtoToRestaurant(RestaurantDto restaurantDto);

    @Mapping(target = "orders", ignore = true)
    RestaurantDto fromRestaurantToRestaurantDto(Restaurant restaurant);

    /*RestaurantCreationDto fromDocumentToRestaurantCreationDto(Restaurant restaurant);

    Restaurant fromRestaurantCreationDtoToDocument(RestaurantCreationDto restaurantCreationDto);
    RestaurantUpdateDto fromDocumentToRestaurantUpdateDto(Restaurant restaurant);

    Restaurant fromRestaurantUpdateDtoToDocument(RestaurantUpdateDto restaurantUpdateDto);*/
}
