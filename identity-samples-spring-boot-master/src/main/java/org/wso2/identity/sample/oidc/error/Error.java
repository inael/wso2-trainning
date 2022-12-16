package org.wso2.identity.sample.oidc.error;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;


@JsonInclude(Include.NON_NULL)
public class Error implements Serializable {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String description;

    @JsonProperty("fields")
    private List<Field> fields;

    @JsonProperty("parceiro")
    private Object parceiro;

    public Error(final String description) {
        this.description = description;
    }


    public Error(final String description, final String parceiro) {
        this.description = description;
        this.parceiro = parceiro;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public List<Field> getFields() {
        return fields;
    }

    public Object getParceiro() {
        return parceiro;
    }
}