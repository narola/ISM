package com.ism.teacher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

    private String feed_id;
    private String created_date;
    private String total_comment;
    private String total_like;
    private String modified_date;
    private String posted_on;
    private String user_id;
    private String audio_link;
    private String video_link;
    private String full_name;
    private String feed_text;
    private int like;
    private ArrayList<Comment> comment_list;

    //For StudymatesList

    private String profile_pic;


    //For AllComments

    private String id;
    private String commentBy;
    private String comment;
    private String profileLink;


    /*for the getAllClassrooms*/

    private String class_name;
    private String class_nickname;
    private String course_id;

    /*for the getSubjects*/

    private String subject_image;
    private String subject_name;

     /* for the getTopics*/

    private String topic_name;
    private String topic_description;

    @JsonProperty("class_name")
    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @JsonProperty("class_nickname")
    public String getClass_nickname() {
        return class_nickname;
    }

    public void setClass_nickname(String class_nickname) {
        this.class_nickname = class_nickname;
    }

    @JsonProperty("course_id")
    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    @JsonProperty("subject_image")
    public String getSubject_image() {
        return subject_image;
    }

    public void setSubject_image(String subject_image) {
        this.subject_image = subject_image;
    }

    @JsonProperty("subject_name")

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    @JsonProperty("topic_name")

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    @JsonProperty("topic_description")

    public String getTopic_description() {
        return topic_description;
    }

    public void setTopic_description(String topic_description) {
        this.topic_description = topic_description;
    }

    @JsonProperty("like")
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("commentBy")
    public String getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @JsonProperty("profileLink")
    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    @JsonProperty("profile_pic")
    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }


    //For feeds

    @JsonProperty("feed_id")
    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    @JsonProperty("created_date")
    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    @JsonProperty("total_comment")
    public String getTotal_comment() {
        return total_comment;
    }

    public void setTotal_comment(String total_comment) {
        this.total_comment = total_comment;
    }

    @JsonProperty("total_like")
    public String getTotal_like() {
        return total_like;
    }

    public void setTotal_like(String total_like) {
        this.total_like = total_like;
    }

    @JsonProperty("modified_date")
    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    @JsonProperty("posted_on")
    public String getPosted_on() {
        return posted_on;
    }

    public void setPosted_on(String posted_on) {
        this.posted_on = posted_on;
    }

    @JsonProperty("user_id")
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @JsonProperty("audio_link")
    public String getAudio_link() {
        return audio_link;
    }

    public void setAudio_link(String audio_link) {
        this.audio_link = audio_link;
    }

    @JsonProperty("video_link")
    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    @JsonProperty("full_name")
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    @JsonProperty("feed_text")
    public String getFeed_text() {
        return feed_text;
    }

    public void setFeed_text(String feed_text) {
        this.feed_text = feed_text;
    }


    @JsonProperty("comment_list")
    public ArrayList<Comment> getCommentList() {
        return comment_list;
    }

    public void setCommentList(ArrayList<Comment> comment) {
        this.comment_list = comment;
    }


}
