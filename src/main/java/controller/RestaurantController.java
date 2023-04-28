package controller;

import com.example.demoSpringRestaurant.Restaurant;
import service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "hello")
@ComponentScan("com.example.demoSpringRestaurant")
public class RestaurantController {

    private final RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public List<Restaurant> getStudent() {
        return service.getRestaurants();
    }
}
