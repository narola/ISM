package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class AboutMe {
	
    private String message;
    private ArrayList<Object> data;
    private String status;



    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("data")
    public ArrayList<Object> getData() {
        return this.data;
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    
}
