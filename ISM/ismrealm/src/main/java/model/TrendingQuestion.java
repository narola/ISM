package model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c162 on 09/01/16.
 */
public class TrendingQuestion extends RealmObject {

    @PrimaryKey
    private int trendingId;
    private String questionText;
    private String answerText;
    private User questionBy;
    private User questionFor;
    private RealmList<TrendingQuestionComment> trendingQuestionComments=new RealmList<>();
    private int followerCount;
    private int isFollowed;
    private int totalComment;
    private Date createdDate;
    private Date modifiedDate;

    public int getTrendingId() {
        return trendingId;
    }

    public void setTrendingId(int trendingId) {
        this.trendingId = trendingId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public User getQuestionBy() {
        return questionBy;
    }

    public void setQuestionBy(User questionBy) {
        this.questionBy = questionBy;
    }

    public User getQuestionFor() {
        return questionFor;
    }

    public void setQuestionFor(User questionFor) {
        this.questionFor = questionFor;
    }

    public RealmList<TrendingQuestionComment> getTrendingQuestionComments() {
        return trendingQuestionComments;
    }

    public void setTrendingQuestionComments(RealmList<TrendingQuestionComment> trendingQuestionComments) {
        this.trendingQuestionComments = trendingQuestionComments;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(int isFollowed) {
        this.isFollowed = isFollowed;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
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
