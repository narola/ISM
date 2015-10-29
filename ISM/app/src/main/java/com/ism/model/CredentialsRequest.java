package com.ism.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialsRequest {
	
    private String schoolName;
    private String homeAddress;
    private int cityId;
    private String lastname;
    private String firstname;
    private int stateId;
    private int countryId;
    private String contactNumber;
    private String emailAddress;

    public String getSchoolName() {
        return this.schoolName;
    }

    @JsonProperty("school_name")
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getHomeAddress() {
        return this.homeAddress;
    }

    @JsonProperty("home_address")
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public int getCityId() {
        return this.cityId;
    }

    @JsonProperty("city_id")
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getLastname() {
        return this.lastname;
    }

    @JsonProperty("lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return this.firstname;
    }

    @JsonProperty("firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getStateId() {
        return this.stateId;
    }

    @JsonProperty("state_id")
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getCountryId() {
        return this.countryId;
    }

    @JsonProperty("country_id")
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    @JsonProperty("contact_number")
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    @JsonProperty("email_address")
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
}
