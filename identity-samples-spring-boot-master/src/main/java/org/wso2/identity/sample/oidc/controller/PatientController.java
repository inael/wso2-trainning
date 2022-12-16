package org.wso2.identity.sample.oidc.controller;


import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.wso2.identity.sample.oidc.client.admin.AdminClient;
import org.wso2.identity.sample.oidc.client.doctor.DoctorClient;
import org.wso2.identity.sample.oidc.domain.MedicalRecord;
import org.wso2.identity.sample.oidc.domain.Patient;
import org.wso2.identity.sample.oidc.service.PatientService;

import java.util.UUID;
import java.util.logging.Level;


@Controller
public class PatientController {
    private String userName;
    private DefaultOidcUser user;
    @Autowired
    private PatientService patientService;

    @Autowired
    private AdminClient adminClient;

    @Autowired
    private DoctorClient doctorClient;

    private Patient patient;

    public PatientController() {
        patient = new Patient();
    }

    @PostMapping("/add-patient")
    public String addPatient(Authentication authentication, Patient patient, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                return "addPatient";
            }
            extracted(authentication);

            String response = patientService.addPatient(patient);
            model.addAttribute("success", response);
            model.addAttribute("userName", userName);
            model.addAttribute("idtoken", user.getClaims());

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }


    @PostMapping("/search-patient")
    public String searchPatient(Authentication authentication, Patient patient, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                return "search-patient";
            }
            extracted(authentication);

            Patient response = adminClient.searchPatient(patient.getEmail());
            model.addAttribute("userName", userName);
            model.addAttribute("idtoken", user.getClaims());
            model.addAttribute("patient", response);
            model.addAttribute("medicalRecord", new MedicalRecord());

            model.addAttribute("success", patient.getId());


        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("patient", new Patient());
            throw e;
        }

        return "viewPatientRecords";
    }

    @PostMapping("/delete-patient-by-id")
    public String deletePatientById(Authentication authentication, String patientId, Model model) {
        try {
            extracted(authentication);

            JsonNode response = adminClient.deletePatient(patientId);

            model.addAttribute("success", "{patientId:" + patientId + "}");

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("patient", new Patient());
        return "index";
    }

    @PostMapping("/delete-patient")
    public String deletePatient(Authentication authentication, Patient patient, Model model) {
        return deletePatientById(authentication, patient.getId(), model);
    }

    @PostMapping("/delete-patient-medical-records")
    public String deletePatientMedicalRecord(Authentication authentication, String patientId, String recordId, String patientEmail, Model model) {
        try {
            extracted(authentication);

            JsonNode response1 = doctorClient.deleteMedicaRecord(patientId, recordId);
            Patient response = adminClient.searchPatient(patientEmail);
            model.addAttribute("success", "{patientId:" + patientId + ", recordId:" + recordId + "}");
            model.addAttribute("patient", response);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("patient", new Patient());
        }
        return "viewPatientRecords";
    }

    @PostMapping("/get-patient-medical-records")
    public String getPatientMedicalRecord(Authentication authentication, Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "get-patient-medical-records";
        }
        Patient response = patientService.getPatientMedicalRecord(patient.getEmail());
        model.addAttribute("patient", response);
        model.addAttribute("userName", userName);
        model.addAttribute("idtoken", user.getClaims());
        return "redirect:/get-patient-medical-records";
    }

    @PostMapping("/add-patient-medical-records")
    public String addPatientMedicaRecords(Authentication authentication,
                                          Patient patient,
                                          String email,
                                          String patientId,
                                          String medicineCode,
                                          int amount,
                                          String date,
                                          String doctorId,
                                          BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                return "addPatient";
            }
            extracted(authentication);
            MedicalRecord medicalRecord = MedicalRecord.builder().id(UUID.randomUUID().toString()).medicineCode(medicineCode).amount(amount).doctorId(doctorId).date(date).build();
            JsonNode response1 = patientService.addPatientMedicalRecord(patientId, medicalRecord);
            Patient response2 = adminClient.searchPatient(patient.getEmail());
            model.addAttribute("success", "{patientId:" + response2.getId() + ", recordId:" + response1.get("id") + "}");
            model.addAttribute("patient", response2);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("patient", new Patient());
        }
        return "index";
    }
    private void extracted(Authentication authentication) {
        user = (DefaultOidcUser) authentication.getPrincipal();
        if (user != null) {
            userName = user.getName();
        }
    }

}
