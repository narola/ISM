package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of exams.
 * Relationship with {@link Classrooms}
 * Relationship with {@link User}
 * Relationship with {@link Subjects}
 * Relationship with {@link Books}
 */
public class Exam extends RealmObject {


    @PrimaryKey
    private  int examId;
    private String examName;
    private String instructions;
    private Classrooms classroom;
    private User createdBy;
    private Subjects subject;
    private Books    book;
    private int examType;
    private int examCategory;
    private int examMode;
    private int passPercentage;
    private int duration;
    private int attemptCount;
    private int negativeMarkValue;
    private int correctAnswerScore;
    private boolean negativeMarking;
    private boolean useQuestionScore;
    private boolean randomQuestion;
    private boolean declareResults;
    private Date createdDate;
    private Date modifiedDate;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Classrooms getClassroom() {
        return classroom;
    }

    public void setClassroom(Classrooms classroom) {
        this.classroom = classroom;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public int getExamType() {
        return examType;
    }

    public void setExamType(int examType) {
        this.examType = examType;
    }

    public int getExamCategory() {
        return examCategory;
    }

    public void setExamCategory(int examCategory) {
        this.examCategory = examCategory;
    }

    public int getExamMode() {
        return examMode;
    }

    public void setExamMode(int examMode) {
        this.examMode = examMode;
    }

    public int getPassPercentage() {
        return passPercentage;
    }

    public void setPassPercentage(int passPercentage) {
        this.passPercentage = passPercentage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public int getNegativeMarkValue() {
        return negativeMarkValue;
    }

    public void setNegativeMarkValue(int negativeMarkValue) {
        this.negativeMarkValue = negativeMarkValue;
    }

    public int getCorrectAnswerScore() {
        return correctAnswerScore;
    }

    public void setCorrectAnswerScore(int correctAnswerScore) {
        this.correctAnswerScore = correctAnswerScore;
    }

    public boolean isNegativeMarking() {
        return negativeMarking;
    }

    public void setNegativeMarking(boolean negativeMarking) {
        this.negativeMarking = negativeMarking;
    }

    public boolean isUseQuestionScore() {
        return useQuestionScore;
    }

    public void setUseQuestionScore(boolean useQuestionScore) {
        this.useQuestionScore = useQuestionScore;
    }

    public boolean isRandomQuestion() {
        return randomQuestion;
    }

    public void setRandomQuestion(boolean randomQuestion) {
        this.randomQuestion = randomQuestion;
    }

    public boolean isDeclareResults() {
        return declareResults;
    }

    public void setDeclareResults(boolean declareResults) {
        this.declareResults = declareResults;
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
