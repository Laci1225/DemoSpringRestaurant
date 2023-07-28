package com.example.demoSpringRestaurant.mapper.service;

import com.example.demoSpringRestaurant.model.service.GuestCreationDto;
import com.example.demoSpringRestaurant.model.service.GuestDto;
import com.example.demoSpringRestaurant.model.service.GuestUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})//todo
public interface GuestMapper {

    GuestDocument fromGuestDtoToDocument(GuestDto GuestDto);

    GuestDto fromDocumentToGuestDto(GuestDocument GuestDocument);

    GuestCreationDto fromDocumentToGuestCreationDto(GuestDocument GuestDocument);

    GuestDocument fromGuestCreationDtoToDocument(GuestCreationDto GuestCreationDto);

    GuestDto fromCreationDtoToDto(GuestCreationDto guestCreationDto);

    GuestUpdateDto fromDocumentToGuestUpdateDto(GuestDocument GuestDocument);

    GuestDocument fromGuestUpdateDtoToDocument(GuestUpdateDto GuestUpdateDto);
}
