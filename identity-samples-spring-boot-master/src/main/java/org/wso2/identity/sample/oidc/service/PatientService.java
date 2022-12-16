package org.wso2.identity.sample.oidc.service;



import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wso2.identity.sample.oidc.client.admin.AdminClient;
import org.wso2.identity.sample.oidc.client.doctor.DoctorClient;
import org.wso2.identity.sample.oidc.domain.MedicalRecord;
import org.wso2.identity.sample.oidc.domain.Patient;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    @Autowired
    private AdminClient adminClient;

    @Autowired
    private DoctorClient doctorClient;

    public String addPatient(Patient patient) {
        patient.setId(UUID.randomUUID().toString());
        JsonNode response =  adminClient.addPatient(patient);
        return response.toString();
    }



    public JsonNode addPatientMedicalRecord(String patientId, MedicalRecord medicalRecord) {
        return doctorClient.addMedicalRecors(patientId, medicalRecord);
    }



    public JsonNode deleteMedicalRecord(String patientId, String medicalRecordId) {
        return doctorClient.deleteMedicaRecord(patientId,medicalRecordId);
    }

    public Patient getPatientMedicalRecord(String email) {
        return doctorClient.searchPatient(email);
    }
}
