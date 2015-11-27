package com.ism.teacher.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamEvaluation {

    private ArrayList<String> questionPalette;
    private String examScore;
    private String examId;
    private ArrayList<Evaluation> evaluation;


    @JsonProperty("question_palette")
    public ArrayList<String> getQuestionPalette() {
        return this.questionPalette;
    }

    public void setQuestionPalette(ArrayList<String> questionPalette) {
        this.questionPalette = questionPalette;
    }

    @JsonProperty("exam_score")
    public String getExamScore() {
        return this.examScore;
    }

    public void setExamScore(String examScore) {
        this.examScore = examScore;
    }

    @JsonProperty("exam_id")
    public String getExamId() {
        return this.examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    @JsonProperty("evaluation")
    public ArrayList<Evaluation> getEvaluation() {
        return this.evaluation;
    }

    public void setEvaluation(ArrayList<Evaluation> evaluation) {
        this.evaluation = evaluation;
    }


}
