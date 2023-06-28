package com.example.demoSpringRestaurant.mapper;

import com.example.demoSpringRestaurant.model.GuestCreationDto;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.model.GuestUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface GuestMapper {

    GuestDocument fromGuestDtoToDocument(GuestDto GuestDto);

    GuestDto fromDocumentToGuestDto(GuestDocument GuestDocument);

    GuestCreationDto fromDocumentToGuestCreationDto(GuestDocument GuestDocument);

    GuestDocument fromGuestCreationDtoToDocument(GuestCreationDto GuestCreationDto);

    GuestUpdateDto fromDocumentToGuestUpdateDto(GuestDocument GuestDocument);

    GuestDocument fromGuestUpdateDtoToDocument(GuestUpdateDto GuestUpdateDto);
}
