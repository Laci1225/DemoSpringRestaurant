package com.example.demoSpringRestaurant.persistance.repository;

import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT count(r1) FROM RestaurantEntity r1 where r1.id = :restaurantId")
    Integer numberOfRestaurantFound(Long restaurantId);
    // TODO not in a good place

    List<OrderEntity> findAllByRestaurantId(Long restaurantId);
}
