package com.example.demoSpringRestaurant.exception;

public abstract class DocumentNotFoundException extends Exception{
    public DocumentNotFoundException(String message){
        super(message);
    }
}
