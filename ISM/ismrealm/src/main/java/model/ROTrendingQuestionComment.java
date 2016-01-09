package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c162 on 10/01/16.
 */
public class ROTrendingQuestionComment extends RealmObject {
    @PrimaryKey
    private int trendingQuestionCommentId;
    private ROTrendingQuestion ROTrendingQuestion;
    private ROUser commentBy;
    private String comment;
    private Date createdDate;
    private Date modifiedDate;

    public int getTrendingQuestionCommentId() {
        return trendingQuestionCommentId;
    }

    public void setTrendingQuestionCommentId(int trendingQuestionCommentId) {
        this.trendingQuestionCommentId = trendingQuestionCommentId;
    }

    public ROTrendingQuestion getROTrendingQuestion() {
        return ROTrendingQuestion;
    }

    public void setROTrendingQuestion(ROTrendingQuestion ROTrendingQuestion) {
        this.ROTrendingQuestion = ROTrendingQuestion;
    }

    public ROUser getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(ROUser commentBy) {
        this.commentBy = commentBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
