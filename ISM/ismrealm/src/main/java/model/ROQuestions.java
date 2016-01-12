package model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class of questions related to tutorial group.
 * Relationship with {@link ROUser},{@link ROClassrooms},{@link ROTopics},{@link ROSubjects},{@link ROBooks}
 */
public class ROQuestions extends RealmObject {


    @PrimaryKey
    private int questionId;
    private ROUser questionCreator;
    private ROExam roExam;
    private String questionFormat;
    private String questionHint;
    private String questionText;
    private String assetsLink;
    private String questionImageLink;
    private String questionScore;
    private String evaluationNotes;
    private String solution;
    private ROTopics roTopic;
    private ROSubjects roSubject;
    private ROClassrooms roClassroom;
    private ROBooks roBook;
    private RealmList<ROAnswerChoices> roAnswerChoices = new RealmList<ROAnswerChoices>();
    private RealmList<ROTags> roTags = new RealmList<ROTags>();
    private String roSpannedText;


    private String videoLink;
    private int difficultyLevel;
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

    public String getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(String questionScore) {
        this.questionScore = questionScore;
    }

    public String getQuestionFormat() {
        return questionFormat;
    }

    public void setQuestionFormat(String questionFormat) {
        this.questionFormat = questionFormat;
    }

    public ROUser getQuestionCreator() {
        return questionCreator;
    }

    public void setQuestionCreator(ROUser questionCreator) {
        this.questionCreator = questionCreator;
    }

    public ROTopics getRoTopic() {
        return roTopic;
    }

    public void setRoTopic(ROTopics roTopic) {
        this.roTopic = roTopic;
    }

    public ROSubjects getRoSubject() {
        return roSubject;
    }

    public void setRoSubject(ROSubjects roSubject) {
        this.roSubject = roSubject;
    }

    public ROClassrooms getRoClassroom() {
        return roClassroom;
    }

    public void setRoClassroom(ROClassrooms roClassroom) {
        this.roClassroom = roClassroom;
    }

    public ROBooks getRoBook() {
        return roBook;
    }

    public void setRoBook(ROBooks roBook) {
        this.roBook = roBook;
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

    public ROExam getRoExam() {
        return roExam;
    }

    public void setRoExam(ROExam roExam) {
        this.roExam = roExam;
    }

    public RealmList<ROAnswerChoices> getRoAnswerChoices() {
        return roAnswerChoices;
    }

    public void setRoAnswerChoices(RealmList<ROAnswerChoices> roAnswerChoices) {
        this.roAnswerChoices = roAnswerChoices;

    }

    public RealmList<ROTags> getRoTags() {
        return roTags;
    }

    public void setRoTags(RealmList<ROTags> roTags) {
        this.roTags = roTags;
    }

    public String getRoSpannedText() {
        return roSpannedText;
    }

    public void setRoSpannedText(String roSpannedText) {
        this.roSpannedText = roSpannedText;

    }


}
