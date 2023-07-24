package com.example.demoSpringRestaurant.persistance.repository;

import com.example.demoSpringRestaurant.persistance.document.RestaurantDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends MongoRepository<RestaurantDocument, String> {

    /*@Deprecated
    @Query("SELECT r1.id,r1.owner, r2.name FROM RestaurantEntity r1 JOIN RestaurantEntity r2 ON r1.id = r2.id ORDER BY r1.owner")
    Optional<List<String>> getRestaurantsByOwner();*/

    List<RestaurantDocument> findAllByIsVeganTrue();
}
