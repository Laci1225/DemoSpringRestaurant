package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.CourierMapper;
import com.example.demoSpringRestaurant.model.CourierCreationDto;
import com.example.demoSpringRestaurant.model.CourierDto;
import com.example.demoSpringRestaurant.model.CourierUpdateDto;
import com.example.demoSpringRestaurant.persistance.repository.CourierRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CourierService {
    private final CourierMapper courierMapper;
    private final CourierRepository courierRepository;

    public List<CourierDto> getCouriers() {
        log.trace("All courier listed");
        return courierRepository.findAll().stream()
                .map(courierMapper::fromDocumentToCourierDto).toList();
    }

    public CourierDto getCourier(String courierId) throws CourierDocumentNotFoundException {
        return courierRepository.findById(courierId)
                .map(courierMapper::fromDocumentToCourierDto).orElseThrow(
                        () -> new CourierDocumentNotFoundException("Courier not found")
                );
    }

    public CourierDto createCourier(CourierCreationDto CourierCreationDto) {
        log.trace("Creating Courier " + CourierCreationDto);
        var courierEntity = courierRepository.save(courierMapper.
                fromCourierCreationDtoToDocument(CourierCreationDto));
        log.trace("Courier created with ID:" + courierEntity.getId());
        return courierMapper.fromDocumentToCourierDto(courierEntity);
    }

    public CourierDto updateCourier(String courierId, CourierUpdateDto courierUpdateDto) throws CourierDocumentNotFoundException {
        log.trace("Updating order with ID: " + courierId + "to " + courierUpdateDto);
        var cDate = courierRepository.findById(courierId)
                .orElseThrow(() -> new CourierDocumentNotFoundException("courier doesn't exist"));

        var updatedDocument = courierMapper.fromCourierUpdateDtoToDocument(courierUpdateDto);
        updatedDocument.setId(courierId);
        updatedDocument.setCreatedDate(cDate.getCreatedDate());
        courierRepository.save(updatedDocument);
        log.trace("courier with ID: " + updatedDocument.getId() + " updated as" + updatedDocument);
        return courierMapper.fromDocumentToCourierDto(updatedDocument);
    }

    public CourierDto findCourierDtoByActiveOrder_Id(String id) throws CourierDocumentNotFoundException {
        var courier =  courierRepository.findCourierDocumentByActiveOrder_Id(id).
                orElseThrow(() -> new CourierDocumentNotFoundException("Courier not found"));
        return courierMapper.fromDocumentToCourierDto(courier);
    }

    public CourierDto findCourierById(String courierId) throws CourierDocumentNotFoundException {
        var courierDocument = courierRepository.findById(courierId)
                .orElseThrow(() -> new CourierDocumentNotFoundException("Courier not found"));
        return courierMapper.fromDocumentToCourierDto(courierDocument);
    }

    public void deleteById(String id) throws CourierDocumentNotFoundException {
        if (courierRepository.existsById(id))
            courierRepository.deleteById(id);
        else throw new CourierDocumentNotFoundException("Courier not found");
    }
}
