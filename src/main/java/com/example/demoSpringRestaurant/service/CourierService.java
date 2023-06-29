package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.mapper.CourierMapper;
import com.example.demoSpringRestaurant.model.CourierCreationDto;
import com.example.demoSpringRestaurant.model.CourierDto;
import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import com.example.demoSpringRestaurant.persistance.repository.CourierRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public CourierDto createCourier(CourierCreationDto CourierCreationDto) {
        log.trace("Creating Courier " + CourierCreationDto);
        var courierEntity = courierRepository.save(courierMapper.
                fromCourierCreationDtoToDocument(CourierCreationDto));
        log.trace("Courier created with ID:" + courierEntity.getId());
        return courierMapper.fromDocumentToCourierDto(courierEntity);
    }

    public Optional<CourierDocument> findCourierDocumentByActiveOrder_Id(String id) {
        return courierRepository.findCourierDocumentByActiveOrder_Id(id);
    }

    public void deleteByOrderId(String id) {
        courierRepository.deleteById(id);
    }

    public Optional<CourierDocument> findById(String courierId) {
        return courierRepository.findById(courierId);
    }
}
