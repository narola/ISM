package com.ism.author.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserImages {

    private ArrayList<String> profileImages;


    @JsonProperty("profile_images")
    public ArrayList<String> getProfileImages() {
        return this.profileImages;
    }

    public void setProfileImages(ArrayList<String> profileImages) {
        this.profileImages = profileImages;
    }


}
