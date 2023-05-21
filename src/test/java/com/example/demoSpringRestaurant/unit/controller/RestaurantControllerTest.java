package com.example.demoSpringRestaurant.unit.controller;

import com.example.demoSpringRestaurant.controller.RestaurantController;
import com.example.demoSpringRestaurant.exception.EntityNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {
    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private RestaurantOrderFacade restaurantOrderFacade;

    @Test
    void getRestaurant() {
        when(restaurantService.getRestaurants())
                .thenReturn(RestaurantFixture.getRestaurantDtoList());

        var restaurantDtoList = restaurantController.getRestaurant();

        assertThat(restaurantDtoList).usingRecursiveComparison()
                .isEqualTo(RestaurantFixture.getRestaurantEntityList());
    }

    @Test
    void addRestaurant() {
        when(restaurantService.addRestaurant(any(RestaurantCreationDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        var restaurantDto = restaurantController
                .addRestaurant(RestaurantFixture.getRestaurantCreationDto());

        assertThat(restaurantDto).usingRecursiveComparison()
                .isEqualTo(RestaurantFixture.getRestaurantDto());
    }

    @Test
    void removeRestaurant() throws RestaurantEntityNotFoundException {
        when(restaurantOrderFacade.removeRestaurant(any(Long.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        var restaurantDto = restaurantController
                .removeRestaurant(RestaurantFixture.getRestaurantDto().getId());

        assertThat(restaurantDto).usingRecursiveComparison()
                .isEqualTo(RestaurantFixture.getRestaurantDto());
    }

    @Test
    void getRestaurantsByOwner() {
    }

    @Test
    void updateRestaurant() throws EntityNotFoundException {
        when(restaurantService.updateRestaurant(any(Long.class), any(RestaurantUpdateDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        var restaurantDto = restaurantController
                .updateRestaurant(RestaurantFixture.getRestaurantDto().getId(),
                        RestaurantFixture.getRestaurantUpdateDto());

        assertThat(restaurantDto).usingRecursiveComparison()
                .isEqualTo(RestaurantFixture.getRestaurantDto());
    }

    @Test
    void updateParametersInRestaurant() throws EntityNotFoundException {
        when(restaurantService.updateParametersInRestaurant(any(Long.class), any(RestaurantUpdateDto.class)))
                .thenReturn(RestaurantFixture.getRestaurantDto());

        var restaurantDto = restaurantController
                .updateParametersInRestaurant(RestaurantFixture.getRestaurantDto().getId(),
                        RestaurantFixture.getRestaurantUpdateDto());

        assertThat(restaurantDto).usingRecursiveComparison()
                .isEqualTo(RestaurantFixture.getRestaurantDto());
    }

    @Test
    void getVeganRestaurants() {
        when(restaurantService.getVeganRestaurant())
                .thenReturn(RestaurantFixture.getRestaurantDtoList());

        var restaurantDtoList = restaurantController
                .getVeganRestaurants();

        assertThat(restaurantDtoList).usingRecursiveComparison()
                .isEqualTo(RestaurantFixture.getRestaurantDtoList());
    }
}