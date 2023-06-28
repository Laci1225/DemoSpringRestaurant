package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.mapper.GuestMapper;
import com.example.demoSpringRestaurant.model.GuestCreationDto;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.persistance.repository.GuestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GuestService {

    private final GuestMapper guestMapper;
    private final GuestRepository guestRepository;

    public List<GuestDto> getGuests() {
        log.trace("All guests listed");
        return guestRepository.findAll().stream()
                .map(guestMapper::fromDocumentToGuestDto).toList();
    }

    public GuestDto createGuest(GuestCreationDto GuestCreationDto) {
        log.trace("Creating Guest " + GuestCreationDto);
        var guestDocument = guestRepository.save(guestMapper.
                fromGuestCreationDtoToDocument(GuestCreationDto));
        log.trace("Guest created with ID:" + guestDocument.getId());
        return guestMapper.fromDocumentToGuestDto(guestDocument);
    }
}
