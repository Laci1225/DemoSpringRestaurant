package com.example.demoSpringRestaurant.exception;

public abstract class EntityNotFoundException extends Exception{
    public EntityNotFoundException(String message){
        super(message);
    }
}
