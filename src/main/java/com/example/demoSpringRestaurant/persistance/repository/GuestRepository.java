package com.example.demoSpringRestaurant.persistance.repository;

import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends MongoRepository<GuestDocument, String> {
    Optional<GuestDocument> findGuestDocumentByActiveOrder_Id(String activeOrder_id);
}
