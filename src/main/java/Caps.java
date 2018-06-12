import org.apache.commons.codec.digest.DigestUtils;
import java.text.SimpleDateFormat;
import java.util.Date;



public abstract class Caps {


    public static String apiKey(String key) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String md5Hex = DigestUtils.md5Hex(format.format(date)+key);
        return md5Hex;
    }






}
