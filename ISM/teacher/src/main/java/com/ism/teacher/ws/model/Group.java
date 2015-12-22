package com.ism.teacher.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by c75 on 22/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {

    private String topicId;
    private String groupRank;
    private String groupId;
    private ArrayList<GroupMembers> groupMembers;
    private String groupScore;
    private String examType;
    private String groupClass;
    private String topicName;
    private String examName;
    private String groupName;
    private String examId;


    @JsonProperty("topic_id")
    public String getTopicId() {
        return this.topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
    @JsonProperty("group_rank")
    public String getGroupRank() {
        return this.groupRank;
    }

    public void setGroupRank(String groupRank) {
        this.groupRank = groupRank;
    }
    @JsonProperty("group_id")
    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @JsonProperty("group_members")
    public ArrayList<GroupMembers> getGroupMembers() {
        return this.groupMembers;
    }

    public void setGroupMembers(ArrayList<GroupMembers> groupMembers) {
        this.groupMembers = groupMembers;
    }


    @JsonProperty("group_score")
    public String getGroupScore() {
        return this.groupScore;
    }

    public void setGroupScore(String groupScore) {
        this.groupScore = groupScore;
    }
    @JsonProperty("exam_type")
    public String getExamType() {
        return this.examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }
    @JsonProperty("group_class")
    public String getGroupClass() {
        return this.groupClass;
    }

    public void setGroupClass(String groupClass) {
        this.groupClass = groupClass;
    }
    @JsonProperty("topic_name")
    public String getTopicName() {
        return this.topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    @JsonProperty("exam_name")
    public String getExamName() {
        return this.examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }
    @JsonProperty("group_name")
    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    @JsonProperty("exam_id")
    public String getExamId() {
        return this.examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }


}
