package com.example.demoSpringRestaurant.persistance.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Data
public class GuestDocument {

    @Id
    private String id;
    @DocumentReference
    private OrderDocument activeOrder;

    private boolean payed;
}
