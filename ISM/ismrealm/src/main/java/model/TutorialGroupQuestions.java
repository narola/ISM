package model;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of question assign to tutorial group.
 * Relationship with {@link Questions},{@link TutorialTopic}
 */
public class TutorialGroupQuestions extends RealmObject {

    @PrimaryKey
    private  int tutorialGroupQuestionId;
    private Questions question;
    private TutorialTopic tutorialTopic;
    private Date createdDate;
    private Date modifiedDate;

    public int getTutorialGroupQuestionId() {
        return tutorialGroupQuestionId;
    }

    public void setTutorialGroupQuestionId(int tutorialGroupQuestionId) {
        this.tutorialGroupQuestionId = tutorialGroupQuestionId;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
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
