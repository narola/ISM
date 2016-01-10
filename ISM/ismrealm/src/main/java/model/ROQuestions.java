package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class of questions related to tutorial group.
 * Relationship with {@link ROUser},{@link ROClassrooms},{@link ROTopics},{@link ROSubjects},{@link ROBooks}
 */
public class ROQuestions extends RealmObject {

    @PrimaryKey
    private  int questionId;
    private String questionText;
    private String questionHint;
    private String assetsLink;
    private String questionImageLink;
    private String videoLink;
    private String evaluationNotes;
    private String solution;
    private int difficultyLevel;
    private int questionScore;
    private int questionFormat;
    private ROUser questionCreator;
    private ROTopics topic;
    private ROSubjects subject;
    private ROClassrooms classroom;
    private ROBooks book;
    private Date createdDate;
    private Date modifiedDate;


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionHint() {
        return questionHint;
    }

    public void setQuestionHint(String questionHint) {
        this.questionHint = questionHint;
    }

    public String getAssetsLink() {
        return assetsLink;
    }

    public void setAssetsLink(String assetsLink) {
        this.assetsLink = assetsLink;
    }

    public String getQuestionImageLink() {
        return questionImageLink;
    }

    public void setQuestionImageLink(String questionImageLink) {
        this.questionImageLink = questionImageLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getEvaluationNotes() {
        return evaluationNotes;
    }

    public void setEvaluationNotes(String evaluationNotes) {
        this.evaluationNotes = evaluationNotes;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(int questionScore) {
        this.questionScore = questionScore;
    }

    public int getQuestionFormat() {
        return questionFormat;
    }

    public void setQuestionFormat(int questionFormat) {
        this.questionFormat = questionFormat;
    }

    public ROUser getQuestionCreator() {
        return questionCreator;
    }

    public void setQuestionCreator(ROUser questionCreator) {
        this.questionCreator = questionCreator;
    }

    public ROTopics getTopic() {
        return topic;
    }

    public void setTopic(ROTopics topic) {
        this.topic = topic;
    }

    public ROSubjects getSubject() {
        return subject;
    }

    public void setSubject(ROSubjects subject) {
        this.subject = subject;
    }

    public ROClassrooms getClassroom() {
        return classroom;
    }

    public void setClassroom(ROClassrooms classroom) {
        this.classroom = classroom;
    }

    public ROBooks getBook() {
        return book;
    }

    public void setBook(ROBooks book) {
        this.book = book;
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
