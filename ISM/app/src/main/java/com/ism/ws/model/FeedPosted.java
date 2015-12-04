package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c161 on 04/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedPosted {
	
    private String feedId;
    private String feedText;
    private String feedUserPic;
    private String feedPostedOn;
    private String feedUserName;
    private String feedUserId;

    @JsonProperty("feed_id")
    public String getFeedId() {
        return this.feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    @JsonProperty("feed_text")
    public String getFeedText() {
        return this.feedText;
    }

    public void setFeedText(String feedText) {
        this.feedText = feedText;
    }

    @JsonProperty("feed_user_pic")
    public String getFeedUserPic() {
        return this.feedUserPic;
    }

    public void setFeedUserPic(String feedUserPic) {
        this.feedUserPic = feedUserPic;
    }

    @JsonProperty("feed_posted_on")
    public String getFeedPostedOn() {
        return this.feedPostedOn;
    }

    public void setFeedPostedOn(String feedPostedOn) {
        this.feedPostedOn = feedPostedOn;
    }

    @JsonProperty("feed_user_name")
    public String getFeedUserName() {
        return this.feedUserName;
    }

    public void setFeedUserName(String feedUserName) {
        this.feedUserName = feedUserName;
    }

    @JsonProperty("feed_user_id")
    public String getFeedUserId() {
        return this.feedUserId;
    }

    public void setFeedUserId(String feedUserId) {
        this.feedUserId = feedUserId;
    }

}
