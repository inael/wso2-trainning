package org.wso2.identity.sample.oidc.client.admin;


import com.fasterxml.jackson.databind.JsonNode;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.wso2.identity.sample.oidc.client.patient.PatientConfiguration;
import org.wso2.identity.sample.oidc.domain.Patient;
import org.wso2.identity.sample.oidc.openfeign.oauthfeign.OAuthFeignConfig;

import java.util.List;

@FeignClient (name = "adminClient", url = "${healt-first-api.host}", configuration = AdminConfiguration.class)
public interface AdminClient {
    @RequestMapping(method = RequestMethod.POST, value = "/patient")
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    JsonNode addPatient(Patient patient);

    @RequestMapping(method = RequestMethod.DELETE, value = "/patient/{patientId}")
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    JsonNode deletePatient(@PathVariable("patientId") String patientId);

    @RequestMapping(method = RequestMethod.GET, value = "/patient/search")
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    Patient searchPatient(@RequestParam("email") String email);



}