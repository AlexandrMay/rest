package Properties;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.codec.digest.DigestUtils;
import java.text.SimpleDateFormat;
import java.util.Date;




public abstract class ReusableMethods {


    public static String apiKey(String key) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String md5Hex = DigestUtils.md5Hex(format.format(date)+key);
        return md5Hex;
    }

    public JsonPath rawToJson(ValidatableResponse r) {
        String resp = r.extract().body().asString();
        JsonPath x = new JsonPath(resp);
        return x;
    }








}
