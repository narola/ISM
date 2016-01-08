package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c161 on 05/01/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {
	
    private int responseDuration;
    private int correctAnswerScore;
    private int questionId;
    private int isRight;
    private int choiceId;
    private String answerStatus;

    @JsonProperty("response_duration")
    public int getResponseDuration() {
        return this.responseDuration;
    }

    public void setResponseDuration(int responseDuration) {
        this.responseDuration = responseDuration;
    }

    @JsonProperty("correct_answer_score")
    public int getCorrectAnswerScore() {
        return this.correctAnswerScore;
    }

    public void setCorrectAnswerScore(int correctAnswerScore) {
        this.correctAnswerScore = correctAnswerScore;
    }

    @JsonProperty("question_id")
    public int getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @JsonProperty("is_right")
    public int getIsRight() {
        return this.isRight;
    }

    public void setIsRight(int isRight) {
        this.isRight = isRight;
    }

    @JsonProperty("choice_id")
    public int getChoiceId() {
        return this.choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    @JsonProperty("answer_status")
    public String getAnswerStatus() {
        return this.answerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        this.answerStatus = answerStatus;
    }
    
}
