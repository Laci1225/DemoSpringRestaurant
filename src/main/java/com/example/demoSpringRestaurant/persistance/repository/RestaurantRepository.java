package com.example.demoSpringRestaurant.persistance.repository;

import com.example.demoSpringRestaurant.persistance.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    @Deprecated
    @Query("SELECT r1.id,r1.owner, r2.name FROM RestaurantEntity r1 JOIN RestaurantEntity r2 ON r1.id = r2.id ORDER BY r1.owner")
    Optional<List<String>> getRestaurantsByOwner();
    List<RestaurantEntity> findAllByIsVeganTrue();
}
