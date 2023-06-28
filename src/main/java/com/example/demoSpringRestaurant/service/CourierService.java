package com.example.demoSpringRestaurant.service;

import com.example.demoSpringRestaurant.mapper.CourierMapper;
import com.example.demoSpringRestaurant.model.CourierCreationDto;
import com.example.demoSpringRestaurant.model.CourierDto;
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
    public CourierDto createCourier(CourierCreationDto CourierCreationDto) {
        log.trace("Creating Courier " + CourierCreationDto);
        var CourierEntity = courierRepository.save(courierMapper.
                fromCourierCreationDtoToDocument(CourierCreationDto));
        log.trace("Courier created with ID:" + CourierEntity.getId());
        return courierMapper.fromDocumentToCourierDto(CourierEntity);
    }
}
