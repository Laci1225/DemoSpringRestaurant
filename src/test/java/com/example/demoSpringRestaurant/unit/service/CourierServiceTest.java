package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.fixtures.CourierFixture;
import com.example.demoSpringRestaurant.mapper.CourierMapper;
import com.example.demoSpringRestaurant.model.CourierCreationDto;
import com.example.demoSpringRestaurant.model.CourierUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import com.example.demoSpringRestaurant.persistance.repository.CourierRepository;
import com.example.demoSpringRestaurant.service.CourierService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourierServiceTest {

    @InjectMocks
    private CourierService courierService;

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private CourierMapper courierMapper;

    @Test
    void getCourierShouldGetAllCourier() {
        when(courierMapper.fromDocumentToCourierDto(any(CourierDocument.class)))
                .thenReturn(CourierFixture.getCourierDto());
        when(courierRepository.findAll()).thenReturn(CourierFixture.getCourierDocumentList());

        //act / when
        var courierDtoList = courierService.getCouriers();

        //assert / then
        assertThat(courierDtoList).usingRecursiveComparison().isEqualTo(CourierFixture.getCourierDtoList());
        verify(courierRepository, times(1)).findAll();
        verifyNoMoreInteractions(courierRepository);
    }


    @Test
    void createCourierShouldCreateOneCourier() {
        //arrange / given
        when(courierMapper.fromCourierCreationDtoToDocument(any(CourierCreationDto.class)))
                .thenReturn(CourierFixture.getCourierDocument(false));
        when(courierMapper.fromDocumentToCourierDto(any(CourierDocument.class)))
                .thenReturn(CourierFixture.getCourierDto());
        when(courierRepository.save(any(CourierDocument.class)))
                .thenReturn(CourierFixture.getCourierDocument(true));

        //act / when
        var courierDto = courierService.createCourier(CourierFixture.getCourierCreationDto());

        //assert / then
        assertThat(courierDto).usingRecursiveComparison().isEqualTo(CourierFixture.getCourierDto());
        verify(courierRepository, times(1)).save(any(CourierDocument.class));
        verifyNoMoreInteractions(courierRepository);
    }


    @Test
    void getCourierShouldGetACourier() {
        when(courierMapper.fromDocumentToCourierDto(any(CourierDocument.class)))
                .thenReturn(CourierFixture.getCourierDto());
        when(courierRepository.findById(anyString()))
                .thenReturn(Optional.ofNullable(CourierFixture.getCourierDocument(true)));

        //act / when
        var courierDto = courierService.getCourier("1234");

        //assert / then
        assertThat(courierDto).usingRecursiveComparison().isEqualTo(CourierFixture.getCourierDto());
        verify(courierRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(courierRepository);
    }

    @Test
    void updateCourierShouldUpdateOneCourier() throws DocumentNotFoundException {
        //arrange / given
        when(courierMapper.fromDocumentToCourierDto(any(CourierDocument.class)))
                .thenReturn(CourierFixture.getCourierDto());
        when(courierMapper.fromCourierUpdateDtoToDocument(any(CourierUpdateDto.class)))
                .thenReturn(CourierFixture.getCourierDocument(false));
        when(courierRepository.save(any(CourierDocument.class)))
                .thenReturn(CourierFixture.getCourierDocument(true));
        when(courierRepository.findById(anyString())).thenReturn(Optional.of(CourierFixture.getCourierDocument(true)));
        //act / when
        var courierUpdate = courierService.updateCourier("1L", CourierFixture.getCourierUpdateDto());

        //assert / then
        assertThat(courierUpdate).usingRecursiveComparison().isEqualTo(CourierFixture.getCourierDto());
        verify(courierRepository, times(1)).findById(anyString());
        verify(courierRepository, times(1)).save(any(CourierDocument.class));
        verifyNoMoreInteractions(courierRepository);
    }

    @Test
    void deleteByIdShouldThrowCourierDocumentNotFoundException() throws CourierDocumentNotFoundException {
        when(courierRepository.existsById(anyString()))
                .thenReturn(false);

        assertThrows(CourierDocumentNotFoundException.class, () ->
                courierService.deleteById(CourierFixture.getCourierDto().getId()));
    }

    @Test
    void deleteByIdShouldDeleteOneCourier() throws CourierDocumentNotFoundException {
        doNothing().when(courierRepository).deleteById(anyString());
        when(courierRepository.existsById(anyString()))
                .thenReturn(true);

        courierService.deleteById(CourierFixture.getCourierDto().getId());

        verify(courierRepository, times(1)).deleteById(anyString());
        verifyNoMoreInteractions(courierRepository);
    }

    @Test
    void findCourierDtoByActiveOrder_Id() throws CourierDocumentNotFoundException {
        when(courierRepository.findCourierDocumentByActiveOrder_Id(anyString())).
                thenReturn(Optional.of(CourierFixture.getCourierDocument(true)));
        when(courierMapper.fromDocumentToCourierDto(any(CourierDocument.class)))
                .thenReturn(CourierFixture.getCourierDto());

        var courierDto = courierService.findCourierDtoByActiveOrder_Id(
                "1234");

        assertThat(courierDto).usingRecursiveComparison().isEqualTo(CourierFixture.getCourierDto());
        verify(courierRepository, times(1)).findCourierDocumentByActiveOrder_Id(anyString());
        verifyNoMoreInteractions(courierRepository);
    }

    @Test
    void findByCourierIdShouldReturnOneCourier() throws CourierDocumentNotFoundException {
        when(courierRepository.findById(anyString())).
                thenReturn(Optional.of(CourierFixture.getCourierDocument(true)));
        when(courierMapper.fromDocumentToCourierDto(any(CourierDocument.class)))
                .thenReturn(CourierFixture.getCourierDto());

        var courierDto = courierService.findCourierById("1234");

        assertThat(courierDto).usingRecursiveComparison().isEqualTo(CourierFixture.getCourierDto());
        verify(courierRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(courierRepository);
    }

}