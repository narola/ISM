package com.ism.teacher.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {

    private String questionId;

    @JsonProperty("hasgtag")
    public String getHashtag() {
        return hashtag;
    }

    public Question setHashtag(String hashtag) {
        this.hashtag = hashtag;
        return this;
    }

    private String hashtag;

    @JsonProperty("question_id")
    public String getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }


}
