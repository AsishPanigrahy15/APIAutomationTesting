package org.example;

import io.restassured.RestAssured;

public class Lab01 {
    public static void main(String[] args) {

        // Rest Assured first program
        RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/ping")
                .when().get().then().statusCode(201);
    }
}
