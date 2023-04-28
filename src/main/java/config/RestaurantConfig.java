package config;

import com.example.demoSpringRestaurant.Restaurant;
import com.example.demoSpringRestaurant.RestaurantRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ComponentScan("com.example.demoSpringRestaurant")
//@ComponentScan("restaurant")
@Configuration
public class RestaurantConfig {
    @Bean
    CommandLineRunner commandLineRunner(RestaurantRepository repository) {
        return args -> {
            Restaurant r1 = new Restaurant(
                    "Don Pedro",
                    "Kis József",
                    "Debrecen Kossuth utca 6.",
                    "DonPedro@gmail.com",
                    "06221156475",
                    25,
                    false,
                    true
            );
            Restaurant r2 = new Restaurant(
                    "Don Santos",
                    "Kis Elemér",
                    "Budapest Kossuth körút 564.",
                    "Santos@gmail.com",
                    "0645843342",
                    44,
                    true,
                    true
            );
            repository.saveAll(List.of(r1, r2));
        };
    }

}
