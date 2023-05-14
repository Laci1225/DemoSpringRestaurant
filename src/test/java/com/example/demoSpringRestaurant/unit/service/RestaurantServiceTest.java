package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import com.example.demoSpringRestaurant.service.RestaurantService;
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

    @Test
    void addRestaurantShouldCreateOneRestaurant() {
        //arrange / given
        /*RestaurantCreationDto restaurantCreationDto = RestaurantCreationDto.builder()
                .name("John's restaurant")
                .owner("John")
                .address("Budapest")
                .email("b@b.com")
                .phoneNumber("1231241242")
                .numberOfTables(1)
                .canDeliver(true)
                .build();*/

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
        verify(restaurantRepository,times(1)).save(any(RestaurantEntity.class));
        verifyNoMoreInteractions(restaurantRepository);
    }
}
