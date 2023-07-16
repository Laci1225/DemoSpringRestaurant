package com.example.demoSpringRestaurant.mapper;

import com.example.demoSpringRestaurant.model.CourierCreationDto;
import com.example.demoSpringRestaurant.model.CourierDto;
import com.example.demoSpringRestaurant.model.CourierUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface CourierMapper {

    CourierDocument fromCourierDtoToDocument(CourierDto courierDto);

    CourierDto fromDocumentToCourierDto(CourierDocument courierDocument);

    CourierCreationDto fromDocumentToCourierCreationDto(CourierDocument courierDocument);

    CourierDocument fromCourierCreationDtoToDocument(CourierCreationDto courierCreationDto);

    CourierUpdateDto fromDocumentToCourierUpdateDto(CourierDocument courierDocument);

    CourierDocument fromCourierUpdateDtoToDocument(CourierUpdateDto courierUpdateDto);
}
