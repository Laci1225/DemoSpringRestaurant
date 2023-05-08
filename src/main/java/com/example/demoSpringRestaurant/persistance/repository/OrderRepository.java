package com.example.demoSpringRestaurant.persistance.repository;

import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    @Query("select o from OrderEntity o where o.restaurantId = :orderId")
    List<OrderEntity> findAllById(@Param("orderId") Long id);

    @Query("select o from OrderEntity o where o.restaurantId = :orderId")
    List<OrderEntity> getOrdersByRestaurantId(@Param("orderId") Long id);
}
