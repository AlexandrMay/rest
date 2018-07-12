package Properties;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.codec.digest.DigestUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public abstract class ReusableMethods {

    // Шифрование ключа в md5

    public static String apiKey(String key) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String md5Hex = DigestUtils.md5Hex(format.format(date)+key);
        System.out.println("MY SITE KEY IS "+md5Hex);
        return md5Hex;
    }
    // Форматирование полученного ответа в json

    public static JsonPath rawToJson(ValidatableResponse r) {
        String resp = r.extract().asString();
        JsonPath x = new JsonPath(resp);
        return x;
    }

    // Форматирование полученного ответа в String

    public static String rawToString(Response response) {
        String resp = response.asString();
        return resp;
    }

    //Генерация рандомного 13-значного номера телефона

    private static final int PHONE_NUMBER_LENGTH = 13;
    public static String getRandomPhone() {
        String s = "123456789";
        StringBuffer phoneNumber = new StringBuffer();
        for (int i = 0; i < PHONE_NUMBER_LENGTH; i++) {
            phoneNumber.append(s.charAt(new Random().nextInt(s.length())));
        }
        String s1 = "+" + phoneNumber.toString();
        return s1;
    }
}
