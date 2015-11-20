package com.ism.ws.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ism.ws.model.DataAboutMe;
import com.ism.ws.model.DataGetAllPreferences;
import com.ism.ws.model.DataGetCountries;
import com.ism.ws.model.DataLogin;
import com.ism.ws.model.DataUserPreferences;
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
    private ArrayList<DataAboutMe> user;
    private ArrayList<DataGetAllPreferences> preference;
    private ArrayList<DataUserPreferences> userPreference;

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

    @JsonProperty("user")
    public ArrayList<DataAboutMe> getUser() {
        return user;
    }

    public void setUser(ArrayList<DataAboutMe> user) {
        this.user = user;
    }

    @JsonProperty("preference")
    public ArrayList<DataGetAllPreferences> getPreference() {
        return preference;
    }

    public void setPreference(ArrayList<DataGetAllPreferences> preference) {
        this.preference = preference;
    }
    @JsonProperty("user_preference")
    public ArrayList<DataUserPreferences> getUserPreference() {
        return this.userPreference;
    }

    public void setUserPreference(ArrayList<DataUserPreferences> userPreference) {
        this.userPreference = userPreference;
    }




}
