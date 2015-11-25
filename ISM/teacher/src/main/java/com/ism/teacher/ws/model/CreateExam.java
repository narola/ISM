package com.ism.teacher.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateExam {

    @JsonProperty("exam_id")
    private double examId;

    public double getExamId() {
        return this.examId;
    }

    public void setExamId(double examId) {
        this.examId = examId;
    }


}
