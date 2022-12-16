package org.wso2.identity.sample.oidc.error;

import feign.Response;
public class HttpRedirectErrorException extends RuntimeException {

    private Response response;

    public HttpRedirectErrorException(Response response) {
        this.response = response;
    }

    public HttpRedirectErrorException(String message, Response response) {
        super(message);
        this.response = response;
    }

    public HttpRedirectErrorException(String message, Throwable cause, Response response) {
        super(message, cause);
        this.response = response;
    }

    public HttpRedirectErrorException(Throwable cause, Response response) {
        super(cause);
        this.response = response;
    }

    public HttpRedirectErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Response response) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
