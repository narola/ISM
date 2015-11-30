package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of questions in forum.
 * Relationship with {@link User}
 */
public class ForumQuestion extends RealmObject {

    @PrimaryKey
    private  int forumQuestionId;
    private String questionTitle;
    private String questionText;
    private String videoLink;
    private String status;
    private User postedBy;
    private User questionFor;
    private int totalAnswer;
    private int totalUpvote;
    private int totalDownvote;
    private Date createdDate;
    private Date modifiedDate;

    public int getForumQuestionId() {
        return forumQuestionId;
    }

    public void setForumQuestionId(int forumQuestionId) {
        this.forumQuestionId = forumQuestionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public User getQuestionFor() {
        return questionFor;
    }

    public void setQuestionFor(User questionFor) {
        this.questionFor = questionFor;
    }

    public int getTotalAnswer() {
        return totalAnswer;
    }

    public void setTotalAnswer(int totalAnswer) {
        this.totalAnswer = totalAnswer;
    }

    public int getTotalUpvote() {
        return totalUpvote;
    }

    public void setTotalUpvote(int totalUpvote) {
        this.totalUpvote = totalUpvote;
    }

    public int getTotalDownvote() {
        return totalDownvote;
    }

    public void setTotalDownvote(int totalDownvote) {
        this.totalDownvote = totalDownvote;
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
