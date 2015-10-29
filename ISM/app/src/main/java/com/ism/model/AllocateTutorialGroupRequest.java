package com.ism.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllocateTutorialGroupRequest {
	
    private double userId;

    public double getUserId() {
        return this.userId;
    }

    @JsonProperty("user_id")
    public void setUserId(double userId) {
        this.userId = userId;
    }

}
