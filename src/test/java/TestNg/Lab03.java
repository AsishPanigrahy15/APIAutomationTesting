package TestNg;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class Lab03 {
    @Description("TC#1 Verify Get Request 1 from Lab03")
    @Test
    public void getRequest(){
        RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/ping")
                .when().get()
                .then().statusCode(201);
    }

    @Description("TC#2 Verify Get Request 2 from Lab03")
    @Test
    public void getRequest1(){
        RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/ping")
                .when().get()
                .then().statusCode(201);
    }

    @Description("TC#3 Verify Get Request 3 from Lab03")
    @Test
    public void getRequest2(){
        RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/ping")
                .when().get()
                .then().statusCode(201);
    }
}
