package com.ism.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by c161 on 04/01/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamQuestion {
	
    private String questionFormat;
    private String questionAssetsLink;
    private String questionHint;
    private String questionImageLink;
    private String evaluationNotes;
    private String topicId;
    private String questionCreatorName;
    private String classroomId;
    private ArrayList<ExamAnswer> examAnswers;
    private ArrayList<String> tags;
    private String subjectId;
    private String bookId;
    private String solution;
    private String subjectName;
    private String questionId;
    private String questionText;
    private String questionCreatorId;
    private String questionScore;
    private int responseDuration;
    private int correctAnswerScore;
    private int isRight;
    private int choiceId;
    private String answerStatus;
    private boolean isSkipped;
    private boolean isReviewLater;
    private boolean isAnswered;
    private boolean isCorrect;

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

    @JsonProperty("question_creator_name")
    public String getQuestionCreatorName() {
        return this.questionCreatorName;
    }

    public void setQuestionCreatorName(String questionCreatorName) {
        this.questionCreatorName = questionCreatorName;
    }

    @JsonProperty("classroom_id")
    public String getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    @JsonProperty("answers")
    public ArrayList<ExamAnswer> getExamAnswers() {
        return this.examAnswers;
    }

    public void setExamAnswers(ArrayList<ExamAnswer> examAnswers) {
        this.examAnswers = examAnswers;
    }

    @JsonProperty("tags")
    public ArrayList<String> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
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

    @JsonProperty("subject_name")
    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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

    @JsonProperty("question_score")
    public String getQuestionScore() {
        return this.questionScore;
    }

    public void setQuestionScore(String questionScore) {
        this.questionScore = questionScore;
    }

    @JsonProperty("response_duration")
    public int getResponseDuration() {
        return this.responseDuration;
    }

    public void setResponseDuration(int responseDuration) {
        this.responseDuration = responseDuration;
    }

    @JsonProperty("correct_answer_score")
    public int getCorrectAnswerScore() {
        return this.correctAnswerScore;
    }

    public void setCorrectAnswerScore(int correctAnswerScore) {
        this.correctAnswerScore = correctAnswerScore;
    }

    @JsonProperty("is_right")
    public int getIsRight() {
        return this.isRight;
    }

    public void setIsRight(int isRight) {
        this.isRight = isRight;
    }

    @JsonProperty("choice_id")
    public int getChoiceId() {
        return this.choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    @JsonProperty("answer_status")
    public String getAnswerStatus() {
        return this.answerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        this.answerStatus = answerStatus;
    }

	public boolean isSkipped() {
		return isSkipped;
	}

	public void setIsSkipped(boolean isSkipped) {
		this.isSkipped = isSkipped;
	}

	public boolean isReviewLater() {
		return isReviewLater;
	}

	public void setIsReviewLater(boolean isReviewLater) {
		this.isReviewLater = isReviewLater;
	}

	public boolean isAnswered() {
		return isAnswered;
	}

	public void setIsAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

}
