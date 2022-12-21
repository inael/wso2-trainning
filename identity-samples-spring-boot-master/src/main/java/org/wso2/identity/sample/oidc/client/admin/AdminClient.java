package org.wso2.identity.sample.oidc.client.admin;


import com.fasterxml.jackson.databind.JsonNode;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wso2.identity.sample.oidc.domain.Patient;

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