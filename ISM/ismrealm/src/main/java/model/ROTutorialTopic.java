package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class for different topic related to tutorials.
 * Relationship with {@link ROTutorialTopic},{@link ROClassrooms},{@link ROUser},{@link ROSubjects},{@link ROTopics}
 */
public class ROTutorialTopic extends RealmObject {

    @PrimaryKey
    private int tutorialTopicId;
    private int serverTutorialTopicId;
    private String topicName;
    private String topicDescription;
    private String evaluationKeyword;

    private ROTutorialGroupTopicAllocation tutorialGroupTopicAllocation;
//    private Classrooms classroom;
//    private User createdBy;
//    private Subjects subject;
//    private Topics topic;

    private ROTutorialTopic parent;
    private ROClassrooms classroom;
    private ROUser createdBy;
    private ROSubjects subject;
    private ROTopics topic;

    private String topicDay;
    private int allocationCount;
//	private int createdBy;
    private int status;
    private boolean isCompleted;
    private Date createdDate;
    private Date modifiedDate;

    public int getTutorialTopicId() {
        return tutorialTopicId;
    }

    public void setTutorialTopicId(int tutorialTopicId) {
        this.tutorialTopicId = tutorialTopicId;
    }

    public int getServerTutorialTopicId() {
        return serverTutorialTopicId;
    }

    public void setServerTutorialTopicId(int serverTutorialTopicId) {
        this.serverTutorialTopicId = serverTutorialTopicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public String getEvaluationKeyword() {
        return evaluationKeyword;
    }

    public void setEvaluationKeyword(String evaluationKeyword) {
        this.evaluationKeyword = evaluationKeyword;
    }


    public ROTutorialGroupTopicAllocation getTutorialGroupTopicAllocation() {
        return tutorialGroupTopicAllocation;
    }

    public void setTutorialGroupTopicAllocation(ROTutorialGroupTopicAllocation tutorialGroupTopicAllocation) {
        this.tutorialGroupTopicAllocation = tutorialGroupTopicAllocation;
    }

    public ROTutorialTopic getParent() {
        return parent;
    }

    public void setParent(ROTutorialTopic parent) {

        this.parent = parent;
    }

    public ROClassrooms getClassroom() {
        return classroom;
    }

    public void setClassroom(ROClassrooms classroom) {
        this.classroom = classroom;
    }


//    public User getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(User createdBy) {
//        this.createdBy = createdBy;


    public ROUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ROUser createdBy) {
        this.createdBy = createdBy;
    }

    public ROSubjects getSubject() {
        return subject;
    }

    public void setSubject(ROSubjects subject) {
        this.subject = subject;
    }

    public ROTopics getTopic() {
        return topic;
    }

    public void setTopic(ROTopics topic) {
        this.topic = topic;
    }

    public String getTopicDay() {
        return topicDay;
    }

    public void setTopicDay(String topicDay) {
        this.topicDay = topicDay;
    }

    public int getAllocationCount() {
        return allocationCount;
    }

    public void setAllocationCount(int allocationCount) {
        this.allocationCount = allocationCount;
    }

//	public int getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(int createdBy) {
//		this.createdBy = createdBy;
//	}

	public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
