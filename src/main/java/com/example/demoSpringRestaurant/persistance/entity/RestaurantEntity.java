package com.example.demoSpringRestaurant.persistance.entity;

import jakarta.annotation.Nullable;
//import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;


//@Entity
//@Table(name = "restaurants")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RestaurantEntity {
    @Id
    /*@SequenceGenerator(name = "restaurant", sequenceName = "restaurant_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_sequence")*/
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String owner;
    private String address;
    @Email
    @Indexed(unique = true)
    private String email;
    @Nullable
    private String phoneNumber = null;
    @Nullable
    private Integer numberOfTables = null;
    @Nullable
    private Boolean isVegan = false;
    @NotNull
    private Boolean canDeliver;

     @NotNull
     private Boolean isOnWolt;

    // @OneToMany private List<OrderEntity> orderEntity;
}