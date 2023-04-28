package com.example.demoSpringRestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"config"})
public class DemoSpringRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringRestaurantApplication.class, args);
	}

}
