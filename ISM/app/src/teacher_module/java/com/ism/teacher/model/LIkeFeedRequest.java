package com.ism.teacher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * model to like post feeds of users.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LikeFeedRequest {


    private String user_id;
    private String[] liked_id;
    private String[] unliked_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String[] getLiked_id() {
        return liked_id;
    }

    public void setLiked_id(String[] liked_id) {
        this.liked_id = liked_id;
    }

    public String[] getUnliked_id() {
        return unliked_id;
    }

    public void setUnliked_id(String[] unliked_id) {
        this.unliked_id = unliked_id;
    }


}
