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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class Registration extends ReusableMethods {

    private static String phoneNumber = getRandomPhone();

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

    @Given("^Сформирован запрос с корректным номером телефона$")
    public void registration_code_given() throws Throwable {
            data.request = given().header("Authorization", "Key " + apiKey(props.getProperty("siteKey"))).
                    header("Content-Type", "application/json").body("{\"phone\": \""+phoneNumber+"\"}");
    }

    @When("^Запрос отправлен на ресурс registration.code$")
    public void registration_code_when() throws Throwable {
            data.response = data.request.when().put(props.getProperty("registration.code"));
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Then("^Получен статус-код 200$")
    public void registration_code_then() throws Throwable {
        data.json = data.response.then().assertThat().statusCode(200);
    }

    @And("^Ответ содержит такие данные$")
    public void registration_code_and(Map<String,Boolean> responseFields) throws Throwable {
        for (Map.Entry<String, Boolean> field : responseFields.entrySet()) {
            data.json.body(field.getKey(), equalTo(field.getValue()));
        }
            data.js = rawToJson(data.json);
            data.newCode = data.js.get("code");
    }

    // Обработка метода registration.confirm, используя код, полученный посредством выполнения метода registration.code

    @Given("^Формирую запрос с корректным номером телефона и кодом подтверждения$")
    public void registration_confirm_given() throws Throwable {
        data.request = given().header("Authorization", "Key " + apiKey(props.getProperty("siteKey"))).
                header("Content-Type", "application/json").body("{\"phone\":\""+phoneNumber+"\",\"code\":"+data.newCode+"}");
    }

    @When("^Запрос отправлен на ресурс registration.confirm$")
    public void registration_confirm_when() throws Throwable {
        data.response = data.request.when().put(props.getProperty("registration.confirm"));
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);

    }

    @And("^Ответ содержит handle$")
    public void registration_confirm_and() throws Throwable {
        data.js = rawToJson(data.json);
        data.handle = data.js.get("handle");
    }





    // Обработака ошибок метода registration.code

    @Given("^Запрос сформирован, в теле передан (.+) и (.+)$")
    public void request_is_prepared_and_body_contains_and(String key, String value) throws Throwable {
        data.request = given().header("Authorization", "Key " + apiKey(props.getProperty("siteKey"))).
                header("Content-Type", "application/json").body("{"+key+": "+value+"}");
    }

    @When("^Я отправляю запрос на  (.+)$")
    public void i_send_the_new_request_with(String resource) throws Throwable {
        data.response = data.request.when().put(resource);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);


    }

    @And("^Ответ содержит (.+) и (.+)$")
    public void registration_code_errorcode(String arg1, int arg2) throws Throwable {
        data.json = data.response.then().body(arg1, equalTo(arg2));
    }

    @And("^А также (.+) и (.+)$")
    public void registration_code_message(String arg1, String arg2) throws Throwable {
        data.json = data.response.then().body(arg1, equalTo(arg2));
    }

    @Then("^Получен такой статус-код (.+)$")
    public void i_expect_statuscode(int statuscode){
        data.json = data.response.then().assertThat().statusCode(statuscode);
    }

    @After
    public void logs(Scenario scenario) {
        byte[] log = data.r.getBytes();
        scenario.embed(log, "text/html");
    }

}
