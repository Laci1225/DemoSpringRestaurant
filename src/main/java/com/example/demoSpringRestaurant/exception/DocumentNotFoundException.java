package com.example.demoSpringRestaurant.exception;

public class DocumentNotFoundException extends Exception{
    public DocumentNotFoundException(String message){
        super(message);
    }
}
