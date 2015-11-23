package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.*;
import java.util.ArrayList;

/**
 * Created by c161 on 20/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TutorialGroup {
	
    private String tutorialGroupId;
    private String tutorialGroupComplete;
    private String tutorialGroupName;
    private String tutorialGroupJoiningStatus;
    private ArrayList<TutorialGroupMember> tutorialGroupMembers;

    @JsonProperty("tutorial_group_id")
    public String getTutorialGroupId() {
        return this.tutorialGroupId;
    }

    public void setTutorialGroupId(String tutorialGroupId) {
        this.tutorialGroupId = tutorialGroupId;
    }

    @JsonProperty("tutorial_group_complete")
    public String getTutorialGroupComplete() {
        return this.tutorialGroupComplete;
    }

    public void setTutorialGroupComplete(String tutorialGroupComplete) {
        this.tutorialGroupComplete = tutorialGroupComplete;
    }

    @JsonProperty("tutorial_group_name")
    public String getTutorialGroupName() {
        return this.tutorialGroupName;
    }

    public void setTutorialGroupName(String tutorialGroupName) {
        this.tutorialGroupName = tutorialGroupName;
    }

    @JsonProperty("tutorial_group_joining_status")
    public String getTutorialGroupJoiningStatus() {
        return this.tutorialGroupJoiningStatus;
    }

    public void setTutorialGroupJoiningStatus(String tutorialGroupJoiningStatus) {
        this.tutorialGroupJoiningStatus = tutorialGroupJoiningStatus;
    }

    @JsonProperty("tutorial_group_members")
    public ArrayList<TutorialGroupMember> getTutorialGroupMembers() {
        return this.tutorialGroupMembers;
    }

    public void setTutorialGroupMembers(ArrayList<TutorialGroupMember> tutorialGroupMembers) {
        this.tutorialGroupMembers = tutorialGroupMembers;
    }

}
