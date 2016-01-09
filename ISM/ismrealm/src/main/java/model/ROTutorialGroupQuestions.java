package model;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of question assign to tutorial group.
 * Relationship with {@link ROQuestions},{@link ROTutorialTopic}
 */
public class ROTutorialGroupQuestions extends RealmObject {

    @PrimaryKey
    private  int tutorialGroupQuestionId;
    private ROQuestions question;
    private ROTutorialTopic roTutorialTopic;
    private Date createdDate;
    private Date modifiedDate;

    public int getTutorialGroupQuestionId() {
        return tutorialGroupQuestionId;
    }

    public void setTutorialGroupQuestionId(int tutorialGroupQuestionId) {
        this.tutorialGroupQuestionId = tutorialGroupQuestionId;
    }

    public ROQuestions getQuestion() {
        return question;
    }

    public void setQuestion(ROQuestions question) {
        this.question = question;
    }

    public ROTutorialTopic getRoTutorialTopic() {
        return roTutorialTopic;
    }

    public void setRoTutorialTopic(ROTutorialTopic roTutorialTopic) {
        this.roTutorialTopic = roTutorialTopic;
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
