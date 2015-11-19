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
public class ResponseAboutMe {

    private String message;
    private ArrayList<DataAboutMe> data;
    private String status;

    @JsonProperty("messsage")
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @JsonProperty("data")
    public ArrayList<DataAboutMe> getData() {
        return this.data;
    }

    public void setData(ArrayList<DataAboutMe> data) {
        this.data = data;
    }

    @JsonProperty("status")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
