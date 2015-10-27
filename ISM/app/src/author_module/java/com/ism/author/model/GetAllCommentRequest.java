package com.ism.author.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by c166 on 26/10/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllCommentRequest {

    private String feed_id;

    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }


}
