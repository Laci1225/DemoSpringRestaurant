package com.example.demoSpringRestaurant.persistance.entity;

import com.example.demoSpringRestaurant.constant.DrinkType;
import com.example.demoSpringRestaurant.constant.MealType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.example.demoSpringRestaurant.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @SequenceGenerator(name = "order", sequenceName = "order_sequence", initialValue = 1)
    @GeneratedValue(generator = "order_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    //@NotNull
    @ManyToOne
    private RestaurantEntity restaurant;

    @Enumerated(EnumType.STRING)
    //@Nullable
    private MealType mealType;

    //@Nullable
    @Enumerated(EnumType.STRING)
    private DrinkType drinkType;
    @Transient
    private double price;

    @NotNull
    private String deliveryAddress;

    @Transient
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus orderStatus = OrderStatus.SENT;

    private LocalTime estimatedDeliveryTime;

    //TODO new fields

    public double getPrice() {
        return getMealType().getValue() + getDrinkType().getValue();
    }

}
