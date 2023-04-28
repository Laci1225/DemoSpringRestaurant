package service;

import com.example.demoSpringRestaurant.Restaurant;
import com.example.demoSpringRestaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@ComponentScan({"com.example.demoSpringRestaurant"})//,"repository"})
@Service
public class RestaurantService {

    public final RestaurantRepository repository;
    //public final Restaurant restaurant;

    @Autowired
    public RestaurantService(RestaurantRepository repository){//, Restaurant restaurant) {
        //this.restaurant = new Restaurant();
        this.repository = repository;
    }


    public List<Restaurant> getRestaurants() {
        return repository.findAll();
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return repository.save(restaurant);
    }
}
