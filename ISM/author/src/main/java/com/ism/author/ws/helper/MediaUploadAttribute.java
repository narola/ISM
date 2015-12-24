package com.ism.author.ws.helper;

import com.ism.author.ws.model.AnswerChoices;

import java.util.ArrayList;

/**
 * Created by c162 on 08/12/15.
 */
public class MediaUploadAttribute {

    private String paramName;
    private String paramValue;
    private ArrayList<AnswerChoices> arrListMcqAnswerValue;
    private String fileName;


    public String getParamValue() {
        return paramValue;
    }

    public MediaUploadAttribute setParamValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }

    public String getParamName() {
        return paramName;
    }

    public MediaUploadAttribute setParamName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public MediaUploadAttribute setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public ArrayList<AnswerChoices> getArrListMcqAnswerValue() {
        return arrListMcqAnswerValue;
    }

    public MediaUploadAttribute setArrListMcqAnswerValue(ArrayList<AnswerChoices> arrListMcqAnswerValue) {
        this.arrListMcqAnswerValue = arrListMcqAnswerValue;
        return this;
    }


}
