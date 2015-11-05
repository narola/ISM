package com.ism.teacher.model;

/*these model class is for getALLFeedsCooment list*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostFeedCommentsModel {

    private String username;
    private String id;
    private String commentBy;
    private String comment;
    private String profileLink;


    @JsonProperty("full_name")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("comment_by")
    public String getCommentBy() {
        return this.commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    @JsonProperty("comment")
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty("profile_link")
    public String getProfileLink() {
        return this.profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }


}
