package com.example.demoSpringRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GuestCreationDto {
    private OrderDto activeOrder;
    private boolean payed;

    public GuestCreationDto() {
    }
}
