package CRUD.PUT;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class PutNonBddStyle {

    RequestSpecification rs = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    String BASE_URL = "https://restful-booker.herokuapp.com";
    String BASE_PATH = "/booking";

    String token = "f1a9a6c5d5ebf67";
    String bookingId = "236";

    String payload;


    @Description("TC#1 - Verify that Create Booking is working with valid payload")
    @Test
    public void putRequestPositive(){

        String BASE_PATH_UPDATED = BASE_PATH + "/" +bookingId;
        System.out.println(BASE_PATH_UPDATED);

        payload = "{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Pani\",\n" +
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
        rs.basePath(BASE_PATH_UPDATED).log().all();
        rs.contentType(ContentType.JSON).log().all();
        rs.cookie("token", token);
        rs.body(payload).log().all();

        //When
        response =  rs.when().log().all().put();
        String resp = response.asString();
        System.out.println(resp);

        //Then
        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.body("firstname", Matchers.equalTo("James"));
        validatableResponse.body("lastname", Matchers.equalTo("Pani"));
    }
}
