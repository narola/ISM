package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle tutorial group score for a weekly exam.
 * Relationship with {@link TutorialTopic},{@link TutorialGroupMember}
 */
public class TutorialGroupScore extends RealmObject {

    @PrimaryKey
    private  int tutorialGroupScoreId;
    private TutorialTopic tutorialTopic;
    private TutorialGroupMember tutorialGroupMember;
    private int score;
    private Date createdDate;
    private Date modifiedDate;

    public int getTutorialGroupScoreId() {
        return tutorialGroupScoreId;
    }

    public void setTutorialGroupScoreId(int tutorialGroupScoreId) {
        this.tutorialGroupScoreId = tutorialGroupScoreId;
    }

    public TutorialTopic getTutorialTopic() {
        return tutorialTopic;
    }

    public void setTutorialTopic(TutorialTopic tutorialTopic) {
        this.tutorialTopic = tutorialTopic;
    }

    public TutorialGroupMember getTutorialGroupMember() {
        return tutorialGroupMember;
    }

    public void setTutorialGroupMember(TutorialGroupMember tutorialGroupMember) {
        this.tutorialGroupMember = tutorialGroupMember;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
