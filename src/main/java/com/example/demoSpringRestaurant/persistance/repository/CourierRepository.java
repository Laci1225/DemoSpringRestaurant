package com.example.demoSpringRestaurant.persistance.repository;

import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends MongoRepository<CourierDocument, String> {
}
