package com.example.demoSpringRestaurant;

import com.example.demoSpringRestaurant.Restaurant;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@ComponentScan("restaurant")
@ComponentScan({"config"})//,"com.example.demoSpringRestaurant"})
//@ComponentScan("controller")
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

}
