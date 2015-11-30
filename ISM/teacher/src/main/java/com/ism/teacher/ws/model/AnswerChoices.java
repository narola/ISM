package com.ism.teacher.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerChoices {

    private String isRight;
    private String choiceText;


    public String getIsRight() {
        return this.isRight;
    }

    @JsonProperty("is_right")
    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }

    public String getChoiceText() {
        return this.choiceText;
    }

    @JsonProperty("choice_text")
    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }


}
