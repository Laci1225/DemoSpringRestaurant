package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.GuestDocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.GuestMapper;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.model.GuestUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
import com.example.demoSpringRestaurant.persistance.repository.GuestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public GuestDto getGuest(String id) {
        log.trace("All guests listed");
        return guestRepository.findById(id)
                .map(guestMapper::fromDocumentToGuestDto)
                .orElseThrow();
    }
    public GuestDto updateGuest(String guestId, GuestUpdateDto guestUpdateDto) throws GuestDocumentNotFoundException {
        log.trace("Updating order with ID: " + guestId + "to " + guestUpdateDto);
        guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestDocumentNotFoundException("guest doesn't exist"));

        var updatedDocument = guestMapper.fromGuestUpdateDtoToDocument(guestUpdateDto);
        updatedDocument.setId(guestId);
        guestRepository.save(updatedDocument);
        log.trace("guest with ID: " + updatedDocument.getId() + " updated as" + updatedDocument);
        return guestMapper.fromDocumentToGuestDto(updatedDocument);
    }


    public Optional<GuestDocument> findGuestDocumentByActiveOrder_Id(String id) {
       return guestRepository.findGuestDocumentByActiveOrder_Id(id);
    }

    public void deleteByOrderId(String id) {
        guestRepository.deleteById(id);
    }

    public Optional<GuestDocument> findById(String courierId) {
        return guestRepository.findById(courierId);
    }

    public GuestDocument saveGuest(GuestDocument guestDocument) {
        return guestRepository.save(guestDocument);
    }


    public void deleteById(String id) throws DocumentNotFoundException {
        if (guestRepository.existsById(id))
            guestRepository.deleteById(id);
        else throw new DocumentNotFoundException("sad");

    }


}
