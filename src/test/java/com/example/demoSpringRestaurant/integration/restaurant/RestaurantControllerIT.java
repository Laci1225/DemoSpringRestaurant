package com.example.demoSpringRestaurant.integration.restaurant;


import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@TestPropertySource(locations = "classpath:application-integration.properties")
public class RestaurantControllerIT {

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    RestaurantMapper restaurantMapper;
    @LocalServerPort
    private int givenPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = givenPort;
    }

    @Test
    void addRestaurantShouldRespondWithTheCreatedRestaurant() { //TODO nevezék
        var expectedResult = RestaurantFixture.getRestaurantDto();
        var response = given().body(RestaurantFixture.getRestaurantCreationDto())
                .contentType(ContentType.JSON)
                .when().post("/restaurants")
                .then().statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .extract().as(RestaurantDto.class);

        expectedResult.setId(response.getId());
        assertThat(response).isEqualTo(expectedResult);
    }

    @Test
    void addRestaurantShouldRespondWithBadRequestInCaseOfWrongBody() { //TODO nevezék
        given().body(OrderFixture.getOrderDto())
                .contentType(ContentType.JSON)
                .when().post("/restaurants")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void getRestaurantShouldReturnZeroRestaurant() {
        given()
                .when().get("/restaurants")
                .then().statusCode(HttpStatus.OK.value())
                .body("size()",is(0));
    }

    @Test
    void getRestaurantShouldReturnOneRestaurant() {
        var result = restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));
        given()
                .when().get("/restaurants")
                .then().statusCode(HttpStatus.OK.value())
                .body("size()",is(1));
        //TODO tömb visszaellenőrzés stringbe mi
                //.body("",hasItem(restaurantMapper.fromEntityToRestaurantDto(result)));

    }
    @Test
    void removeRestaurantShouldRemoveGivenRestaurant() {
        var expectedResult = RestaurantFixture.getRestaurantDto();
        restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));

        var response = given()
                .when().delete("/restaurants/1")
                .then().statusCode(HttpStatus.ACCEPTED.value())
                .body("id", notNullValue())
                .extract().as(RestaurantDto.class);

        expectedResult.setId(response.getId());
        assertThat(response).isEqualTo(expectedResult);
    }

    @Test
    void updateRestaurant() {
        var expectedResult = RestaurantFixture.getRestaurantDto();
        restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));

        var response = given().body(RestaurantFixture.getUpdatedRestaurantUpdateDto())
                .contentType(ContentType.JSON)
                .when().put("/restaurants/1")
                .then().statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .extract().as(RestaurantDto.class);

        expectedResult.setId(response.getId());
        assertThat(response).isNotEqualTo(expectedResult);
        assertThat(response.getOwner()).isEqualTo(expectedResult.getOwner());
    }

    @Test
    void updateParametersInRestaurant() {
        var expectedResult = RestaurantFixture.getRestaurantDto();
        restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));

        var response = given().body(RestaurantFixture.getUpdatedRestaurantUpdateDto())
                .contentType(ContentType.JSON)
                .when().patch("/restaurants/1")
                .then().statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .extract().as(RestaurantDto.class);

        expectedResult.setId(response.getId());
        assertThat(response).isNotEqualTo(expectedResult);
        assertThat(response.getOwner()).isEqualTo(expectedResult.getOwner());
    }

    @Test
    void getVeganRestaurantsShouldReturnZero() {
        var result = restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));
        given()
                .when().get("/restaurants/vegan")
                .then().statusCode(HttpStatus.OK.value())
                .body("size()",is(0));
    }
    //TODO error throwokat is megnézni
    @Test
    void getVeganRestaurantsShouldReturnOne() {
        var result = restaurantRepository.save(RestaurantFixture.getRestaurantEntityIsVegan(false));
        given()
                .when().get("/restaurants/vegan")
                .then().statusCode(HttpStatus.OK.value())
                .body("size()",is(1));
    }


}
