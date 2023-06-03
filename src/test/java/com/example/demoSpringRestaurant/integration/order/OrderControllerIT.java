package com.example.demoSpringRestaurant.integration.order;

import com.example.demoSpringRestaurant.constant.OrderStatus;
import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.persistance.repository.OrderRepository;
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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@TestPropertySource(locations = "classpath:application-integration.properties")
public class OrderControllerIT {


    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMapper orderMapper;
    @LocalServerPort
    private int givenPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = givenPort;
    }

    @Test
    void getOrdersByRestaurantIdShouldReturnOneOrder() {
        var restaurant = restaurantRepository.save(RestaurantFixture.getRestaurantEntity(true));
        orderRepository.save(OrderFixture.getOrderEntityToGivenRestaurant(true,restaurant));

        given()
                .when().get("/orders/1")
                .then().statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

    @Test
    void getOrdersByRestaurantIdShouldReturnZeroOrder() {
        given()
                .when().get("/orders/1")
                .then().statusCode(HttpStatus.OK.value())
                .body("size()", is(0));
    }

    @Test
    void createOrderShouldRespondWithOneCreatedOrder() {
        var restaurant = restaurantRepository.save(RestaurantFixture.getRestaurantEntity(true));
        var expectedResult = OrderFixture.getOrderDtoToGivenRestaurant(restaurant);

        var response = given().body(OrderFixture.getOrderCreationDto())
                .contentType(ContentType.JSON)
                .when().post("/orders/1")
                .then().statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .extract().as(OrderDto.class);

        expectedResult.setCreateDate(response.getCreateDate());
        expectedResult.setId(response.getId());
        assertThat(response).isEqualTo(expectedResult);
    }

    @Test
    void createOrderShouldRespondWithNotFound() {
        given().body(OrderFixture.getOrderCreationDto())
                .contentType(ContentType.JSON)
                .when().post("/orders/1")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void createOrderShouldRespondWithBadRequest() {
        given().body(RestaurantFixture.getRestaurantCreationDto())
                .contentType(ContentType.JSON)
                .when().post("/orders/1")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deleteOrderShouldRemoveGivenOrder() {
        var restaurant = restaurantRepository.save(RestaurantFixture.getRestaurantEntity(true));
        var a = orderRepository.save(OrderFixture.getOrderEntityToGivenRestaurant(true,restaurant));
        var expectedResult = OrderFixture.getOrderDtoToGivenRestaurant(restaurant);

        var response = given()
                .when().delete("/orders/1")
                .then().statusCode(HttpStatus.ACCEPTED.value())
                .body("id", is(1)) //TODO itt kivel kell hasonlítani miért nem a.getId tehát 1L
                .extract().as(OrderDto.class);

        expectedResult.setCreateDate(response.getCreateDate());
        expectedResult.setId(response.getId());
        assertThat(response).isEqualTo(expectedResult);
    }

    @Test
    void deleteOrderShouldRespondWithNotFound() {
        given()
                .when().delete("/orders/1")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void setNextStateFromSentToApproved() {
        var restaurant = restaurantRepository.save(RestaurantFixture.getRestaurantEntity(true));
        var a = orderRepository.save(OrderFixture.getOrderEntityToGivenRestaurant(true,restaurant));
        var expectedResult = OrderFixture.getOrderDtoGetNextStatusToGivenRestaurant(OrderStatus.SENT,restaurant);

        var response = given()
                .when().post("/orders/1/next-state")
                .then().statusCode(HttpStatus.OK.value())
                .body("id", is(1)) //TODO a.getID nem jó miért itt kivel kell hasonlítani
                .extract().as(OrderDto.class);

        expectedResult.setCreateDate(response.getCreateDate());
        expectedResult.setId(response.getId());
        assertThat(response).isEqualTo(expectedResult);
    }
}
