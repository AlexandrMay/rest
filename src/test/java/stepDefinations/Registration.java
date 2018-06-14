package stepDefinations;

import Properties.ReusableMethods;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Registration extends ReusableMethods {

    Properties props = new Properties();
    private Response response;
    private RequestSpecification request;
    private Response json;



    @Before
    public void getData() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/java/Properties/env.properties");
        props.load(fis);
        RestAssured.baseURI = props.getProperty("HOST");
    }

    @Given("^Request is prepared and body contains (.+) and (.+)$")
    public void request_is_prepared(String key, String value) {
        request = given().header("Authorization", "Key " + apiKey(props.getProperty("siteKey"))).
                header("Content-Type", "application/json").body("{"+key+":"+value+"}");
    }

    @When("^I send the request with (.+)$")
    public void i_send_the_request_with(String resource) {
        response = request.when().put(resource);
        System.out.println(response.prettyPrint());
    }

    @Then("^I have statusCode (.+)$")
    public void i_have_statuscode(int statuscode) {
        json = response.then().assertThat().statusCode(statuscode).extract().response();
    }

}
