package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c162 on 21/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieData {

    private String movieName;
    private String movieGenre;
    private String screenplay;
    private String description;
    private String movieId;
    private String movieImage;

    @JsonProperty("movie_name")
    public String getMovieName() {
        return this.movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    @JsonProperty("movie_genre")
    public String getMovieGenre() {
        return this.movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }
    @JsonProperty("screenplay")
    public String getScreenplay() {
        return this.screenplay;
    }

    public void setScreenplay(String screenplay) {
        this.screenplay = screenplay;
    }
    @JsonProperty("description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @JsonProperty("movie_id")
    public String getMovieId() {
        return this.movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
    @JsonProperty("movie_image")
    public String getMovieImage() {
        return this.movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }


    
}
