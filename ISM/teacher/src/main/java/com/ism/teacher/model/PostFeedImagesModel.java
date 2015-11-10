package com.ism.teacher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c162 on 02/11/15.
 */
public class PostFeedImagesModel {
    private String image_link;
    private String id;

    @JsonProperty("image_link")
    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
