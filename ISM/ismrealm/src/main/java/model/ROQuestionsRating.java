package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle rating related to question.
 * Relationship with {@link ROQuestions}
 * Relationship with {@link ROUser}
 */
public class ROQuestionsRating extends RealmObject {


    @PrimaryKey
    private  int questionsRatingId;
    private ROQuestions question;
    private ROUser ROUser;
    private int ratingScale;
    private Date createdDate;
    private Date modifiedDate;

    public int getQuestionsRatingId() {
        return questionsRatingId;
    }

    public void setQuestionsRatingId(int questionsRatingId) {
        this.questionsRatingId = questionsRatingId;
    }

    public ROQuestions getQuestion() {
        return question;
    }

    public void setQuestion(ROQuestions question) {
        this.question = question;
    }

    public ROUser getROUser() {
        return ROUser;
    }

    public void setROUser(ROUser ROUser) {
        this.ROUser = ROUser;
    }

    public int getRatingScale() {
        return ratingScale;
    }

    public void setRatingScale(int ratingScale) {
        this.ratingScale = ratingScale;
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
