package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by c161 on 24/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TutorialGroupProfile {
	
    private String groupProfilePic;
    private String groupScore;
    private String groupName;
    private String groupRank;
    private String totalActiveComments;
    private ArrayList<TutorialGroupMember> groupMembers;

    @JsonProperty("group_profile_pic")
    public String getGroupProfilePic() {
        return this.groupProfilePic;
    }

    public void setGroupProfilePic(String groupProfilePic) {
        this.groupProfilePic = groupProfilePic;
    }

    @JsonProperty("group_score")
    public String getGroupScore() {
        return this.groupScore;
    }

    public void setGroupScore(String groupScore) {
        this.groupScore = groupScore;
    }

    @JsonProperty("group_name")
    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JsonProperty("group_rank")
    public String getGroupRank() {
        return this.groupRank;
    }

    public void setGroupRank(String groupRank) {
        this.groupRank = groupRank;
    }

    @JsonProperty("total_active_comments")
    public String getTotalActiveComments() {
        return this.totalActiveComments;
    }

    public void setTotalActiveComments(String totalActiveComments) {
        this.totalActiveComments = totalActiveComments;
    }

    @JsonProperty("group_members")
    public ArrayList<TutorialGroupMember> getGroupMembers() {
        return this.groupMembers;
    }

    public void setGroupMembers(ArrayList<TutorialGroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }

}
