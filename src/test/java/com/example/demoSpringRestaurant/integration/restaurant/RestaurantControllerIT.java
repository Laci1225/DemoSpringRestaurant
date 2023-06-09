package com.example.demoSpringRestaurant.integration.restaurant;


import com.example.demoSpringRestaurant.exception.RestaurantEntityNotFoundException;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import com.example.demoSpringRestaurant.model.RestaurantDto;
import com.example.demoSpringRestaurant.persistance.repository.RestaurantRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
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

import java.util.Arrays;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
    void createRestaurantShouldRespondWithOneCreatedRestaurant() {
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
    void createRestaurantShouldRespondWithBadRequestInCaseOfWrongBody() {
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
                .body("size()", is(0));
    }

    @Test
    void getRestaurantShouldReturnOneRestaurant() {
        var expectedResult = RestaurantFixture.getRestaurantDtoList();
        restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));
        var response = Arrays.asList(given()
                .when().get("/restaurants")
                .then().statusCode(HttpStatus.OK.value())
                .body("size()", is(1))
                .extract().as(RestaurantDto[].class));
        assertThat(response).isEqualTo(expectedResult);
    }
    @Test
    void getRestaurantShouldReturnTwoRestaurant() {
        var expectedResult = RestaurantFixture.getRestaurantDtoList();
        expectedResult.add(RestaurantFixture.getRestaurantDto(2L));
        restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));
        restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));
        var response = Arrays.asList(given()
                .when().get("/restaurants")
                .then().statusCode(HttpStatus.OK.value())
                .body("size()", is(2))
                .extract().as(RestaurantDto[].class));
        assertThat(response).isEqualTo(expectedResult);
    }

    @Test
    void deleteRestaurantShouldRemoveGivenRestaurant() {
        var expectedResult = RestaurantFixture.getRestaurantDto();
        restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));

        var response = given()
                .when().delete("/restaurants/" + expectedResult.getId())
                .then().statusCode(HttpStatus.ACCEPTED.value())
                .body("id", notNullValue())
                .extract().as(RestaurantDto.class);

        expectedResult.setId(response.getId());
        assertThat(response).isEqualTo(expectedResult);
    }

    @Test
    void deleteRestaurantShouldRespondWithNotFound() {
        var expectedResult = new RestaurantEntityNotFoundException("Restaurant not found");

        var response = given()
                .when().delete("/restaurants/" + new Random().nextInt(1, 10))
                .then().statusCode(HttpStatus.NOT_FOUND.value())
                .extract().response();

        String message = JsonPath.from(response.getBody().asString()).getString("message");
        assertThat(message).isEqualTo(expectedResult.getMessage());
    }

    @Test
    void updateRestaurantShouldUpdateOneRestaurant() {
        var expectedResult = RestaurantFixture.getRestaurantDto();
        restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));

        var response = given().body(RestaurantFixture.getUpdatedRestaurantUpdateDto())
                .contentType(ContentType.JSON)
                .when().put("/restaurants/" + expectedResult.getId())
                .then().statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .extract().as(RestaurantDto.class);

        expectedResult.setId(response.getId());
        assertThat(response).isNotEqualTo(expectedResult);
        assertThat(response.getOwner()).isEqualTo(expectedResult.getOwner());
    }

    @Test
    void updateRestaurantShouldRespondWithNotFound() {
        given().body(RestaurantFixture.getUpdatedRestaurantUpdateDto())
                .contentType(ContentType.JSON)
                .when().put("/restaurants/" + new Random().nextInt(1, 10)) // TODO if not with an actual value then with what
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void updateRestaurantShouldRespondWithBadRequest() {
        given().body(OrderFixture.getOrderCreationDto())
                .contentType(ContentType.JSON)
                .when().put("/restaurants/" + new Random().nextInt(1, 10))
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void updateParametersInRestaurantShouldUpdateOneRestaurantsParameter() {
        var expectedResult = RestaurantFixture.getRestaurantDto();
        restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));

        var response = given().body(RestaurantFixture.getUpdatedRestaurantUpdateDto())
                .contentType(ContentType.JSON)
                .when().patch("/restaurants/" + expectedResult.getId())
                .then().statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .extract().as(RestaurantDto.class);

        expectedResult.setId(response.getId());
        assertThat(response).isNotEqualTo(expectedResult);
        assertThat(response.getOwner()).isEqualTo(expectedResult.getOwner());
    }

    @Test
    void updateParametersInRestaurantShouldRespondWithNotFound() {
        given().body(RestaurantFixture.getUpdatedRestaurantUpdateDto())
                .contentType(ContentType.JSON)
                .when().patch("/restaurants/" + new Random().nextInt(1, 10))
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void updateParametersInRestaurantShouldRespondWithBadRequest() {
        given().body(OrderFixture.getOrderCreationDto())
                .contentType(ContentType.JSON)
                .when().patch("/restaurants/" + new Random().nextInt(1, 10))
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void getVeganRestaurantsShouldReturnZero() {
        restaurantRepository.save(RestaurantFixture.getRestaurantEntity(false));
        given()
                .when().get("/restaurants/vegan")
                .then().statusCode(HttpStatus.OK.value())
                .body("size()", is(0));
    }

    @Test
    void getVeganRestaurantsShouldReturnOne() {
        restaurantRepository.save(RestaurantFixture.getRestaurantEntityIsVegan(false));
        given()
                .when().get("/restaurants/vegan")
                .then().statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }


}
