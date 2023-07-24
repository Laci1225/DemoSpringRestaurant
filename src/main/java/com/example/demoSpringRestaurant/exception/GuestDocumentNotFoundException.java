package com.example.demoSpringRestaurant.exception;

public class GuestDocumentNotFoundException extends DocumentNotFoundException {
    public GuestDocumentNotFoundException(String message) {
        super(message);
    }
}
