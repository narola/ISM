package com.ism.teacher.ws.helper;

/**
 * Created by c166 on 04/12/15.
 */
public class MediaUploadAttribute {

    private String paramName;
    private String paramValue;
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


}
