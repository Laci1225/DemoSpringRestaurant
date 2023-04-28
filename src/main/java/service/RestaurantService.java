package service;

import com.example.demoSpringRestaurant.Restaurant;
import com.example.demoSpringRestaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ComponentScan({"com.example.demoSpringRestaurant"})//,"repository"})
@Service
public class RestaurantService {

    public final RestaurantRepository repository;
    //public final Restaurant restaurant;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {//, Restaurant restaurant) {
        //this.restaurant = new Restaurant();
        this.repository = repository;
    }


    public List<Restaurant> getRestaurants() {
        return repository.findAll();
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public void removeRestaurant(Long id) {
        repository.deleteById(id);
    }

    public Map<String,List<String>> getRestaurantsByOwner() {
        var a = repository.getRestaurantsByOwner();
        List<Map<String, String>> restaurants = new ArrayList<>();
        for (var s : a) {
            Map<String, String> temp = new HashMap<>();
            temp.put("owner", s.split(",")[0]);
            temp.put("name", s.split(",")[1]);
            restaurants.add(temp);
        }
        var restaurantsByOwner = restaurants.stream().collect(Collectors
                .groupingBy(r -> r.get("owner"),
                        Collectors.mapping(restaurant -> restaurant.get("name"), Collectors.toList())));
        restaurantsByOwner.forEach((owner, restaurantList) ->
                System.out.println(owner + " owns: " + String.join(", ", restaurantList)));
        return restaurantsByOwner;
    }
}
