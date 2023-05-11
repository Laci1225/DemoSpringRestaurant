package com.example.demoSpringRestaurant.constant;

public enum OrderStatus {
    SENT,
    APPROVED,
    SHIPPING,
    SHIPPED;

    public OrderStatus getNextStatus() {
        return switch (this) {
            case SENT -> APPROVED;
            case APPROVED -> SHIPPING;
            case SHIPPING, SHIPPED -> SHIPPED;
        };
    }
}
