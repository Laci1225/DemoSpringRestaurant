package com.example.demoSpringRestaurant.mapper;

import com.example.demoSpringRestaurant.model.CourierCreationDto;
import com.example.demoSpringRestaurant.model.CourierDto;
import com.example.demoSpringRestaurant.model.CourierUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface CourierMapper {

    CourierDocument fromCourierDtoToDocument(CourierDto CourierDto);

    CourierDto fromDocumentToCourierDto(CourierDocument CourierDocument);

    CourierCreationDto fromDocumentToCourierCreationDto(CourierDocument CourierDocument);

    CourierDocument fromCourierCreationDtoToDocument(CourierCreationDto CourierCreationDto);

    CourierUpdateDto fromDocumentToCourierUpdateDto(CourierDocument CourierDocument);

    CourierDocument fromCourierUpdateDtoToDocument(CourierUpdateDto CourierUpdateDto);
}
