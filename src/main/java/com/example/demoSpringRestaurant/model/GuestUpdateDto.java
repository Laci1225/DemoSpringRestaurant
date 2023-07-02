package com.example.demoSpringRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GuestUpdateDto {
    private OrderDto activeOrder;
    private boolean payed;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public GuestUpdateDto() {
    }
}
