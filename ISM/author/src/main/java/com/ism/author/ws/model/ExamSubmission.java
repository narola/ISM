package com.ism.author.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamSubmission  {

    private String examId;
    private ArrayList<Examsubmittor> examsubmittor;



    @JsonProperty("exam_id")
    public String getExamId() {
        return this.examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    @JsonProperty("examsubmittor")
    public ArrayList<Examsubmittor> getExamsubmittor() {
        return this.examsubmittor;
    }

    public void setExamsubmittor(ArrayList<Examsubmittor> examsubmittor) {
        this.examsubmittor = examsubmittor;
    }




}
