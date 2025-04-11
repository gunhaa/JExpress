package simple.decoder;

import java.nio.charset.StandardCharsets;

public class URLDecoder {

    public static String decodeURL(String rawValue){
        return java.net.URLDecoder.decode(rawValue, StandardCharsets.UTF_8);
    }

}
