package CRUD.GET;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class GetNonBddStyle {

    RequestSpecification rs = RestAssured.given();

    @Test
    public void getRequestPositive(){

        //Given
        rs.baseUri("https://restful-booker.herokuapp.com");
        rs.basePath("/booking/610").log().all();

        //When
        rs.when().get();

        //Then
        rs.then().log().all().statusCode(200);
    }

    @Test
    public void getRequestNegative(){

        //Given
        rs.baseUri("https://restful-booker.herokuapp.com");
        rs.basePath("/booking/1074").log().all();

        //When
        rs.when().get();

        //Then
        rs.then().log().all().statusCode(200);
    }
}
