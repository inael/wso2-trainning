package org.wso2.identity.sample.oidc.domain;

import java.util.*;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Patient {

    public static final String DATE_FORMAT = "MM/dd/yyyy";
    private String id;
    private String name;
    private String email;
    private String dateOfBirth;
    private Map<String, MedicalRecord> medicalRecords = new HashMap<>();;


    public Patient() {

    }

}
