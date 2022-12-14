package org.wso2.identity.sample.oidc.client.patient;

import com.fasterxml.jackson.databind.JsonNode;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.wso2.identity.sample.oidc.client.AuthConfiguration;
import org.wso2.identity.sample.oidc.error.APIException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.logging.Logger;


@RequiredArgsConstructor
public class PatientConfiguration implements RequestInterceptor {
    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.secret-id}")
    private String secret;
    @Value("${security.oauth2.client.patient.user}")
    private String user;
    @Value("${security.oauth2.client.patient.password}")
    private String password;


    @Value("${security.oauth2.host}")
    private String host;


    @Autowired
    private final RestTemplate restTemplate;
    @Getter
    private String token;
    private LocalDateTime tokenDate;
    private Integer expirationInSeconds;

    @Override
    public void apply(RequestTemplate template) {


        Optional<Object> authorization = Optional.ofNullable(template.headers().get("Authorization"));
        if (!authorization.isPresent()) {

            if (tokenDate == null ||
                    ((expirationInSeconds != null && tokenDate != null)
                            && LocalDateTime.now().plusSeconds(-expirationInSeconds).isAfter(tokenDate))) {


                final String userNamePassword = String.format("%s:%s", user, password);
                final String tokenBase64 = Base64.getEncoder().encodeToString(userNamePassword.getBytes());
                final String credential = String.format("Basic %s", tokenBase64);

                final HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setBasicAuth(clientId, secret);

                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("grant_type", "password");
                map.add("username", user);
                map.add("password", password);
                map.add("scope", "openid delete_patient list_patient_record delete_medical_record add_patient add_medical_records");


                HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

                final ResponseEntity<JsonNode> response =
                        restTemplate
                                .exchange(host,
                                        HttpMethod.POST, entity, JsonNode.class);

                final JsonNode node = response.getBody();

                token = "Bearer " + node.get("access_token").asText();
                tokenDate = LocalDateTime.now();
                expirationInSeconds = node.get("expires_in").asInt();

            }
            template.header("Authorization", token);
        }
    }

}

