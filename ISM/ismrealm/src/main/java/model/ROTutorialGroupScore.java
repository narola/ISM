package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle tutorial group score for a weekly exam.
 * Relationship with {@link ROTutorialTopic},{@link ROTutorialGroupMember}
 */
public class ROTutorialGroupScore extends RealmObject {

    @PrimaryKey
    private  int tutorialGroupScoreId;
    private ROTutorialTopic roTutorialTopic;
    private ROTutorialGroupMember roTutorialGroupMember;
    private int score;
    private Date createdDate;
    private Date modifiedDate;

    public int getTutorialGroupScoreId() {
        return tutorialGroupScoreId;
    }

    public void setTutorialGroupScoreId(int tutorialGroupScoreId) {
        this.tutorialGroupScoreId = tutorialGroupScoreId;
    }

    public ROTutorialTopic getRoTutorialTopic() {
        return roTutorialTopic;
    }

    public void setRoTutorialTopic(ROTutorialTopic roTutorialTopic) {
        this.roTutorialTopic = roTutorialTopic;
    }

    public ROTutorialGroupMember getRoTutorialGroupMember() {
        return roTutorialGroupMember;
    }

    public void setRoTutorialGroupMember(ROTutorialGroupMember roTutorialGroupMember) {
        this.roTutorialGroupMember = roTutorialGroupMember;
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
