package org.wso2.identity.sample.oidc.error;

import org.springframework.http.HttpStatus;

;


public class APIException extends RuntimeException {

    private final Error error;
    private final HttpStatus httpStatus;


    public APIException(final HttpStatus status, final Error error, final Throwable cause) {
        super(error.getDescription(), cause);
        this.error = error;
        this.httpStatus = status;
    }

    public APIException(final HttpStatus httpStatus, final String error) {
        super(error);
        this.httpStatus = httpStatus;
        this.error = new Error(error);
    }


    public APIException(final HttpStatus httpStatus, final Error error) {
        super(error.getDescription());
        this.httpStatus = httpStatus;
        this.error = error;
    }


    public APIException(final HttpStatus status, final String message, final Throwable cause) {
        this(status, new Error(message), cause);
    }

    public APIException(final HttpStatus status, final String message, final String parceiro, final Throwable cause) {
        this(status, new Error(message, parceiro), cause);
    }

    public Error getError() {
        return error;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
