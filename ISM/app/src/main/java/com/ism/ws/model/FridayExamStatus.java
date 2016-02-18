package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c161 on 29/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FridayExamStatus {
	
    private String isReady;

    @JsonProperty("is_ready")
    public String getIsReady() {
        return this.isReady;
    }

    public void setIsReady(String isReady) {
        this.isReady = isReady;
    }

}
