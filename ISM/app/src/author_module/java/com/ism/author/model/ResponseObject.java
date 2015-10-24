package com.ism.author.model;


import java.util.ArrayList;

/**
 * Created by c166 on 23/10/15.
 */
public class ResponseObject {

    private String message;
    private ArrayList<Data> data;
    private String status;
    private int total_feeds;

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


    public int getTotal_feeds() {
        return total_feeds;
    }

    public void setTotal_feeds(int total_feeds) {
        this.total_feeds = total_feeds;
    }


}

