package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c162 on 10/01/16.
 */
public class TrendingQuestionComment extends RealmObject {
    @PrimaryKey
    private int trendingQuestionCommentId;
    private TrendingQuestion trendingQuestion;
    private User commentBy;
    private String comment;
    private Date createdDate;
    private Date modifiedDate;

    public int getTrendingQuestionCommentId() {
        return trendingQuestionCommentId;
    }

    public void setTrendingQuestionCommentId(int trendingQuestionCommentId) {
        this.trendingQuestionCommentId = trendingQuestionCommentId;
    }

    public TrendingQuestion getTrendingQuestion() {
        return trendingQuestion;
    }

    public void setTrendingQuestion(TrendingQuestion trendingQuestion) {
        this.trendingQuestion = trendingQuestion;
    }

    public User getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(User commentBy) {
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
