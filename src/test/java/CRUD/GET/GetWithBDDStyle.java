package CRUD.GET;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class GetWithBDDStyle {

    //given
    //URL
    //Header? Cookies
    //Base Url
    //base path
    //Auth - Basic, Digest, JWT, OUATH2.0,Bearer Token

    //when
    //Payload - No/Yes, Json/Xml
    //Get method

    //Then
    //Response validation
    //Status code
    //Response body


    @Test
    public void getRequestPositive(){

        //Builder pattern - BP
        RestAssured
                .given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking/1074")

                .when().log().all().get()

                .then().log().all()
                .statusCode(200);
    }
}
