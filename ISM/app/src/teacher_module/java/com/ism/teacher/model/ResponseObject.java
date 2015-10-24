package com.ism.teacher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ResponseObject {
	
    private String message;
    private ArrayList<Data> data;
    private String status;
    private String total_feeds;

    @JsonProperty("total_feeds")
    public String getTotal_feeds() {
        return total_feeds;
    }

    public void setTotal_feeds(String total_feeds) {
        this.total_feeds = total_feeds;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Data> getData() {
        return this.data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    
}
