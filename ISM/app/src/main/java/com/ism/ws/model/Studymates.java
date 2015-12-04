package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c161 on 04/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Studymates {
	
    private String studymateName;
    private String studymateProfilePic;
    private String studymateId;
    private String studymateSchoolName;
    private String displayContent;

    @JsonProperty("studymate_name")
    public String getStudymateName() {
        return this.studymateName;
    }

    public void setStudymateName(String studymateName) {
        this.studymateName = studymateName;
    }

    @JsonProperty("studymate_profile_pic")
    public String getStudymateProfilePic() {
        return this.studymateProfilePic;
    }

    public void setStudymateProfilePic(String studymateProfilePic) {
        this.studymateProfilePic = studymateProfilePic;
    }

    @JsonProperty("studymate_id")
    public String getStudymateId() {
        return this.studymateId;
    }

    public void setStudymateId(String studymateId) {
        this.studymateId = studymateId;
    }

    @JsonProperty("studymate_school_name")
    public String getStudymateSchoolName() {
        return this.studymateSchoolName;
    }

    public void setStudymateSchoolName(String studymateSchoolName) {
        this.studymateSchoolName = studymateSchoolName;
    }

    @JsonProperty("display_content")
    public String getDisplayContent() {
        return this.displayContent;
    }

    public void setDisplayContent(String displayContent) {
        this.displayContent = displayContent;
    }

}
