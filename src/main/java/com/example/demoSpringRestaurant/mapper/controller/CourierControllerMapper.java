package com.example.demoSpringRestaurant.mapper.controller;

import com.example.demoSpringRestaurant.model.controller.Courier;
import com.example.demoSpringRestaurant.model.service.CourierDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderControllerWithoutReferencesMapper.class})
public interface CourierControllerMapper {

    Courier fromCourierDtoToCourier(CourierDto courierDto);

    CourierDto fromCourierToCourierDto(Courier courierDocument);

    /*CourierCreationDto fromDocumentToCourierCreationDto(Courier courierDocument);

    Courier fromCourierCreationDtoToDocument(CourierCreationDto courierCreationDto);

    CourierUpdateDto fromDocumentToCourierUpdateDto(Courier courierDocument);

    Courier fromCourierUpdateDtoToDocument(CourierUpdateDto courierUpdateDto);*/
}
