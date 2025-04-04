package simple.httpRequest;

import java.util.HashMap;

public class URL {
    private final HashMap<String, String> queryString = new HashMap<>();
    private final HashMap<String, String> params = new HashMap<>();



    // url : /member/:memberId/team/:teamId
    public void parsingLambdaURL(String url){
        String[] split = url.split("/");

    }

    public void parsingRequestURL(){
        System.out.println("gd");
    }


}
