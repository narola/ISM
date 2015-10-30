package com.ism.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForgotPasswordRequest {
	
    private String emailId;

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @JsonProperty("email_id")
    public String getEmailId() {
        return this.emailId;
    }

}
