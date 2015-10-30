package com.ism.model;

import java.util.ArrayList;

public class ResponseObject {

    private String message;
    private ArrayList<Data> data;
    private String status;

    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String DUPLICATE_ENTRY = "Duplicate entry";

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
