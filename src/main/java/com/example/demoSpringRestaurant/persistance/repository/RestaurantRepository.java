package com.example.demoSpringRestaurant.persistance.repository;

import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@ComponentScan("com.example.demoSpringRestaurant.restaurant")
//@ComponentScan({"com.example.demoSpringRestaurant.config"})//,"com.example.demoSpringRestaurant"})
//@ComponentScan("com.example.demoSpringRestaurant.controller")
@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    void deleteById(Long id);

    @Query("SELECT r1.owner, r2.name FROM RestaurantEntity r1 JOIN RestaurantEntity r2 ON r1.id = r2.id ORDER BY r1.owner")
    Optional<List<String>> getRestaurantsByOwner();

    //@Query("SELECT r FROM RestaurantEntity r where r.isVegan =true ")
    //Optional<List<RestaurantEntity>> getRestaurantsByIsVegan();

    @Query("SELECT r FROM RestaurantEntity r where r.isVegan = :isVegan")
    Optional<List<RestaurantEntity>> findAllWithFilters(@Param("isVegan") boolean isVegan);
}
