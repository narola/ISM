package com.ism.author.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadQuestion {

    private String questionId;
    private ArrayList<String> imagesText;


    @JsonProperty("question_id")
    public String getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @JsonProperty("images_text")
    public ArrayList<String> getImagesText() {
        return this.imagesText;
    }

    public void setImagesText(ArrayList<String> imagesText) {
        this.imagesText = imagesText;
    }


}
