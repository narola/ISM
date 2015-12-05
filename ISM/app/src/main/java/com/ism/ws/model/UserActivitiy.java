package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c161 on 04/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserActivitiy {
	
    private String activityType;
    private Studymates studymates;
    private FeedPosted feedPosted;

    public static final String ACTIVITY_STUDYMATE = "studymate";
    public static final String ACTIVITY_FEED_POSTED = "post";
    public static final String ACTIVITY_TOPIC_ALLOCATED = "topic_allocated";
    public static final String ACTIVITY_EXAM_ATTEMPTED = "exam_attempted";
    public static final String ACTIVITY_FEED_LIKED = "feed_liked";
    public static final String ACTIVITY_ASSIGNMENT_SUBMITTED = "assignment_submitted";
    public static final String ACTIVITY_FEED_COMMENTED = "feed_commented";

    @JsonProperty("activity_type")
    public String getActivityType() {
        return this.activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    @JsonProperty("studymates")
    public Studymates getStudymates() {
        return this.studymates;
    }

    public void setStudymates(Studymates studymates) {
        this.studymates = studymates;
    }

    @JsonProperty("feed_posted")
    public FeedPosted getFeedPosted() {
        return this.feedPosted;
    }

    public void setFeedPosted(FeedPosted feedPosted) {
        this.feedPosted = feedPosted;
    }

}
