package com.ism.author.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Books {
	
    private ArrayList<BookData> suggested;
    private ArrayList<BookData> favorite;

    @JsonProperty("suggested_book")
    public ArrayList<BookData> getSuggested() {
        return this.suggested;
    }

    public void setSuggested(ArrayList<BookData> suggested) {
        this.suggested = suggested;
    }
    @JsonProperty("favorite_book")
    public ArrayList<BookData> getFavorite() {
        return this.favorite;
    }

    public void setFavorite(ArrayList<BookData> favorite) {
        this.favorite = favorite;
    }


    
}
