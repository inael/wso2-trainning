package org.wso2.identity.sample.oidc.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class SpringWebClientErrorDecoder implements ErrorDecoder {
    private final Logger LOGGER = Logger.getLogger(SpringWebClientErrorDecoder.class.getName());

    private ErrorDecoder delegate = new Default();

    @Override
    public Exception decode(final String methodKey, final Response response) {
        final HttpHeaders responseHeaders = new HttpHeaders();
        response.headers().forEach((k, v) -> responseHeaders.put(k, new ArrayList<>(v)));

        HttpStatus statusCode = HttpStatus.PRECONDITION_FAILED;

        try {
            statusCode = HttpStatus.valueOf(response.status());
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.FINE, String.format("Invalid HTTP Status %s!", response.status()));
        }

        final String statusText = response.reason();
        final byte[] responseBody = getBodyAsByteArray(response.body());

        if (response.status() >= 301 && response.status() <= 399) {
            throw new HttpRedirectErrorException(response);
        }

        if (response.status() >= 400 && response.status() <= 499) {
            return new HttpClientErrorException(statusCode, statusText, responseHeaders, responseBody, StandardCharsets.UTF_8);
        }

        if (response.status() >= 500 && response.status() <= 599) {
            return new HttpServerErrorException(statusCode, statusText, responseHeaders, responseBody, StandardCharsets.UTF_8);
        }
        return delegate.decode(methodKey, response);
    }

    private byte[] getBodyAsByteArray(final Response.Body body) {
        if (Objects.isNull(body)) {
            return null;
        }

        try {
            return read(body.asInputStream()).getBytes();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to process response body.", ex);
        }
    }

    private String read(final InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}