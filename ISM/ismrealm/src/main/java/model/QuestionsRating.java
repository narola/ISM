package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle rating related to question.
 * Relationship with {@link Questions}
 * Relationship with {@link User}
 */
public class QuestionsRating extends RealmObject {


    @PrimaryKey
    private  int questionsRatingId;
    private Questions question;
    private User user;
    private int ratingScale;
    private Date createdDate;
    private Date modifiedDate;

    public int getQuestionsRatingId() {
        return questionsRatingId;
    }

    public void setQuestionsRatingId(int questionsRatingId) {
        this.questionsRatingId = questionsRatingId;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
