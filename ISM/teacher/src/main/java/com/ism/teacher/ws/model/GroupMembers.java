package com.ism.teacher.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class GroupMembers {

    private String memberProfilePic;
    private String memberId;
    private String memberScore;
    private String memberSchool;
    private String memberName;

    @JsonProperty("member_profile_pic")
    public String getMemberProfilePic() {
        return this.memberProfilePic;
    }

    public void setMemberProfilePic(String memberProfilePic) {
        this.memberProfilePic = memberProfilePic;
    }

    @JsonProperty("member_id")
    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @JsonProperty("member_score")
    public String getMemberScore() {
        return this.memberScore;
    }

    public void setMemberScore(String memberScore) {
        this.memberScore = memberScore;
    }

    @JsonProperty("member_school")
    public String getMemberSchool() {
        return this.memberSchool;
    }

    public void setMemberSchool(String memberSchool) {
        this.memberSchool = memberSchool;
    }

    @JsonProperty("member_name")
    public String getMemberName() {
        return this.memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }


}
