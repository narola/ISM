package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of users' favorite pastime.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROQuestions}
 */
public class ROUserFavoriteQuestion extends RealmObject {
    @PrimaryKey
    private  int userFavoriteQuestionId;
    private ROUser roUser;
    private ROQuestions question;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserFavoriteQuestionId() {
        return userFavoriteQuestionId;
    }

    public void setUserFavoriteQuestionId(int userFavoriteQuestionId) {
        this.userFavoriteQuestionId = userFavoriteQuestionId;
    }

    public ROUser getRoUser() {
        return roUser;
    }

    public void setRoUser(ROUser roUser) {
        this.roUser = roUser;
    }

    public ROQuestions getQuestion() {
        return question;
    }

    public void setQuestion(ROQuestions question) {
        this.question = question;
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
