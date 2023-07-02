package com.example.demoSpringRestaurant.persistance.document;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;


@Data
@Document(collection = "guest")
public class GuestDocument {

    @Id
    private String id;
    @DocumentReference
    private OrderDocument activeOrder;

    private boolean payed;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
