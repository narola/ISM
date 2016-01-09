package model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by c162 on 09/01/16.
 */
public class ROTrendingQuestionFollower extends RealmObject{

    private int questionFollowerId;
    private ROUser followerBy;
    private ROTrendingQuestion ROTrendingQuestion;
    private Date createdDate;
    private Date modifiedDate;

    public int getQuestionFollowerId() {
        return questionFollowerId;
    }

    public void setQuestionFollowerId(int questionFollowerId) {
        this.questionFollowerId = questionFollowerId;
    }

    public ROUser getFollowerBy() {
        return followerBy;
    }

    public void setFollowerBy(ROUser followerBy) {
        this.followerBy = followerBy;
    }

    public ROTrendingQuestion getROTrendingQuestion() {
        return ROTrendingQuestion;
    }

    public void setROTrendingQuestion(ROTrendingQuestion ROTrendingQuestion) {
        this.ROTrendingQuestion = ROTrendingQuestion;
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
