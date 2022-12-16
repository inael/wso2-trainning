package org.wso2.identity.sample.oidc.client.doctor;


import com.fasterxml.jackson.databind.JsonNode;
import feign.Headers;
import feign.Param;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wso2.identity.sample.oidc.client.admin.AdminConfiguration;
import org.wso2.identity.sample.oidc.client.patient.PatientConfiguration;
import org.wso2.identity.sample.oidc.domain.MedicalRecord;
import org.wso2.identity.sample.oidc.domain.Patient;


@FeignClient (name = "doctorClient",
        url = "${healt-first-api.host}",
        configuration = DoctorConfiguration.class)
public interface DoctorClient {
    @RequestMapping(method = RequestMethod.POST, value = "/patient/{patientId}/history")
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    JsonNode addMedicalRecors(@PathVariable String patientId, MedicalRecord medicalRecord);

    @RequestMapping(method = RequestMethod.GET, value = "/patient/search?email={email}")
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    Patient searchPatient(@Param String email);

    @RequestMapping(method = RequestMethod.DELETE, value = "/patient/{patientId}/history/{idMedicalRecord}")
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    JsonNode deleteMedicaRecord(@PathVariable String patientId, @PathVariable String idMedicalRecord);


}