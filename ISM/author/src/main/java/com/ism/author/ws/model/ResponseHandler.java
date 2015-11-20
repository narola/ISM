package com.ism.author.ws.model;


import com.ism.author.model.Data;

import java.util.ArrayList;

/**
 * Created by c166 on 23/10/15.
 */
public class ResponseHandler {

    private String message;
    private ArrayList<Data> data;
    private String status;

    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String DUPLICATE_ENTRY = "Duplicate entry";
    private String TAG = ResponseHandler.class.getSimpleName();

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


    /*this is the new code for the response handler*/

//    private ArrayList<Data> data;


}

