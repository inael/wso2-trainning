package org.wso2.identity.sample.oidc.client;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class AuthConfiguration {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(getClientId(), getSecret());
    }

    protected abstract String getClientId();

    protected abstract String getSecret();
}


