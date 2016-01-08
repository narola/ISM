package model;

import java.util.Date;

import io.realm.RealmList;
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
    private String dateDay;
    private String status;
    private int weekNumber;
    private int groupScore;
    private TutorialGroup tutorialGroup;
    private TutorialTopic tutorialTopic;
    private Date createdDate;
    private Date modifiedDate;

    public int getTutorialGroupTopicId() {
        return tutorialGroupTopicId;
    }

    public void setTutorialGroupTopicId(int tutorialGroupTopicId) {
        this.tutorialGroupTopicId = tutorialGroupTopicId;
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

    public TutorialTopic getTutorialTopic() {
        return tutorialTopic;
    }

    public void setTutorialTopic(TutorialTopic tutorialTopic) {
        this.tutorialTopic = tutorialTopic;
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
