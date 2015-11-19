package com.ism.ws.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ism.ws.model.DataGetCountries;
import com.ism.ws.model.DataLogin;
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

    /*@JsonProperty("question")
    public ArrayList<Question> getQuestion() {
        return this.question;
    }

    public void setQuestion(ArrayList<Question> question) {
        this.question = question;
    }

    @JsonProperty("feed")
    public ArrayList<Feed> getFeed() {
        return this.feed;
    }

    public void setFeed(ArrayList<Feed> feed) {
        this.feed = feed;
    }*/

}
