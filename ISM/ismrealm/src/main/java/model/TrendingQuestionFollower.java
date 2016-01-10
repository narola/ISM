package model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by c162 on 09/01/16.
 */
public class TrendingQuestionFollower extends RealmObject{

    private int questionFollowerId;
    private User followerBy;
    private TrendingQuestion trendingQuestion;
    private Date createdDate;
    private Date modifiedDate;

    public int getQuestionFollowerId() {
        return questionFollowerId;
    }

    public void setQuestionFollowerId(int questionFollowerId) {
        this.questionFollowerId = questionFollowerId;
    }

    public User getFollowerBy() {
        return followerBy;
    }

    public void setFollowerBy(User followerBy) {
        this.followerBy = followerBy;
    }

    public TrendingQuestion getTrendingQuestion() {
        return trendingQuestion;
    }

    public void setTrendingQuestion(TrendingQuestion trendingQuestion) {
        this.trendingQuestion = trendingQuestion;
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
