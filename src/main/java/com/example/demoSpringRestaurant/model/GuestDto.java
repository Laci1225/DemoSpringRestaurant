package com.example.demoSpringRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
public class GuestDto {

    @Id
    private String id;
    private OrderDto activeOrder;
    private boolean payed;

    public GuestDto() {
    }
}
