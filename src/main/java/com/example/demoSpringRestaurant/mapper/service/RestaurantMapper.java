package com.example.demoSpringRestaurant.mapper.service;

import com.example.demoSpringRestaurant.model.service.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.service.RestaurantDto;
import com.example.demoSpringRestaurant.model.service.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.RestaurantDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {OrderMapper.class})
public interface RestaurantMapper {
    RestaurantDocument fromRestaurantDtoToDocument(RestaurantDto restaurantDto);
    RestaurantDto fromDocumentToRestaurantDto(RestaurantDocument restaurantDocument);

    RestaurantCreationDto fromDocumentToRestaurantCreationDto(RestaurantDocument restaurantDocument);

    RestaurantDocument fromRestaurantCreationDtoToDocument(RestaurantCreationDto restaurantCreationDto);
    RestaurantUpdateDto fromDocumentToRestaurantUpdateDto(RestaurantDocument restaurantDocument);

    RestaurantDocument fromRestaurantUpdateDtoToDocument(RestaurantUpdateDto restaurantUpdateDto);

}
