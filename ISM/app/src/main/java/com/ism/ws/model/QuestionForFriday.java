package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.*;
import java.util.ArrayList;

/**
 * Created by c161 on 29/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionForFriday {
	
    private double examId;
    private double questionId;
    private ArrayList<Integer> answerIds;

    @JsonProperty("exam_id")
    public double getExamId() {
        return this.examId;
    }

    public void setExamId(double examId) {
        this.examId = examId;
    }

    @JsonProperty("question_id")
    public double getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(double questionId) {
        this.questionId = questionId;
    }

    @JsonProperty("answer_ids")
    public ArrayList<Integer> getAnswerIds() {
        return this.answerIds;
    }

    public void setAnswerIds(ArrayList<Integer> answerIds) {
        this.answerIds = answerIds;
    }

}
