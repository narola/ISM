package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - contain multiple choices related to question.
 * Relationship with {@link ROQuestions}
 */
public class ROAnswerChoices extends RealmObject {


    @PrimaryKey
    private int answerChoicesId;
    private int questionId;
    private String choiceText;
    private boolean isRight;
    private String imageLink;
    private String audioLink;
    private String videoLink;
    private ROQuestions roQuestions;
    private Date createdDate;
    private Date modifiedDate;

    public int getAnswerChoicesId() {
        return answerChoicesId;
    }

    public void setAnswerChoicesId(int answerChoicesId) {
        this.answerChoicesId = answerChoicesId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;

    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public ROQuestions getRoQuestions() {
        return roQuestions;
    }

    public void setRoQuestions(ROQuestions roQuestions) {
        this.roQuestions = roQuestions;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
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
