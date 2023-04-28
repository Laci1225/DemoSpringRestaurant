package config;

import com.example.demoSpringRestaurant.Restaurant;
import com.example.demoSpringRestaurant.RestaurantRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ComponentScan({"repository","com.example.demoSpringRestaurant"})
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
                    "Don Angels",
                    "Kis Elemér",
                    "Budapest Kossuth körút 564.",
                    "Angels@gmail.com",
                    "0645843342",
                    44,
                    true,
                    true
            );
            Restaurant r3 = new Restaurant(
                    "Don Rower",
                    "Kis Elemér",
                    "Szeged Kossuth körút 564.",
                    "Rower@gmail.com",
                    "0645843342",
                    44,
                    true,
                    true
            );
            Restaurant r4 = new Restaurant(
                    "Don York",
                    "Kis Elemér",
                    "Pécs Kossuth körút 564.",
                    "York@gmail.com",
                    "0645843342",
                    44,
                    false,
                    false
            );
            Restaurant r5 = new Restaurant(
                    "Don Vienna",
                    "Kis József",
                    "Győr Kossuth körút 564.",
                    "Vienna@gmail.com",
                    "0645843342",
                    44,
                    false,
                    true
            );
            Restaurant r6 = new Restaurant(
                    "Don Pedro",
                    "Kis József",
                    "Debrecen Kossuth utca 6.",
                    "DonPedro@gmail.com",
                    "06221156475",
                    25,
                    false,
                    true
            );
            repository.saveAll(List.of(r1, r2,r3,r4,r5,r6));
        };
    }

}
