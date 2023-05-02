package com.example.demoSpringRestaurant.persistance.repository;

import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
}
