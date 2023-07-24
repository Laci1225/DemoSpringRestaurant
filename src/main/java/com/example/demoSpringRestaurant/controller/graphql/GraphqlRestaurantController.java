package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.model.RestaurantUpdateDto;
import com.example.demoSpringRestaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@AllArgsConstructor
public class GraphqlRestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantOrderFacade restaurantOrderFacade;

    @QueryMapping("restaurants")
    public List<RestaurantDto> getRestaurants() {
        var restaurantList = restaurantService.getRestaurants();
        return restaurantList;
    }

    @QueryMapping("restaurant") //TODO model.graphql.GraphqlRestaurantDto
    // TODO mapper.graphql.GraphqlRestaurantMapper
    public RestaurantDto getRestaurantById(@Argument String id) {
        var restaurant = restaurantService.getRestaurant(id);
        return restaurant;
        //TODO hibakezelés rossz idra graphql hiba máshogy nézzen ki + default szépen nem kell teszt
        // springboot error handling
    }

    @MutationMapping("createRestaurant")
    public RestaurantDto createRestaurant(@Valid @RequestBody @Argument RestaurantCreationDto restaurant) {
        var restaurantDto = restaurantService.createRestaurant(restaurant);
        return restaurantDto;
    }

    @MutationMapping("deleteRestaurant")
    public RestaurantDto deleteRestaurant(@Argument String id) {
        try {
            var restaurantDto = restaurantOrderFacade.deleteRestaurant(id);
            return restaurantDto;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("updateRestaurant")
    public RestaurantDto updateRestaurant(@Argument String id, @Valid @RequestBody @Argument RestaurantUpdateDto restaurant) {
        try {
            var restaurantUpdate = restaurantService.updateRestaurant(id, restaurant);
            return restaurantUpdate;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

    }
    //TODO updateParametersInRestaurant not necessary equal in that case
    @QueryMapping("vegan")
    public List<RestaurantDto> getVeganRestaurants() {
        var restaurantDto = restaurantService.getVeganRestaurant();
        return restaurantDto;
    }


}
