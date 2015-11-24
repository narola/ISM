package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Created by c161 on 19/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PastimeData {

    private String pastimeId;
    private String pastimeName;
    private String pastimeImage;

    @JsonProperty("pastime_id")
    public String getPastimeId() {
        return this.pastimeId;
    }

    public void setPastimeId(String pastimeId) {
        this.pastimeId = pastimeId;
    }
    @JsonProperty("pastime_name")
    public String getPastimeName() {
        return this.pastimeName;
    }

    public void setPastimeName(String pastimeName) {
        this.pastimeName = pastimeName;
    }
    @JsonProperty("pastime_image")
    public String getPastimeImage() {
        return this.pastimeImage;
    }

    public void setPastimeImage(String pastimeImage) {
        this.pastimeImage = pastimeImage;
    }



}
