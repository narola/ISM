package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - tag info for forum questions.
 * Relationship with {@link ROForumQuestion}
 * Relationship with {@link ROTags}
 */
public class ROTagForumQuestion extends RealmObject {


    @PrimaryKey
    private  int tagForumQuestionId;
    private ROForumQuestion roForumQuestion;
    private ROTags tag;
    private Date createdDate;
    private Date modifiedDate;

    public int getTagForumQuestionId() {
        return tagForumQuestionId;
    }

    public void setTagForumQuestionId(int tagForumQuestionId) {
        this.tagForumQuestionId = tagForumQuestionId;
    }

    public ROForumQuestion getRoForumQuestion() {
        return roForumQuestion;
    }

    public void setRoForumQuestion(ROForumQuestion roForumQuestion) {
        this.roForumQuestion = roForumQuestion;
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
