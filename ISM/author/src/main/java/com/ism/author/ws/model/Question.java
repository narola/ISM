package com.ism.author.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {

    private String questionId;
    private String hashtag;
    private String richTextEditorImages;


    @JsonProperty("question_id")
    public String getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @JsonProperty("hasgtag")
    public String getHashtag() {
        return hashtag;
    }

    public Question setHashtag(String hashtag) {
        this.hashtag = hashtag;
        return this;
    }

    @JsonProperty("rich_text_editor_images")
    public String getRichTextEditorImages() {
        return this.richTextEditorImages;
    }

    public void setRichTextEditorImages(String richTextEditorImages) {
        this.richTextEditorImages = richTextEditorImages;
    }

}
