package com.ism.author.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c75 on 05/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class FileUploadResponse {

    private String question_id;
    private String mediaType;



    private String imageLink;

    @JsonProperty("question_id")
    public String getQuestion_id() {
        return question_id;
    }

    public FileUploadResponse setQuestion_id(String question_id) {
        this.question_id = question_id;
        return this;
    }

    @JsonProperty("mediaType")
    public String getMediaType() {
        return mediaType;
    }

    public FileUploadResponse setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @JsonProperty("image_link")

    public String getImageLink() {
        return imageLink;
    }

    public FileUploadResponse setImageLink(String imageLink) {
        this.imageLink = imageLink;
        return this;
    }
}
