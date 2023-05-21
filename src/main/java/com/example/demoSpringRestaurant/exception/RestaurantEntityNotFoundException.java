package com.example.demoSpringRestaurant.exception;

public class RestaurantEntityNotFoundException extends EntityNotFoundException{
    public RestaurantEntityNotFoundException(String message) {
        super(message);
    }
}
