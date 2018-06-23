package stepDefinations;

import Properties.ReusableMethods;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;


public class Registration extends ReusableMethods {

    private StepData data;

    public Registration(StepData data) {
        this.data = data;
    }

    Properties props = new Properties();


    @Before
    public void getData() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/java/Properties/env.properties");
        props.load(fis);
        RestAssured.baseURI = props.getProperty("HOST");
    }

    @Given("^Request is prepared and body contains (.+) and (.+)$")
    public void request_is_prepared(String key, String value) {
        data.request = given().header("Authorization", "Key " + apiKey(props.getProperty("siteKey"))).
                header("Content-Type", "application/json").body("{"+key+":"+value+"}");
    }

    @When("^I send the request with (.+)$")
    public void i_send_the_request_with(String resource) {
        data.response = data.request.when().put(resource);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @When("^I send the new request with (.+)$")
    public void i_send_the_new_request_with(String resource) throws Throwable {
        data.response = data.request.when().put(resource);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }


    @And("^And (.+) equals to (.+)$")
    public void and_equals_to(String key, boolean value) throws Throwable {
           data.json.body(key, equalTo(value));
        data.js = rawToJson(data.json);
        data.newCode = data.js.get("code");
    }

    @And("^Response includes (.+) is (.+) and (.+) is (.+)$")
    public void response_includes_is_and_is(String code, int receivedcode, String message, String receivedmessage) throws Throwable {
        data.json.body(code, equalTo(receivedcode)).and().body(message, equalTo(receivedmessage));
    }

    @Given("^Request is prepared and body with (.+) and (.+) using (.+) with generated code$")
    public void request_is_prepared_and_body_with_and_using_and(String key, String value, String code) {
        data.request = given().header("Authorization", "Key " + apiKey(props.getProperty("siteKey"))).
                header("Content-Type", "application/json").body("{"+key+":"+value+","+code+":"+data.newCode+"}");
        System.out.println(data.newCode);
    }

    @Then("^I expect statusCode (.+)$")
    public void i_expect_statuscode(int statuscode){
        data.json = data.response.then().assertThat().statusCode(statuscode);
    }

    @After
    public void logs(Scenario scenario) {
    byte[] log = data.r.getBytes();
    scenario.embed(log, "text/html");
    }
}
