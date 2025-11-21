package CRUD.DELETE;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class DeleteNonBddStyle {

    RequestSpecification rs = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    String BASE_URL = "https://restful-booker.herokuapp.com";
    String BASE_PATH = "/booking";

    String token = "81749ae41a4d69a";
    String bookingId = "816";

    //String payload;


    @Description("TC#1 - Verify that Create Booking is working with valid payload")
    @Test
    public void deleteRequestPositive(){

        String BASE_PATH_UPDATED = BASE_PATH + "/" +bookingId;
        System.out.println(BASE_PATH_UPDATED);

        //Given
        rs.baseUri(BASE_URL);
        rs.basePath(BASE_PATH_UPDATED).log().all();
        rs.contentType(ContentType.JSON).log().all();
        rs.cookie("token", token);
        rs.log().all();

        //When
        response =  rs.when().log().all().delete();

        //Then
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);
    }
}
