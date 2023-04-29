package service;

import com.example.demoSpringRestaurant.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ComponentScan({"com.example.demoSpringRestaurant"})//,"repository"})
@Service
public class RestaurantService {

    public final RestaurantRepository repository;
    //public final Restaurant restaurant;
    public final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantService(RestaurantRepository repository, RestaurantMapper restaurantMapper) {//, Restaurant restaurant) {
        //this.restaurant = new Restaurant();
        this.repository = repository;
        this.restaurantMapper = restaurantMapper;
    }


    public List<RestaurantDto> getRestaurants() {
        return repository.findAll().stream().map(restaurantMapper::fromEntityToRestaurantDto).toList();
    }

    public Restaurant addRestaurant(RestaurantCretionDto restaurantCretionDto) {
       return repository.save(restaurantMapper.fromRestaurantCreationDtoToEntity(restaurantCretionDto));
    }

    public void removeRestaurant(Long id) {
        repository.deleteById(id);
    }

    public Map<String, List<String>> getRestaurantsByOwner() {
        /*var a = repository.getRestaurantsByOwner();
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
        return restaurantsByOwner;*/
        if (repository.getRestaurantsByOwner().isPresent())
            return repository.getRestaurantsByOwner().get().stream()
                    .map(s -> s.split(","))
                    .collect(Collectors.groupingBy(
                            arr -> arr[0],
                            Collectors.mapping(arr -> arr[1], Collectors.toList())));
        else
            throw new IllegalStateException();
    }


    @Transactional
    public void updateRestaurant(Long id, String owner, String name, String address, String email, String phoneNumber, Integer numberOfTables, Boolean isVegan, Boolean canDeliver) {
        var restaurant = repository.findById(id);
        if (restaurant.isPresent()) {
            if (owner != null)
                restaurant.get().setOwner(owner);
            if (name != null)
                restaurant.get().setName(name);
            if (address != null)
                restaurant.get().setAddress(address);
            if (email != null)
                restaurant.get().setEmail(email);
            if (phoneNumber != null)
                restaurant.get().setPhoneNumber(phoneNumber);
            if (numberOfTables != null)
                restaurant.get().setNumberOfTables(numberOfTables);
            if (isVegan != null)
                restaurant.get().setVegan(isVegan);
            if (canDeliver != null)
                restaurant.get().setCanDeliver(canDeliver);
        }
    }

    public List<RestaurantDto> getVeganRestaurants() {
        if (repository.getRestaurantsByOwner().isPresent())
            return repository.getRestaurantsByIsVegan().get().
                    stream().map(restaurantMapper::fromEntityToRestaurantDto).toList();
        else
            throw new IllegalStateException();

    }
}
