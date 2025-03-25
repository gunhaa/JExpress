package simple.response;

import lombok.Getter;
import simple.constant.HttpStatus;
import simple.httpRequest.SimpleHttpRequest;

import java.io.OutputStream;
import java.io.PrintWriter;

@Getter
public class ResponseSuccess {

    private final HttpStatus userCustomHttpStatus;
    private final Object entity;

    public ResponseSuccess(HttpStatus userCustomHttpStatus, Object entity) {
        this.userCustomHttpStatus = userCustomHttpStatus;
        this.entity = entity;
    }

//    @Deprecated
//    public void getDefaultResponse(OutputStream out, SimpleHttpRequest simpleHttpRequest){
//
//        try(PrintWriter pw = new PrintWriter(out, true)){
//            ResponseBuilder hb = new ResponseBuilder(simpleHttpRequest, this.userCustomHttpStatus, this.entity);
//            StringBuilder response = hb.protocol().httpStatus().date().contentType().contentLength().server().connection().crlf().body().getResponse();
//            pw.print(response);
//        }
//
//    }
}
