package com.example.demoSpringRestaurant.exception;

public class CourierDocumentNotFoundException extends DocumentNotFoundException{
    public CourierDocumentNotFoundException(String message) {
        super(message);
    }
}
