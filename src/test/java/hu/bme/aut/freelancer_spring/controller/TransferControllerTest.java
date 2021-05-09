package hu.bme.aut.freelancer_spring.controller;

import hu.bme.aut.freelancer_spring.model.Package;
import hu.bme.aut.freelancer_spring.model.Transfer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("dev")
class TransferControllerTest {

    @LocalServerPort
    protected int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "api";
    }

    @Test
    void findAllTest() {
        // Arrange
        List<Transfer> expected = Arrays.asList(
                new Transfer(null, 23.34, 34.546),
                new Transfer(null, 13.34, 344.546),
                new Transfer(null, 543.34, 344.546),
                new Transfer(null, 237.34, 364.546)
        );

        // Act
        Transfer[] result = given()
                .when().get("/transfers")
                .then().statusCode(is(200))
                .extract().as(Transfer[].class);

        // Assert
        List<Transfer> resultList = Arrays.asList(result);

        assertEquals(4, resultList.size());
        assertThat(resultList)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }

    @Test
    void deleteTest() {
        // Act
        var initialTransfers = given()
                .when().get("/transfers")
                .then().statusCode(is(200))
                .extract().as(Transfer[].class);

        var result = given()
                .when().delete("/transfers/1")
                .then().statusCode(is(200))
                .extract().as(Boolean.class);

        var afterDeleteTransfers = given()
                .when().get("/transfers")
                .then().statusCode(is(200))
                .extract().as(Transfer[].class);

        // Assert
        assertThat(afterDeleteTransfers).isNotEqualTo(initialTransfers);
        assertEquals(afterDeleteTransfers.length, initialTransfers.length - 1);
        assertEquals(true, result);
    }

    @Test
    void getPackages() {
        // Arrange
        var expected = Arrays.asList(
                new Package("elso", 1, 2),
                new Package("masodik", 10, 210),
                new Package("drage", 1100, 21)
        );

        // Act
        var result = given()
                .when().get("/transfers/packages/1")
                .then().statusCode(is(200))
                .extract().as(Package[].class);

        var resultList = Arrays.asList(result);

        // Assert
        assertEquals(3, resultList.size());
        assertThat(resultList)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }
}
