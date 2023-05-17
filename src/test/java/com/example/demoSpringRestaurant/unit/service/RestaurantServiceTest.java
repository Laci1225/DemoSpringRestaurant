package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import com.example.demoSpringRestaurant.service.RestaurantService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    public void setUp(){
        when(restaurantMapper.fromRestaurantCreationDtoToEntity(any(RestaurantCreationDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantEntity(false));
        when(restaurantMapper.fromEntityToRestaurantDto(any(RestaurantEntity.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());
        when(restaurantRepository.save(any(RestaurantEntity.class)))
                .thenReturn(RestaurantFixture.getRestaurantEntity(true));
    }

    @Test
    void addRestaurantShouldCreateOneRestaurant() {
        //arrange / given
        setUp();

        //act / when
        var restaurantDto = restaurantService.addRestaurant(RestaurantFixture.getRestaurantCreationDto());

        //assert / then
        assertThat(restaurantDto).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantRepository, times(1)).save(any(RestaurantEntity.class));
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void getRestaurantShouldGetAllRestaurant() {
        //arrange / given
        setUp();
        when(restaurantRepository.findAll()).thenReturn(RestaurantFixture.getRestaurantEntityList());

        //act / when
        restaurantService.addRestaurant(RestaurantFixture.getRestaurantCreationDto());
        var restaurantDtoList = restaurantService.getRestaurants();

        //assert / then
        assertThat(restaurantDtoList).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDtoList());
        verify(restaurantRepository, times(1)).findAll();
        verify(restaurantRepository, times(1)).save(any(RestaurantEntity.class));
        verifyNoMoreInteractions(restaurantRepository);
    }
    @SneakyThrows
    @Test
    void updateRestaurantShouldUpdateOneRestaurant() {
        //arrange / given
        setUp();
        when(restaurantRepository.existsById(any(Long.class))).thenReturn(true);
        when(restaurantMapper.fromRestaurantUpdateDtoToEntity(any()))
                .thenReturn(RestaurantFixture.getRestaurantEntity(false));
        //act / when
        restaurantService.addRestaurant(RestaurantFixture.getRestaurantCreationDto());
        var restaurantUpdate = restaurantService.updateRestaurant(1L, RestaurantFixture.getRestaurantUpdateDto());

        //assert / then
        assertThat(restaurantUpdate).usingRecursiveComparison().isEqualTo(RestaurantFixture.getRestaurantDto());
        verify(restaurantRepository, times(1)).existsById(any(Long.class));
        verify(restaurantRepository, times(2)).save(any(RestaurantEntity.class));
        verifyNoMoreInteractions(restaurantRepository);
    }
}
