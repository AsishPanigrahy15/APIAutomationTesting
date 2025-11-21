package CRUD.POST;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class PostNonBddStyle {

    RequestSpecification rs = RestAssured.given();

    String BASE_URL = "https://restful-booker.herokuapp.com";
    String BASE_PATH = "/booking";
    String payload;

    @Description("TC#1 - Verify that Create Booking is working with valid payload")
    @Test
    public void postRequestPositive(){

        payload = "{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        //Given
        rs.baseUri(BASE_URL);
        rs.basePath(BASE_PATH).log().all();
        rs.contentType(ContentType.JSON).log().all();
        rs.body(payload);

        //When
        Response postResponse =  rs.when().log().all().post();
        String response = postResponse.asString();
        System.out.println(response);

        //Then
        ValidatableResponse validatableResponse = postResponse.then();
        validatableResponse.statusCode(200);
    }

    @Description("TC#2 - Verify that Create Booking is not working with 500 error")
    @Test
    public void postRequestNegative(){

        payload = "";

        //Given
        rs.baseUri(BASE_URL);
        rs.basePath(BASE_PATH).log().all();
        rs.contentType(ContentType.JSON).log().all();
        rs.body(payload);

        //When
        Response postResponse =  rs.when().log().all().post();
        String response = postResponse.asString();
        System.out.println(response);

        //Then
        ValidatableResponse validatableResponse = postResponse.then();
        validatableResponse.statusCode(500);
    }
}
