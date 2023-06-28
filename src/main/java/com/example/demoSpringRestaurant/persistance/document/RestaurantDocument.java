package com.example.demoSpringRestaurant.persistance.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Builder
@Document(collection = "restaurants")
public class RestaurantDocument {

    @Id
    private String id;
    @DocumentReference(lazy = true, lookup = "{'orders' : ?#{#self.id}}")
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

    public RestaurantDocument() {
    }

    public RestaurantDocument(String id, List<OrderDocument> orders, String name, String owner, String address, String email, String phoneNumber, Integer numberOfTables, Boolean isVegan, Boolean canDeliver, Boolean isOnWolt) {
        this.id = id;
        this.orders = orders;
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.numberOfTables = numberOfTables;
        this.isVegan = isVegan;
        this.canDeliver = canDeliver;
        this.isOnWolt = isOnWolt;
    }
}
