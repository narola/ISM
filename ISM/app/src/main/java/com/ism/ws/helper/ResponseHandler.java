package com.ism.ws.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ism.ws.model.DataGetCountries;
import com.ism.ws.model.DataLogin;
import com.ism.ws.model.States;
//import com.narola.apisample.ws.model.Feed;
//import com.narola.apisample.ws.model.Question;

import java.util.ArrayList;

/**
 * Created by c161 on 19/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseHandler {
	
    private String message;
    private String status;
    private ArrayList<DataLogin> question;
    private ArrayList<DataGetCountries> feed;
    private ArrayList<States> states;

    @JsonProperty("message")
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("status")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("states")
    public ArrayList<States> getStates() {
        return this.states;
    }

    public void setStates(ArrayList<States> states) {
        this.states = states;
    }

}
