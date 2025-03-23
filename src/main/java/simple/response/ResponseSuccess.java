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

    public void getDefaultResponse(OutputStream out, SimpleHttpRequest simpleHttpRequest){
        PrintWriter pw = new PrintWriter(out);
        HttpBuilder hb = new HttpBuilder(simpleHttpRequest, this.userCustomHttpStatus, this.entity);
        hb.protocol().httpStatus().date().contentType().contentLength().server().connection().crlf().body();
        pw.print(hb.getSb());
        pw.flush();
        pw.close();
    }
}
