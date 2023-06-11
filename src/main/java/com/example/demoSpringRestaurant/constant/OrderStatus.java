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
            case SHIPPING -> SHIPPED;
            case SHIPPED ->
                    throw new UnsupportedOperationException("Invalid operation: Next status not available after SHIPPED");
            default -> throw new UnsupportedOperationException("Invalid operation");
        };
    }
}
