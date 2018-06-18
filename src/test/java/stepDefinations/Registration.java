package stepDefinations;

import Properties.ReusableMethods;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Registration extends ReusableMethods {

    Properties props = new Properties();
    private Response response;
    private RequestSpecification request;
    private ValidatableResponse json;
    private Logger log = LogManager.getLogger(Registration.class.getName());




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

    @Then(("^I have statusCode (.+) and (.+) equals to (.+)$"))
    public void i_have_statuscode_and_equals_to(int statuscode, String keyword, boolean operand){
        json = response.then().assertThat().statusCode(statuscode).contentType(ContentType.JSON).body(keyword, equalTo(operand));
        JsonPath js = rawToJson(json);
        int genCode = js.get("code");
    }

    @Given("^Request is prepared and body with (.+) and (.+) using (.+) with generated code$")
    public void request_is_prepared_and_body_with_and_using_and(String key, String value, String code) {

        request = given().header("Authorization", "Key " + apiKey(props.getProperty("siteKey"))).
                header("Content-Type", "application/json").body("{"+key+":"+value+", "+code+":"+123+"}");
    }

    @Then("^I expect statusCode (.+)$")
    public void i_expect_statuscode(int statuscode){
        json = response.then().assertThat().statusCode(statuscode);
    }


}
