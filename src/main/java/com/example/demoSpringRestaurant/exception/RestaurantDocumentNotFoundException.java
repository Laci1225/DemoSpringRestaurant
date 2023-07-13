package com.example.demoSpringRestaurant.exception;

public class RestaurantDocumentNotFoundException extends DocumentNotFoundException {
    public RestaurantDocumentNotFoundException(String message) {
        super(message);
    }
}
