package org.wso2.identity.sample.oidc.client.patient;


public class AddPatientResponse {
    private String id;

    public AddPatientResponse() {
    }

    public AddPatientResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
