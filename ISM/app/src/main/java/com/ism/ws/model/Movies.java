package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by c162 on 21/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movies {
	
    private ArrayList<MovieData> suggestedMovies;
    private ArrayList<MovieData> favoriteMovies;


    @JsonProperty("suggested_movies")
    public ArrayList<MovieData> getSuggestedMovies() {
        return this.suggestedMovies;
    }

    public void setSuggestedMovies(ArrayList<MovieData> suggestedMovies) {
        this.suggestedMovies = suggestedMovies;
    }
    @JsonProperty("favorite_movies")
    public ArrayList<MovieData> getFavoriteMovies() {
        return this.favoriteMovies;
    }

    public void setFavoriteMovies(ArrayList<MovieData> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }


    
}
