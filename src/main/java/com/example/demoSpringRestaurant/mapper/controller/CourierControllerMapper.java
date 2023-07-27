package com.example.demoSpringRestaurant.mapper.controller;

import com.example.demoSpringRestaurant.model.controller.Courier;
import com.example.demoSpringRestaurant.model.service.CourierCreationDto;
import com.example.demoSpringRestaurant.model.service.CourierDto;
import com.example.demoSpringRestaurant.model.service.CourierUpdateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderControllerMapper.class})
public interface CourierControllerMapper {

    Courier fromCourierDtoToCourier(CourierDto courierDto);

    CourierDto fromCourierToCourierDto(Courier courierDocument);

    /*CourierCreationDto fromDocumentToCourierCreationDto(Courier courierDocument);

    Courier fromCourierCreationDtoToDocument(CourierCreationDto courierCreationDto);

    CourierUpdateDto fromDocumentToCourierUpdateDto(Courier courierDocument);

    Courier fromCourierUpdateDtoToDocument(CourierUpdateDto courierUpdateDto);*/
}
