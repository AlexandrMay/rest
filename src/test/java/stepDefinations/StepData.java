package stepDefinations;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class StepData {

    protected Response response;
    protected RequestSpecification request;
    protected ValidatableResponse json;
    protected static int newCode;
    protected String r;
    protected JsonPath js;
    protected static String handle;
}
