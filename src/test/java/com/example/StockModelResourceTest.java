package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class StockModelResourceTest {

    @Test
    public void testLiveDataEndpoint() {
        given()
                .when().get("/stock/stocks")
                .then()
                .statusCode(200)
                .body("stockName", is("test_1"));
    }

}
