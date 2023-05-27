package com.example.demoSpringRestaurant.exception;

public class OrderEntityNotFoundException extends EntityNotFoundException{
    public OrderEntityNotFoundException(String message) {
        super(message);
    }
}
