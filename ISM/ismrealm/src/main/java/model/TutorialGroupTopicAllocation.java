package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class for handle allocated topic to the group.
 * Relationship with {@link TutorialGroup},{@link Topics}
 */
public class TutorialGroupTopicAllocation extends RealmObject {

    @PrimaryKey
    private  int tutorialGroupTopicId;
    private String interfaceType;
    private String dateDay;
    private String status;
    private int weekNumber;
    private int groupScore;
    private TutorialGroup tutorialGroup;
    private Topics topic;
    private Date createdDate;
    private Date modifiedDate;

    public int getTutorialGroupTopicId() {
        return tutorialGroupTopicId;
    }

    public void setTutorialGroupTopicId(int tutorialGroupTopicId) {
        this.tutorialGroupTopicId = tutorialGroupTopicId;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getGroupScore() {
        return groupScore;
    }

    public void setGroupScore(int groupScore) {
        this.groupScore = groupScore;
    }

    public TutorialGroup getTutorialGroup() {
        return tutorialGroup;
    }

    public void setTutorialGroup(TutorialGroup tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    public Topics getTopic() {
        return topic;
    }

    public void setTopic(Topics topic) {
        this.topic = topic;
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
