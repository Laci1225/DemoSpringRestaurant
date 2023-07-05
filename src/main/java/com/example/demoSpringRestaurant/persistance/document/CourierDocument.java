package com.example.demoSpringRestaurant.persistance.document;

import com.example.demoSpringRestaurant.constant.Vehicle;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "courier")
public class CourierDocument {
    @Id
    private String id;
    private String name;
    private boolean isActive;
    @DocumentReference
    private List<OrderDocument> orders;
    @DocumentReference
    private OrderDocument activeOrder;

    private double paymentPerOrder;

    private Vehicle vehicle;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
