package org.wso2.identity.sample.oidc.openfeign.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.wso2.identity.sample.oidc.openfeign.exception.BadRequestException;
import org.wso2.identity.sample.oidc.openfeign.exception.NotFoundException;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()){
            case 400:
                return new BadRequestException();
            case 404:
                return new NotFoundException("Not found !!!");
            default:
                return new Exception("Generic error");
        }
    }
}
