package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - comments related to forum questions.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROForumQuestion}
 */
public class ROForumQuestionComment extends RealmObject {

    @PrimaryKey
    private  int forumQuestionCommentId;
    private String comment;
    private ROUser commentBy;
    private ROForumQuestion roForumQuestion;
    private Date createdDate;
    private Date modifiedDate;

    public int getForumQuestionCommentId() {
        return forumQuestionCommentId;
    }

    public void setForumQuestionCommentId(int forumQuestionCommentId) {
        this.forumQuestionCommentId = forumQuestionCommentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ROUser getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(ROUser commentBy) {
        this.commentBy = commentBy;
    }

    public ROForumQuestion getRoForumQuestion() {
        return roForumQuestion;
    }

    public void setRoForumQuestion(ROForumQuestion roForumQuestion) {
        this.roForumQuestion = roForumQuestion;
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
