package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle different topics.
 * Relationship with {@link ROSubjects}
 */
public class ROTopics extends RealmObject {

    @PrimaryKey
    private int topicId;
    private int serverTopicId;
    private String topicName;
    private String topicDescription;
    private ROSubjects subject;
    private Date createdDate;
    private Date modifiedDate;

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getServerTopicId() {
        return serverTopicId;
    }

    public void setServerTopicId(int serverTopicId) {
        this.serverTopicId = serverTopicId;
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

    public ROSubjects getSubject() {
        return subject;
    }

    public void setSubject(ROSubjects subject) {
        this.subject = subject;
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
