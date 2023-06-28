package com.example.demoSpringRestaurant.persistance.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Data
@Document(collection = "guest")
public class GuestDocument {

    @Id
    private String id;
    @DocumentReference
    private OrderDocument activeOrder;

    private boolean payed;
}
