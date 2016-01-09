package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - tag info for question.
 * Relationship with {@link ROQuestions}
 * Relationship with {@link ROTags}
 */
public class ROTagQuestion extends RealmObject {

    @PrimaryKey
    private  int tagQuestionId;
    private ROQuestions question;
    private ROTags tag;
    private Date createdDate;
    private Date modifiedDate;

    public int getTagQuestionId() {
        return tagQuestionId;
    }

    public void setTagQuestionId(int tagQuestionId) {
        this.tagQuestionId = tagQuestionId;
    }

    public ROQuestions getQuestion() {
        return question;
    }

    public void setQuestion(ROQuestions question) {
        this.question = question;
    }

    public ROTags getTag() {
        return tag;
    }

    public void setTag(ROTags tag) {
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
