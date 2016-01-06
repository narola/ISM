package com.ism.author.ws.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c166 on 05/01/16.
 */
public class FeedComment {


    private int commentId;


    @JsonProperty("comment_id")
    public int getCommentId() {
        return this.commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
