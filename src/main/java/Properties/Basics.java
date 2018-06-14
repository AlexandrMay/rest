package Properties;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Basics extends ReusableMethods {

    Properties props = new Properties();

@BeforeTest
    public void getData() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/java/Properties/env.properties");
        props.load(fis);
    }
@Test
    public void Test() {

        RestAssured.baseURI = props.getProperty("HOST");
        Response res = given().header("Authorization", "Key " + apiKey(props.getProperty("siteKey"))).
                header("Content-Type", "application/json").
                body("{\"phone\":\"+380664853393\"}").
                when().
                put("/v1/office/registration.code").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).log().body().
                and().body("success", equalTo(true)).
                and().header("Server", "nginx").
                extract().response();
        JsonPath js = rawToJson(res);
        int code = js.get("code");

        given().
                header("Authorization", "Key " + apiKey(props.getProperty("siteKey"))).
                header("Content-Type", "application/json").
                body("{\"phone\":\"+380664853393\", \"code\":"+code+"}").
                when().
                put("/v1/office/registration.confirm").
                then().assertThat().statusCode(200).log().body();
    }
}
