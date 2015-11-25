package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Created by c162 on 19/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class RolemodelData {
	
    private String quotes;
    private String modelName;
    private String organization;
    private String rolemodelId;
    private String achievements;
    private String modelImage;
    private String birthdate;
    private String description;
    private String education;
    private String activities;
    
    @JsonProperty("quotes")
    public String getQuotes() {
        return this.quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }
    @JsonProperty("model_name")
    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    @JsonProperty("organization")
    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
    @JsonProperty("rolemodel_id")
    public String getRolemodelId() {
        return this.rolemodelId;
    }

    public void setRolemodelId(String rolemodelId) {
        this.rolemodelId = rolemodelId;
    }
    @JsonProperty("achievements")
    public String getAchievements() {
        return this.achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }
    @JsonProperty("model_image")
    public String getModelImage() {
        return this.modelImage;
    }

    public void setModelImage(String modelImage) {
        this.modelImage = modelImage;
    }
    @JsonProperty("birthdate")
    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    @JsonProperty("description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @JsonProperty("education")
    public String getEducation() {
        return this.education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
    @JsonProperty("activities")
    public String getActivities() {
        return this.activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }


    
}
