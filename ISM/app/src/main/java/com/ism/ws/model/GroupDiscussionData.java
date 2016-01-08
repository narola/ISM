package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by c161 on 22/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDiscussionData {

	private String tutorialTopic;
	private String groupScore;
	private String totalActiveComments;
	private String topicDescription;
	private String assignedTime;
	private String tutorialTopicId;
	private String tutorialTopicAllocationId;
	private String assignedBy;
	private String isCurrentDay;
	private String subjectName;
	private ArrayList<Discussion> discussion;
	private String totalActiveCommentsScore;
	private String interfaceType;
	private String dayName;
	private String subjectId;
	private String weekDay;
	private String weekNumber;

	@JsonProperty("tutorial_topic")
	public String getTutorialTopic() {
		return this.tutorialTopic;
	}

	public void setTutorialTopic(String tutorialTopic) {
		this.tutorialTopic = tutorialTopic;
	}

	@JsonProperty("group_score")
	public String getGroupScore() {
		return this.groupScore;
	}

	public void setGroupScore(String groupScore) {
		this.groupScore = groupScore;
	}

	@JsonProperty("total_active_comments")
	public String getTotalActiveComments() {
		return this.totalActiveComments;
	}

	public void setTotalActiveComments(String totalActiveComments) {
		this.totalActiveComments = totalActiveComments;
	}

	@JsonProperty("topic_description")
	public String getTopicDescription() {
		return this.topicDescription;
	}

	public void setTopicDescription(String topicDescription) {
		this.topicDescription = topicDescription;
	}

	@JsonProperty("assigned_time")
	public String getAssignedTime() {
		return this.assignedTime;
	}

	public void setAssignedTime(String assignedTime) {
		this.assignedTime = assignedTime;
	}

	@JsonProperty("tutorial_topic_id")
	public String getTutorialTopicId() {
		return this.tutorialTopicId;
	}

	public void setTutorialTopicId(String tutorialTopicId) {
		this.tutorialTopicId = tutorialTopicId;
	}

	@JsonProperty("assigned_by")
	public String getAssignedBy() {
		return this.assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	@JsonProperty("is_current_day")
	public String getIsCurrentDay() {
		return this.isCurrentDay;
	}

	public void setIsCurrentDay(String isCurrentDay) {
		this.isCurrentDay = isCurrentDay;
	}

	@JsonProperty("subject_name")
	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@JsonProperty("discussion")
	public ArrayList<Discussion> getDiscussion() {
		return this.discussion;
	}

	public void setDiscussion(ArrayList<Discussion> discussion) {
		this.discussion = discussion;
	}

	@JsonProperty("total_active_comments_score")
	public String getTotalActiveCommentsScore() {
		return this.totalActiveCommentsScore;
	}

	public void setTotalActiveCommentsScore(String totalActiveCommentsScore) {
		this.totalActiveCommentsScore = totalActiveCommentsScore;
	}

	@JsonProperty("interface_type")
	public String getInterfaceType() {
		return this.interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	@JsonProperty("day_name")
	public String getDayName() {
		return this.dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	@JsonProperty("subject_id")
	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	@JsonProperty("tutorial_topic_allocation_id")
	public String getTutorialTopicAllocationId() {
		return tutorialTopicAllocationId;
	}

	public void setTutorialTopicAllocationId(String tutorialTopicAllocationId) {
		this.tutorialTopicAllocationId = tutorialTopicAllocationId;
	}
	@JsonProperty("week_day")
	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	@JsonProperty("week_number")
	public String getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(String weekNumber) {
		this.weekNumber = weekNumber;
	}
}
