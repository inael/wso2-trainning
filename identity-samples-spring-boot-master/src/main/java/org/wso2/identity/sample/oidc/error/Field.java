package org.wso2.identity.sample.oidc.error;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Field {

    public Field(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Field() {

    }

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
