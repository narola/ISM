package com.ism.author.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by c162 on 30/10/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostFeedRequest {
    public String getFeed_by() {
        return feed_by;
    }

    public void setFeed_by(String feed_by) {
        this.feed_by = feed_by;
    }

    public String getFeed_text() {
        return feed_text;
    }

    public void setFeed_text(String feed_text) {
        this.feed_text = feed_text;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getAudio_link() {
        return audio_link;
    }

    public void setAudio_link(String audio_link) {
        this.audio_link = audio_link;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPosted_on() {
        return posted_on;
    }

    public void setPosted_on(String posted_on) {
        this.posted_on = posted_on;
    }

    String feed_by;
    String feed_text;
    String video_link;
    String audio_link;
    List<String> images;
    String posted_on;



}
