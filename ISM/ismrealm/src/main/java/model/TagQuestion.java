package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - tag info for question.
 * Relationship with {@link Questions}
 * Relationship with {@link Tags}
 */
public class TagQuestion extends RealmObject {

    @PrimaryKey
    private  int tagQuestionId;
    private Questions question;
    private Tags tag;
    private Date createdDate;
    private Date modifiedDate;

    public int getTagQuestionId() {
        return tagQuestionId;
    }

    public void setTagQuestionId(int tagQuestionId) {
        this.tagQuestionId = tagQuestionId;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
    }

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
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
