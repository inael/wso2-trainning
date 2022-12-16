package org.wso2.identity.sample.oidc.domain;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord {

    private String id;
    private String medicineCode;
    private String doctorId;
    private int amount;
    private String date;

}
