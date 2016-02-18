package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionComments {

    private String questionText;
    private String trendingId;
    private String totalComment;
    private String answerText;
    private String postedOn;
    private String postedByUsername;
    private String postedByPic;
    private ArrayList<Comment> comment;
    private String postedByUserId;
    private String followerCount;


    @JsonProperty("question_text")
    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @JsonProperty("trending_id")
    public String getTrendingId() {
        return this.trendingId;
    }

    public void setTrendingId(String trendingId) {
        this.trendingId = trendingId;
    }

    @JsonProperty("total_comment")
    public String getTotalComment() {
        return this.totalComment;
    }

    public void setTotalComment(String totalComment) {
        this.totalComment = totalComment;
    }

    @JsonProperty("answer_text")
    public String getAnswerText() {
        return this.answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @JsonProperty("posted_on")
    public String getPostedOn() {
        return this.postedOn;
    }

    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

    @JsonProperty("posted_by_username")
    public String getPostedByUsername() {
        return this.postedByUsername;
    }

    public void setPostedByUsername(String postedByUsername) {
        this.postedByUsername = postedByUsername;
    }

    @JsonProperty("posted_by_pic")
    public String getPostedByPic() {
        return this.postedByPic;
    }

    public void setPostedByPic(String postedByPic) {
        this.postedByPic = postedByPic;
    }

    @JsonProperty("comment")
    public ArrayList<Comment> getComment() {
        return this.comment;
    }

    public void setComment(ArrayList<Comment> comment) {
        this.comment = comment;
    }

    @JsonProperty("posted_by_user_id")
    public String getPostedByUserId() {
        return this.postedByUserId;
    }

    public void setPostedByUserId(String postedByUserId) {
        this.postedByUserId = postedByUserId;
    }

    @JsonProperty("follower_count")
    public String getFollowerCount() {
        return this.followerCount;
    }

    public void setFollowerCount(String followerCount) {
        this.followerCount = followerCount;
    }


}
