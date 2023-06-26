package com.example.demoSpringRestaurant.persistance.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Document(collection = "restaurants")
public class RestaurantDocument {

    @Id
    private String id;
    @DocumentReference(lazy = true,lookup = "{'orders' : ?#{#self.id}}")
    @ReadOnlyProperty
    private List<OrderDocument> orders;
    private String name;
    private String owner;
    private String address;
    private String email;
    private String phoneNumber;
    private Integer numberOfTables;
    private Boolean isVegan;
    private Boolean canDeliver;
    private Boolean isOnWolt;
}