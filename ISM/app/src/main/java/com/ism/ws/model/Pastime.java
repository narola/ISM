package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
/**
 * Created by c161 on 19/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pastime {
	
    private ArrayList<PastimeData> favoritePastime;
    private ArrayList<PastimeData> suggestedPastime;
    

    @JsonProperty("favorite_pastime")
    public ArrayList<PastimeData> getFavoritePastime() {
        return this.favoritePastime;
    }

    public void setFavoritePastime(ArrayList<PastimeData> favoritePastime) {
        this.favoritePastime = favoritePastime;
    }
    @JsonProperty("suggested_pastime")
    public ArrayList<PastimeData> getSuggestedPastime() {
        return this.suggestedPastime;
    }

    public void setSuggestedPastime(ArrayList<PastimeData> suggestedPastime) {
        this.suggestedPastime = suggestedPastime;
    }


    
}
