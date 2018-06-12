import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Basics extends Caps {


    @BeforeTest
    public void getData() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/java/Files/env.properties");
        props.load(fis);
    }

    @Test
    public void Test() {

        RestAssured.baseURI = host;
        Response res = given().
                header("Authorization", "Key " + apiKey(props.getProperty("siteKey"))).
                header("Content-Type", "application/json").
                body("{\"phone\":\"+380664853393\"}").
                when().
                put("/v1/office/registration.code").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
                and().body("success", equalTo(true)).
                and().header("Server", "nginx").
                extract().response();
        String responseString = res.asString();
        System.out.println(responseString);
        JsonPath js = new JsonPath(responseString);
        int code = js.get("code");

        given().
                header("Authorization", "Key " + apiKey(props.getProperty("siteKey"))).
                header("Content-Type", "application/json").
                body("{\"phone\":\"+380664853393\", \"code\":"+code+"}").
                when().
                put("/v1/office/registration.confirm").
                then().assertThat().statusCode(200);
    }
}
