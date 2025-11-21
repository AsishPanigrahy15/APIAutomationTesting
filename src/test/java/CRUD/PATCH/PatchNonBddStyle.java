package CRUD.PATCH;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class PatchNonBddStyle {

    RequestSpecification rs = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    String BASE_URL = "https://restful-booker.herokuapp.com";
    String BASE_PATH = "/booking";

    String token = "81749ae41a4d69a";
    String bookingId = "816";

    String payload;


    @Description("TC#1 - Verify that Create Booking is working with valid payload")
    @Test
    public void patchRequestPositive(){

        String BASE_PATH_UPDATED = BASE_PATH + "/" +bookingId;
        System.out.println(BASE_PATH_UPDATED);

        payload = "{\n" +
                "    \"firstname\" : \"Happy\",\n" +
                "    \"lastname\" : \"Panigrahy\"\n" +
                "}";

        //Given
        rs.baseUri(BASE_URL);
        rs.basePath(BASE_PATH_UPDATED).log().all();
        rs.contentType(ContentType.JSON).log().all();
        rs.cookie("token", token);
        rs.body(payload).log().all();

        //When
        response =  rs.when().log().all().patch();
        String resp = response.asString();
        System.out.println(resp);

        //Then
        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.body("firstname", Matchers.equalTo("Happy"));
        validatableResponse.body("lastname", Matchers.equalTo("Panigrahy"));
    }
}
