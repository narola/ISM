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
public class Books {
	
    private ArrayList<Book> suggested;
    private ArrayList<Book> favorite;

    @JsonProperty("suggested_book")
    public ArrayList<Book> getSuggested() {
        return this.suggested;
    }

    public void setSuggested(ArrayList<Book> suggested) {
        this.suggested = suggested;
    }
    @JsonProperty("favorite_book")
    public ArrayList<Book> getFavorite() {
        return this.favorite;
    }

    public void setFavorite(ArrayList<Book> favorite) {
        this.favorite = favorite;
    }


    
}
