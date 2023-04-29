package com.example.demoSpringRestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(scanBasePackages = {"com.example.demoSpringRestaurant.config", "com.example.demoSpringRestaurant.controller", "com.example.demoSpringRestaurant.service"})//,"com.example.demoSpringRestaurant.repository"})
public class DemoSpringRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringRestaurantApplication.class, args);
	}

}
