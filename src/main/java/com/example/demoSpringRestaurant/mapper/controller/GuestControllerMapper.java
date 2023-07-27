package com.example.demoSpringRestaurant.mapper.controller;

import com.example.demoSpringRestaurant.model.service.GuestCreationDto;
import com.example.demoSpringRestaurant.model.service.GuestDto;
import com.example.demoSpringRestaurant.model.service.GuestUpdateDto;
import com.example.demoSpringRestaurant.model.controller.Guest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderWithoutGuestControllerMapper.class})//todo
public interface GuestControllerMapper {

    Guest fromGuestDtoToGuest(GuestDto GuestDto);

    GuestDto fromGuestToGuestDto(Guest Guest);

    /*GuestCreationDto fromDocumentToGuestCreationDto(Guest Guest);

    Guest fromGuestCreationDtoToDocument(GuestCreationDto GuestCreationDto);

    GuestDto fromCreationDtoToDto(GuestCreationDto guestCreationDto);

    GuestUpdateDto fromDocumentToGuestUpdateDto(Guest Guest);

    Guest fromGuestUpdateDtoToDocument(GuestUpdateDto GuestUpdateDto);*/
}
