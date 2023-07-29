package com.example.demoSpringRestaurant.mapper.service;

import com.example.demoSpringRestaurant.model.service.CourierCreationDto;
import com.example.demoSpringRestaurant.model.service.CourierDto;
import com.example.demoSpringRestaurant.model.service.CourierUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderWithoutReferencesMapper.class})
public interface CourierMapper {

    CourierDocument fromCourierDtoToDocument(CourierDto courierDto);

    CourierDto fromDocumentToCourierDto(CourierDocument courierDocument);

    CourierCreationDto fromDocumentToCourierCreationDto(CourierDocument courierDocument);

    CourierDocument fromCourierCreationDtoToDocument(CourierCreationDto courierCreationDto);

    CourierUpdateDto fromDocumentToCourierUpdateDto(CourierDocument courierDocument);

    CourierDocument fromCourierUpdateDtoToDocument(CourierUpdateDto courierUpdateDto);
}
