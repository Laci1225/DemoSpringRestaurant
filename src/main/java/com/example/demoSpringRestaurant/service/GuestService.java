package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.GuestMapper;
import com.example.demoSpringRestaurant.model.GuestDto;
import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
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
