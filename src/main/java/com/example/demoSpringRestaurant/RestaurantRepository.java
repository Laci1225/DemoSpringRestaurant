package com.example.demoSpringRestaurant;

import com.example.demoSpringRestaurant.Restaurant;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@ComponentScan("restaurant")
@ComponentScan({"config"})//,"com.example.demoSpringRestaurant"})
//@ComponentScan("controller")
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    void deleteById(Long id);

    @Query("SELECT r1.owner, r2.name FROM Restaurant r1 JOIN Restaurant r2 ON r1.id = r2.id ORDER BY r1.owner")
    Optional<List<String>> getRestaurantsByOwner();
}
