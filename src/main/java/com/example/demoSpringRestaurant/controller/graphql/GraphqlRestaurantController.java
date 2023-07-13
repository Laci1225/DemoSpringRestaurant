package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.controller.RestaurantController;
import com.example.demoSpringRestaurant.model.RestaurantCreationDto;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class GraphqlRestaurantController {

    private final RestaurantService restaurantService;

    @QueryMapping("restaurants")
    public List<RestaurantDto> getRestaurants() {
        var restaurantList = restaurantService.getRestaurants();
        return restaurantList;
    }

    /*@QueryMapping("restaurant") //TODO model.graphql.GraphqlRestaurantDto
    // TODO mapper.graphql.GraphqlRestaurantMapper
    public GraphqlRestaurantDto getRestaurantById(@Argument String id) {
        var restaurant = restaurantService.getRestaurant(id);
        return restaurant; / TODO restnél sincs ilyen method
        //TODO hibakezelés rossz idra
    }*/

    @MutationMapping("createRestaurant")
    public RestaurantDto createRestaurant(@Valid @RequestBody @Argument RestaurantCreationDto restaurant) {
        var restaurantDto = restaurantService.createRestaurant(restaurant);
        return restaurantDto;
    }//TODO
}
