package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.service.RestaurantMapper;
import com.example.demoSpringRestaurant.model.service.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.service.RestaurantDto;
import com.example.demoSpringRestaurant.model.service.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.RestaurantDocument;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import com.example.demoSpringRestaurant.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @Test
    void createRestaurantShouldCreateOneRestaurant() {
        //arrange / given
        when(restaurantMapper.fromRestaurantCreationDtoToDocument(any(RestaurantCreationDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDocument(false));
        when(restaurantMapper.fromDocumentToRestaurantDto(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantRepository.save(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDocument(true));

        //act / when
        var restaurantDto = restaurantService.createRestaurant(RestaurantFixture.getRestaurantCreationDto());

        //assert / then
        assertThat(restaurantDto).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantRepository, times(1)).save(any(RestaurantDocument.class));
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void getRestaurantsShouldGetAllRestaurant() {
        when(restaurantMapper.fromDocumentToRestaurantDto(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantRepository.findAll()).thenReturn(RestaurantFixture.getRestaurantDocumentList());

        //act / when
        var restaurantDtoList = restaurantService.getRestaurants();

        //assert / then
        assertThat(restaurantDtoList).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDtoList());
        verify(restaurantRepository, times(1)).findAll();
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void getRestaurantShouldGetARestaurant() {
        when(restaurantMapper.fromDocumentToRestaurantDto(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantRepository.findById(anyString()))
                .thenReturn(Optional.ofNullable(RestaurantFixture.getRestaurantDocument(true)));

        //act / when
        var restaurantDto = restaurantService.getRestaurant("1234");

        //assert / then
        assertThat(restaurantDto).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void updateRestaurantShouldUpdateOneRestaurant() throws DocumentNotFoundException {
        //arrange / given
        when(restaurantMapper.fromDocumentToRestaurantDto(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantMapper.fromRestaurantUpdateDtoToDocument(any(RestaurantUpdateDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDocument(false));
        when(restaurantRepository.save(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDocument(true));
        when(restaurantRepository.findById(anyString())).thenReturn(Optional.of(RestaurantFixture.getRestaurantDocument(true)));
        //act / when
        var restaurantUpdate = restaurantService.updateRestaurant("1L", RestaurantFixture.getRestaurantUpdateDto());

        //assert / then
        assertThat(restaurantUpdate).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantRepository, times(1)).findById(anyString());
        verify(restaurantRepository, times(1)).save(any(RestaurantDocument.class));
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void updateRestaurantShouldThrowRestaurantNotFoundException() {
        when(restaurantRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(RestaurantDocumentNotFoundException.class, () ->
                restaurantService.updateRestaurant("1L", RestaurantFixture.getRestaurantUpdateDto()));

        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void updateParametersInRestaurantShouldUpdateOneRestaurant() throws DocumentNotFoundException {
        when(restaurantMapper.fromDocumentToRestaurantDto(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantMapper.fromRestaurantDtoToDocument(any(RestaurantDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDocument(true));
        when(restaurantRepository.save(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDocument(true));
        when(restaurantRepository.findById(anyString()))
                .thenReturn(Optional.of(RestaurantFixture.getRestaurantDocument(true)));

        var restaurantDto = restaurantService.updateParametersInRestaurant("1L", RestaurantFixture.getRestaurantUpdateDto());

        assertThat(restaurantDto).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantRepository, times(1)).findById(anyString());
        verify(restaurantRepository, times(1)).save(any(RestaurantDocument.class));
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void updateParametersInRestaurantShouldThrowRestaurantNotFoundException() {
        when(restaurantRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(RestaurantDocumentNotFoundException.class, () ->
                restaurantService.updateParametersInRestaurant("1L", RestaurantFixture.getRestaurantUpdateDto()));

        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void updateParametersInRestaurantShouldDoNothing() throws RestaurantDocumentNotFoundException {
        when(restaurantMapper.fromDocumentToRestaurantDto(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantMapper.fromRestaurantDtoToDocument(any(RestaurantDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDocument(true));
        when(restaurantRepository.save(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDocument(true));
        when(restaurantRepository.findById(anyString()))
                .thenReturn(Optional.of(RestaurantFixture.getRestaurantDocument(true)));
        var restaurantDto = restaurantService.updateParametersInRestaurant("1L", RestaurantFixture.getRestaurantUpdateDtoSetEverythingToNull());

        assertThat(restaurantDto).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantRepository, times(1)).findById(anyString());
        verify(restaurantRepository, times(1)).save(any(RestaurantDocument.class));
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void getVeganRestaurantShouldGetAllVeganRestaurant() {
        when(restaurantMapper.fromDocumentToRestaurantDto(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantRepository.findAllByIsVeganTrue())
                .thenReturn(RestaurantFixture.getRestaurantDocumentList());

        var restaurantDtoList = restaurantService.getVeganRestaurant();

        assertThat(restaurantDtoList).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDtoList());
        verify(restaurantRepository, times(1)).findAllByIsVeganTrue();
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void deleteRestaurantShouldRemoveOneRestaurant() {
        doNothing().when(restaurantRepository).delete(any(RestaurantDocument.class));

        restaurantService.deleteRestaurant(RestaurantFixture.getRestaurantDocument(false));

        verify(restaurantRepository, times(1)).delete(any(RestaurantDocument.class));
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void findRestaurantByIdShouldReturnARestaurant() throws RestaurantDocumentNotFoundException {
        when(restaurantRepository.findById(anyString()))
                .thenReturn(Optional.of(RestaurantFixture.getRestaurantDocument(false)));
        when(restaurantMapper.fromDocumentToRestaurantDto(any(RestaurantDocument.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        var restaurantDto = restaurantService.findRestaurantById(RestaurantFixture.getRestaurantDto().getId());

        assertThat(restaurantDto).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void findRestaurantByIdShouldThrowRestaurantNotFoundException() throws RestaurantDocumentNotFoundException {
        when(restaurantRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(RestaurantDocumentNotFoundException.class, () ->
                restaurantService.findRestaurantById(RestaurantFixture.getRestaurantDto().getId()));

        verifyNoMoreInteractions(restaurantRepository);
    }


    @Test
    void restaurantExistShouldReturnTrue() throws RestaurantDocumentNotFoundException {
        when(restaurantRepository.existsById((anyString())))
                .thenReturn(true);

        var exist = restaurantService.restaurantExist(RestaurantFixture.getRestaurantDto().getId());

        verify(restaurantRepository, times(1)).existsById(anyString());
        verifyNoMoreInteractions(restaurantRepository);
    }
    @Test
    void restaurantExistShouldThrowRestaurantNotFoundException(){
        when(restaurantRepository.existsById((anyString())))
                .thenReturn(false);

        assertThrows(RestaurantDocumentNotFoundException.class, () ->
                restaurantService.restaurantExist(RestaurantFixture.getRestaurantDto().getId()));

        verifyNoMoreInteractions(restaurantRepository);
    }
}
