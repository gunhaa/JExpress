package simple.constant;

public enum CustomHttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    EXCEPTION_STATIC,
    ERROR;

    public HttpStatus getDefaultHttpStatus(){
        if(GET == this){
            return HttpStatus.OK_200;
        }
        if(POST == this){
            return HttpStatus.CREATED_201;
        }
        return HttpStatus.OK_200;
    }
}
