package com.ism.author.ws.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Questions implements Parcelable {

    private String questionFormat;
    private String questionAssetsLink;
    private String questionHint;
    private String questionImageLink;
    private String evaluationNotes;
    private String topicId;
    private ArrayList<Answers> answers;
    private String classroomId;
    private String subjectId;
    private String bookId;
    private String solution;
    private String questionId;
    private String questionText;
    private String questionCreatorId;
    private String questionCreatorName;
    private String subjectName;
    private Boolean isQuestionAddedInPreview = false;

    public Questions() {
    }

    String answerString;

    public Questions(Parcel parcelQuestions) {
        this.questionFormat = parcelQuestions.readString();
        this.questionAssetsLink = parcelQuestions.readString();
        this.questionHint = parcelQuestions.readString();
        this.questionImageLink = parcelQuestions.readString();
        this.evaluationNotes = parcelQuestions.readString();
        this.topicId = parcelQuestions.readString();
        this.classroomId = parcelQuestions.readString();
        this.subjectId = parcelQuestions.readString();
        this.bookId = parcelQuestions.readString();
        this.solution = parcelQuestions.readString();
        this.questionId = parcelQuestions.readString();
        this.questionText = parcelQuestions.readString();
        this.questionCreatorId = parcelQuestions.readString();
        this.questionCreatorName = parcelQuestions.readString();
        this.subjectName = parcelQuestions.readString();
        parcelQuestions.readTypedList(answers, Answers.CREATOR);


    }

    @JsonProperty("question_format")
    public String getQuestionFormat() {
        return this.questionFormat;
    }

    public void setQuestionFormat(String questionFormat) {
        this.questionFormat = questionFormat;
    }

    @JsonProperty("question_assets_link")
    public String getQuestionAssetsLink() {
        return this.questionAssetsLink;
    }

    public void setQuestionAssetsLink(String questionAssetsLink) {
        this.questionAssetsLink = questionAssetsLink;
    }

    @JsonProperty("question_hint")
    public String getQuestionHint() {
        return this.questionHint;
    }

    public void setQuestionHint(String questionHint) {
        this.questionHint = questionHint;
    }

    @JsonProperty("question_image_link")
    public String getQuestionImageLink() {
        return this.questionImageLink;
    }

    public void setQuestionImageLink(String questionImageLink) {
        this.questionImageLink = questionImageLink;
    }

    @JsonProperty("evaluation_notes")
    public String getEvaluationNotes() {
        return this.evaluationNotes;
    }

    public void setEvaluationNotes(String evaluationNotes) {
        this.evaluationNotes = evaluationNotes;
    }

    @JsonProperty("topic_id")
    public String getTopicId() {
        return this.topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @JsonProperty("answers")
    public ArrayList<Answers> getAnswers() {
        return this.answers;
    }

    public void setAnswers(ArrayList<Answers> answers) {
        this.answers = answers;
    }

    @JsonProperty("classroom_id")
    public String getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    @JsonProperty("subject_id")
    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @JsonProperty("book_id")
    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @JsonProperty("solution")
    public String getSolution() {
        return this.solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @JsonProperty("question_id")
    public String getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @JsonProperty("question_text")
    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @JsonProperty("question_creator_id")
    public String getQuestionCreatorId() {
        return this.questionCreatorId;
    }

    public void setQuestionCreatorId(String questionCreatorId) {
        this.questionCreatorId = questionCreatorId;
    }

    @JsonProperty("question_creator_name")
    public String getQuestionCreatorName() {
        return this.questionCreatorName;
    }

    public void setQuestionCreatorName(String questionCreatorName) {
        this.questionCreatorName = questionCreatorName;
    }

    @JsonProperty("subject_name")
    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }


    public Boolean getIsQuestionAddedInPreview() {
        return isQuestionAddedInPreview;
    }

    public void setIsQuestionAddedInPreview(Boolean isQuestionAddedInPreview) {
        this.isQuestionAddedInPreview = isQuestionAddedInPreview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getQuestionFormat());
        dest.writeString(getQuestionAssetsLink());
        dest.writeString(getQuestionHint());
        dest.writeString(getQuestionImageLink());
        dest.writeString(getEvaluationNotes());
        dest.writeString(getTopicId());
        dest.writeTypedList(answers);
        dest.writeString(getClassroomId());
        dest.writeString(getSubjectId());
        dest.writeString(getBookId());
        dest.writeString(getSolution());
        dest.writeString(getQuestionId());
        dest.writeString(getQuestionText());
        dest.writeString(getQuestionCreatorId());
        dest.writeString(getQuestionCreatorName());
        dest.writeString(getSubjectName());
    }

    public static final Parcelable.Creator<Questions> CREATOR = new Parcelable.Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel source) {
            return new Questions(source);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };
}
