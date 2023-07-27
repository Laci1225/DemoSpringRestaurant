package com.example.demoSpringRestaurant.model.service;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantCreationDto {
    @NotNull
    private String name;
    @NotNull
    private String owner;
    private String address;
    @Email
    private String email;
    @Nullable
    private String phoneNumber;

    @Nullable
    private Integer numberOfTables;
    @Nullable
    private Boolean isVegan;
    @NotNull
    private Boolean canDeliver;
    @NotNull
    private Boolean isOnWolt;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
