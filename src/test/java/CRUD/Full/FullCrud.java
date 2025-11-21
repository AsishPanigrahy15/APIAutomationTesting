package CRUD.Full;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

public class FullCrud {

    String token;
    Integer bookingId;

    RequestSpecification requestSpecification = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    String BASE_URL = "https://restful-booker.herokuapp.com";
    String basePath;
    String payload;

    @BeforeTest
    public void getAToken(){

        basePath = "/auth";
        payload = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
        //Given
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(basePath);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(payload);

        //When
        response = requestSpecification.when().post();

        //Then
        validatableResponse = response.then();
        validatableResponse.statusCode(200);

        //Extract the token from this
        token = response.then().log().all().extract().path("token");
        Assert.assertNotNull(token, "Token is null");
    }

    @BeforeTest
    public void getBookingId(){

        basePath = "/booking";
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
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(basePath);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(payload);

        //When
        response = requestSpecification.when().post();

        //Then
        validatableResponse = response.then();
        validatableResponse.statusCode(200);

        //Extract the bookingId from this
        bookingId = response.then().log().all().extract().path("bookingid");
        System.out.println(bookingId);
        Assert.assertNotNull(token, "bookingId is null");

        String firstName = response.then().log().all().extract().path("booking.firstname");
        System.out.println(firstName);
    }

    @Test
    public void testPutRequest(){

        basePath = "/booking" + "/" + bookingId;
        payload = "{\n" +
                "    \"firstname\" : \"John\",\n" +
                "    \"lastname\" : \"Cena\",\n" +
                "    \"totalprice\" : 123,\n" +
                "    \"depositpaid\" : false,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2019-01-01\",\n" +
                "        \"checkout\" : \"2020-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Lunch\"\n" +
                "}";

        //Given
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(basePath);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token", token);
        requestSpecification.body(payload).log().all();

        //When - PUT request
        response = requestSpecification.when().put();

        //Then
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        // 3 ways You can verify the response

        String fullResponseJsonString = response.asString();
        System.out.println(fullResponseJsonString);

        //1. Rest Assured - Matchers
        validatableResponse.body("firstname", Matchers.equalTo("John"));
        validatableResponse.body("lastname", Matchers.equalTo("Cena"));

        //2. TestNg Asserts
        String firstNameResponse = response.then().log().all().extract().path("firstname");
        Assert.assertEquals(firstNameResponse, "John");

        String lastNameResponse = response.then().log().all().extract().path("lastname");
        Assert.assertEquals(lastNameResponse, "Cena");

        //3. TestNg Assertion with JSON path library - Mostly Used
        JsonPath jsonPath = new JsonPath(fullResponseJsonString);
        String firstNameJsonPathExtracted = jsonPath.getString("firstname");
        String lastNameJsonPathExtracted = jsonPath.getString("lastname");
        String totalPriceJsonPathExtracted = jsonPath.getString("totalprice");
        String checkInDateJsonPathExtracted = jsonPath.getString("bookingdates.checkin");

        Assert.assertEquals(firstNameJsonPathExtracted, "John");
        Assert.assertEquals(lastNameJsonPathExtracted, "Cena");
        Assert.assertEquals(totalPriceJsonPathExtracted, "123");
        Assert.assertEquals(checkInDateJsonPathExtracted, "2019-01-01");

        //4. AssertJ Matching
        assertThat(firstNameJsonPathExtracted)
                .isEqualTo("Jhon")
                .isNotBlank().isNotEmpty();

        assertThat(totalPriceJsonPathExtracted)
                .isNotNull()
                .isNotBlank();
    }
}
