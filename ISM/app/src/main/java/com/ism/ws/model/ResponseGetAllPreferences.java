package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
/**
 * Created by c162 on 19/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseGetAllPreferences {

    private String status;
    private ArrayList<DataGetAllPreferences> data;
    private String message;


    @JsonProperty("status")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("data")
    public ArrayList<DataGetAllPreferences> getData() {
        return this.data;
    }

    public void setData(ArrayList<DataGetAllPreferences> data) {
        this.data = data;
    }

    @JsonProperty("message")
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
