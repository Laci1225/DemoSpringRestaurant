package com.example.demoSpringRestaurant;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@ComponentScan("restaurant")
@ComponentScan("config")
//@ComponentScan("controller")
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

}
