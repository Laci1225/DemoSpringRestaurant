package com.example.demoSpringRestaurant.persistance.document;

import com.example.demoSpringRestaurant.constant.Vehicle;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
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
}
