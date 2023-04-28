package controller;

import com.example.demoSpringRestaurant.Restaurant;
import service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        return service.addRestaurant(restaurant);
    }

    @DeleteMapping(path = "{restaurantId}")
    public void removeRestaurant(@PathVariable("restaurantId") Long id) {
        service.removeRestaurant(id);
    }

    @GetMapping("owner")
    public Map<String, List<String>> getRestaurantsByOwner() {
        return service.getRestaurantsByOwner();
    }
    @GetMapping("vegan")
    public List<Restaurant> getVeganRestaurants(){
        return service.getVeganRestaurants();
    }

    @PutMapping(path = "{restaurantId}")
    public void updateRestaurant(
            @PathVariable("restaurantId") Long id,
            @RequestParam(required = false) String owner,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) Integer numberOfTables,
            @RequestParam(required = false) Boolean isVegan,
            @RequestParam(required = false) Boolean canDeliver) {
        service.updateRestaurant(id,owner,name,address,email,phoneNumber,numberOfTables,isVegan,canDeliver);
    }

}
