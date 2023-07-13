package com.example.demoSpringRestaurant.mapper;

import com.example.demoSpringRestaurant.model.CourierCreationDto;
import com.example.demoSpringRestaurant.model.CourierDto;
import com.example.demoSpringRestaurant.model.CourierUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface CourierMapper {

    //@Mapping(target = "isActive",source = "active") // TODO Why????
    CourierDocument fromCourierDtoToDocument(CourierDto courierDto);

   // @Mapping(target = "isActive",source = "courierDocument.active")
    CourierDto fromDocumentToCourierDto(CourierDocument courierDocument);

    //@Mapping(target = "isActive",source = "courierDocument.active")
    CourierCreationDto fromDocumentToCourierCreationDto(CourierDocument courierDocument);

    //@Mapping(target = "isActive",source = "courierCreationDto.active")
    CourierDocument fromCourierCreationDtoToDocument(CourierCreationDto courierCreationDto);

    //@Mapping(target = "isActive",source = "courierDocument.active")
    CourierUpdateDto fromDocumentToCourierUpdateDto(CourierDocument courierDocument);

    //@Mapping(target = "isActive",source = "courierUpdateDto.active")
    CourierDocument fromCourierUpdateDtoToDocument(CourierUpdateDto courierUpdateDto);
}
