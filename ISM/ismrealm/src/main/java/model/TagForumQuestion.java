package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - tag info for forum questions.
 * Relationship with {@link ForumQuestion}
 * Relationship with {@link Tags}
 */
public class TagForumQuestion extends RealmObject {


    @PrimaryKey
    private  int tagForumQuestionId;
    private ForumQuestion forumQuestion;
    private Tags tag;
    private Date createdDate;
    private Date modifiedDate;

    public int getTagForumQuestionId() {
        return tagForumQuestionId;
    }

    public void setTagForumQuestionId(int tagForumQuestionId) {
        this.tagForumQuestionId = tagForumQuestionId;
    }

    public ForumQuestion getForumQuestion() {
        return forumQuestion;
    }

    public void setForumQuestion(ForumQuestion forumQuestion) {
        this.forumQuestion = forumQuestion;
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
