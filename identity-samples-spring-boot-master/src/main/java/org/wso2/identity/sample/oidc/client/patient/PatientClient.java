package org.wso2.identity.sample.oidc.client.patient;

import feign.Headers;
import feign.Param;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "patientClient",
        url = "${healt-first-api.host}",
        configuration = PatientConfiguration.class)
public interface PatientClient {
    @RequestMapping(method = RequestMethod.GET, value = "/patient/search")
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    Response searchPatient(@Param String email);


}