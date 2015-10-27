package com.ism.author.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * these is tag friend in feed request class.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagFriendInFeedRequest {


    private String feed_id;
    private String tagged_by;
    private String[] user_id;


    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public String getTagged_by() {
        return tagged_by;
    }

    public void setTagged_by(String tagged_by) {
        this.tagged_by = tagged_by;
    }

    public String[] getUser_id() {
        return user_id;
    }

    public void setUser_id(String[] user_id) {
        this.user_id = user_id;
    }


//    {
//        "feed_id":240,
//            "tagged_by":134,
//            "user_id":[141,167]
//    }


}
