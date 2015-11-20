package com.ism.ws.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ism.ws.model.City;
import com.ism.ws.model.Country;
import com.ism.ws.model.State;
import com.ism.ws.model.TutorialGroup;
import com.ism.ws.model.User;
import com.ism.ws.model.UserDetail;
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
    private ArrayList<State> states;
    private ArrayList<UserDetail> userDetails;
	private ArrayList<Country> countries;
	private ArrayList<City> cities;
	private ArrayList<User> user;
	private ArrayList<TutorialGroup> tutorialGroup;

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
    public ArrayList<State> getStates() {
        return this.states;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

	@JsonProperty("user_details")
    public ArrayList<UserDetail> getUserDetails() {
        return this.userDetails;
    }

    public void setUserDetails(ArrayList<UserDetail> userDetails) {
        this.userDetails = userDetails;
    }

	@JsonProperty("countries")
	public ArrayList<Country> getCountries() {
		return this.countries;
	}

	public void setCountries(ArrayList<Country> countries) {
		this.countries = countries;
	}

	@JsonProperty("cities")
	public ArrayList<City> getCities() {
		return this.cities;
	}

	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}

	@JsonProperty("user")
	public ArrayList<User> getUser() {
		return this.user;
	}

	public void setUser(ArrayList<User> user) {
		this.user = user;
	}

	@JsonProperty("tutorial_group")
	public ArrayList<TutorialGroup> getTutorialGroup() {
		return this.tutorialGroup;
	}

	public void setTutorialGroup(ArrayList<TutorialGroup> tutorialGroup) {
		this.tutorialGroup = tutorialGroup;
	}

}
