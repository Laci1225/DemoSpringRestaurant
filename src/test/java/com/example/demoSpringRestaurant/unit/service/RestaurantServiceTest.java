package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import com.example.demoSpringRestaurant.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
    void addRestaurantShouldCreateOneRestaurant() {
        //arrange / given
        when(restaurantMapper.fromRestaurantCreationDtoToEntity(any(RestaurantCreationDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantEntity(false));
        when(restaurantMapper.fromEntityToRestaurantDto(any(RestaurantEntity.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantRepository.save(any(RestaurantEntity.class)))
                .thenReturn(RestaurantFixture.getRestaurantEntity(true));

        //act / when
        var restaurantDto = restaurantService.addRestaurant(RestaurantFixture.getRestaurantCreationDto());

        //assert / then
        assertThat(restaurantDto).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantRepository, times(1)).save(any(RestaurantEntity.class));
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void getRestaurantShouldGetAllRestaurant() {
        when(restaurantMapper.fromEntityToRestaurantDto(any(RestaurantEntity.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantRepository.findAll()).thenReturn(RestaurantFixture.getRestaurantEntityList());

        //act / when
        var restaurantDtoList = restaurantService.getRestaurants();

        //assert / then
        assertThat(restaurantDtoList).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDtoList());
        verify(restaurantRepository, times(1)).findAll();
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void updateRestaurantShouldUpdateOneRestaurant() throws EntityNotFoundException {
        //arrange / given
        when(restaurantMapper.fromEntityToRestaurantDto(any(RestaurantEntity.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantMapper.fromRestaurantUpdateDtoToEntity(any(RestaurantUpdateDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantEntity(false));
        when(restaurantRepository.save(any(RestaurantEntity.class)))
                .thenReturn(RestaurantFixture.getRestaurantEntity(true));
        when(restaurantRepository.existsById(any(Long.class))).thenReturn(true);
        //act / when
        var restaurantUpdate = restaurantService.updateRestaurant(5L, RestaurantFixture.getRestaurantUpdateDto());
        var restaurantUpdate2 = restaurantService.updateRestaurant(1L, RestaurantFixture.getRestaurantUpdateDto());

        //assert / then
        assertThat(restaurantUpdate.getId()).isEqualTo(1L);
        assertThat(restaurantUpdate2.getId()).isEqualTo(1L);
        assertThat(restaurantUpdate).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        assertThat(restaurantUpdate).isEqualTo(restaurantUpdate2);
        verify(restaurantRepository, times(2)).existsById(any(Long.class));
        verify(restaurantRepository, times(2)).save(any(RestaurantEntity.class));
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void updateParametersInRestaurantShouldUpdateOneRestaurant() throws EntityNotFoundException {
        when(restaurantMapper.fromEntityToRestaurantDto(any(RestaurantEntity.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantMapper.fromRestaurantDtoToEntity(any(RestaurantDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantEntity(true));
        when(restaurantRepository.save(any(RestaurantEntity.class)))
                .thenReturn(RestaurantFixture.getRestaurantEntity(true));
        when(restaurantRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(RestaurantFixture.getRestaurantEntity(true)));

        var restaurantDto = restaurantService.updateParametersInRestaurant(5L, RestaurantFixture.getRestaurantUpdateDto());

        assertThat(restaurantDto.getId()).isEqualTo(1L);
        assertThat(restaurantDto).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantRepository, times(1)).findById(any(Long.class));
        verify(restaurantRepository, times(1)).save(any(RestaurantEntity.class));
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void getVeganRestaurantShouldGetAllVeganRestaurant() {
        when(restaurantMapper.fromEntityToRestaurantDto(any(RestaurantEntity.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantRepository.findAllByIsVeganTrue())
                .thenReturn(RestaurantFixture.getRestaurantEntityList());

        var restaurantDtoList = restaurantService.getVeganRestaurant();

        assertThat(restaurantDtoList).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDtoList());
        verify(restaurantRepository, times(1)).findAllByIsVeganTrue();
        verifyNoMoreInteractions(restaurantRepository);
    }
}
