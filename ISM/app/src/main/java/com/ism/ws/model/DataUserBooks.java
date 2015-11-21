package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataUserBooks {
	
    private ArrayList<Suggested> suggested;
    private ArrayList<Favorite> favorite;

    @JsonProperty("suggested")
    public ArrayList<Suggested> getSuggested() {
        return this.suggested;
    }

    public void setSuggested(ArrayList<Suggested> suggested) {
        this.suggested = suggested;
    }
    @JsonProperty("favorite")
    public ArrayList<Favorite> getFavorite() {
        return this.favorite;
    }

    public void setFavorite(ArrayList<Favorite> favorite) {
        this.favorite = favorite;
    }


    
}
