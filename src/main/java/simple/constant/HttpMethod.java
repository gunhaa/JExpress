package simple.constant;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public HttpStatus getHttpStatus(){
        if(GET == this){
            return HttpStatus.OK_200;
        }
        if(POST == this){
            return HttpStatus.CREATED_201;
        }
        return HttpStatus.OK_200;
    }
}
