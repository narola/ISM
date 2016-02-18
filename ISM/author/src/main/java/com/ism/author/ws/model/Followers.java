package com.ism.author.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Followers {

    private double numberOfAuthorFollowed;
    private String followerProfilePic;
    private String followerId;
    private String followerName;
    private String followerCountryName;
    private String followerSchool;


    @JsonProperty("number_of_author_followed")
    public double getNumberOfAuthorFollowed() {
        return this.numberOfAuthorFollowed;
    }

    public void setNumberOfAuthorFollowed(double numberOfAuthorFollowed) {
        this.numberOfAuthorFollowed = numberOfAuthorFollowed;
    }

    @JsonProperty("follower_profile_pic")
    public String getFollowerProfilePic() {
        return this.followerProfilePic;
    }

    public void setFollowerProfilePic(String followerProfilePic) {
        this.followerProfilePic = followerProfilePic;
    }

    @JsonProperty("follower_id")
    public String getFollowerId() {
        return this.followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    @JsonProperty("follower_name")
    public String getFollowerName() {
        return this.followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }

    @JsonProperty("follower_country_name")
    public String getFollowerCountryName() {
        return followerCountryName;
    }

    public Followers setFollowerCountryName(String followerCountryName) {
        this.followerCountryName = followerCountryName;
        return this;
    }

    @JsonProperty("follower_school")
    public String getFollowerSchool() {
        return followerSchool;
    }

    public Followers setFollowerSchool(String followerSchool) {
        this.followerSchool = followerSchool;
        return this;
    }


}
