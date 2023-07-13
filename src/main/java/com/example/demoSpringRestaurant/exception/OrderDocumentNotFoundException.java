package com.example.demoSpringRestaurant.exception;

public class OrderDocumentNotFoundException extends DocumentNotFoundException {
    public OrderDocumentNotFoundException(String message) {
        super(message);
    }
}
