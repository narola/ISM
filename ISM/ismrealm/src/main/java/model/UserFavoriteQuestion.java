package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of users' favorite pastime.
 * Relationship with {@link User}
 * Relationship with {@link Questions}
 */
public class UserFavoriteQuestion extends RealmObject {
    @PrimaryKey
    private  int userFavoriteQuestionId;
    private User user;
    private Questions question;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserFavoriteQuestionId() {
        return userFavoriteQuestionId;
    }

    public void setUserFavoriteQuestionId(int userFavoriteQuestionId) {
        this.userFavoriteQuestionId = userFavoriteQuestionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
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
