package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.GuestDocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.service.GuestMapper;
import com.example.demoSpringRestaurant.model.service.GuestDto;
import com.example.demoSpringRestaurant.model.service.GuestUpdateDto;
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
    public GuestDto getGuest(String id) throws GuestDocumentNotFoundException {
        log.trace("All guests listed");
        return guestRepository.findById(id)
                .map(guestMapper::fromDocumentToGuestDto)
                .orElseThrow(() -> new GuestDocumentNotFoundException("Guest not found"));
    }
    public GuestDto updateGuest(String guestId, GuestUpdateDto guestUpdateDto) throws GuestDocumentNotFoundException {
        log.trace("Updating order with ID: " + guestId + "to " + guestUpdateDto);
        var cDate = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestDocumentNotFoundException("guest doesn't exist"));

        var updatedDocument = guestMapper.fromGuestUpdateDtoToDocument(guestUpdateDto);
        updatedDocument.setId(guestId);
        updatedDocument.setCreatedDate(cDate.getCreatedDate());
        guestRepository.save(updatedDocument);
        log.trace("guest with ID: " + updatedDocument.getId() + " updated as" + updatedDocument);
        return guestMapper.fromDocumentToGuestDto(updatedDocument);
    }


    public GuestDto findGuestDtoByActiveOrder_Id(String id) throws GuestDocumentNotFoundException {
       var guest =  guestRepository.findGuestDocumentByActiveOrder_Id(id).
               orElseThrow(() -> new GuestDocumentNotFoundException("Guest not found"));
        return guestMapper.fromDocumentToGuestDto(guest);
    }


    public GuestDto findGuestById(String courierId) throws GuestDocumentNotFoundException {
        var guestDocument = guestRepository.findById(courierId)
                .orElseThrow(() -> new GuestDocumentNotFoundException("Guest not found"));
        return guestMapper.fromDocumentToGuestDto(guestDocument);
    }

    public GuestDto saveGuest(GuestDto guestDto) {
        var guestDocument = guestRepository.save(guestMapper.fromGuestDtoToDocument(guestDto));
        return guestMapper.fromDocumentToGuestDto(guestDocument);
    }


    public void deleteById(String id) throws GuestDocumentNotFoundException {
        if (guestRepository.existsById(id))
            guestRepository.deleteById(id);
        else throw new GuestDocumentNotFoundException("Guest not found");

    }


}
