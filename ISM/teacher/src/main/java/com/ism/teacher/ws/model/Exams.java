package com.ism.teacher.ws.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Exams {
    private String examId;
    private String examName;
    private String classroomId;
    private String classroomName;
    private String totalStudent;
    private String subjectId;
    private String subjectName;
    private String bookId;

    private String bookName;
    private String topicId;
    private String totalStudentAttempted;
    private String examType;
    private String examCategory;
    private String examMode;
    private String passPercentage;
    private String duration;
    private String attemptCount;
    private String examInstructions;
    private String randomQuestion;
    private String negativeMarking;
    private String negativeMarkValue;
    private String useQuestionScore;
    private String correctAnswerScore;
    private String declareResults;
    private String examAssessor;
    private String examStartDate;
    private String examStartTime;
    private String totalQuestion;
    private String evaluationStatus;
    private String averageScore;
    private String totalAssessed;
    private String totalUnassessed;
    private String examCreatedDate;

    @JsonProperty("book_name")
    public String getBookName() {
        return bookName;
    }

    public Exams setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }


    @JsonProperty("negative_mark_value")
    public String getNegativeMarkValue() {
        return negativeMarkValue;
    }

    public Exams setNegativeMarkValue(String negativeMarkValue) {
        this.negativeMarkValue = negativeMarkValue;
        return this;
    }

    @JsonProperty("use_question_score")
    public String getUseQuestionScore() {
        return useQuestionScore;
    }

    public Exams setUseQuestionScore(String useQuestionScore) {
        this.useQuestionScore = useQuestionScore;
        return this;
    }

    @JsonProperty("correct_answer_score")
    public String getCorrectAnswerScore() {
        return correctAnswerScore;
    }

    public Exams setCorrectAnswerScore(String correctAnswerScore) {
        this.correctAnswerScore = correctAnswerScore;
        return this;
    }

    @JsonProperty("declare_results")
    public String getDeclareResults() {
        return declareResults;
    }

    public Exams setDeclareResults(String declareResults) {
        this.declareResults = declareResults;
        return this;
    }

    @JsonProperty("exam_assessor")
    public String getExamAssessor() {
        return examAssessor;
    }

    public Exams setExamAssessor(String examAssessor) {
        this.examAssessor = examAssessor;
        return this;
    }

    @JsonProperty("exam_start_date")
    public String getExamStartDate() {
        return examStartDate;
    }

    public Exams setExamStartDate(String examStartDate) {
        this.examStartDate = examStartDate;
        return this;
    }

    @JsonProperty("exam_start_time")
    public String getExamStartTime() {
        return examStartTime;
    }

    public Exams setExamStartTime(String examStartTime) {
        this.examStartTime = examStartTime;
        return this;
    }


    ////////

    @JsonProperty("negative_marking")
    public String getNegativeMarking() {
        return negativeMarking;
    }

    public Exams setNegativeMarking(String negativeMarking) {
        this.negativeMarking = negativeMarking;
        return this;
    }

    @JsonProperty("random_question")
    public String getRandomQuestion() {
        return randomQuestion;
    }

    public Exams setRandomQuestion(String randomQuestion) {
        this.randomQuestion = randomQuestion;
        return this;
    }


    @JsonProperty("exam_instructions")
    public String getExamInstructions() {
        return examInstructions;
    }

    public Exams setExamInstructions(String examInstructions) {
        this.examInstructions = examInstructions;
        return this;
    }

    @JsonProperty("attempt_count")
    public String getAttemptCount() {
        return attemptCount;
    }

    public Exams setAttemptCount(String attemptCount) {
        this.attemptCount = attemptCount;
        return this;
    }

    @JsonProperty("total_student_attempted")
    public String getTotalStudentAttempted() {
        return totalStudentAttempted;
    }

    public Exams setTotalStudentAttempted(String totalStudentAttempted) {
        this.totalStudentAttempted = totalStudentAttempted;
        return this;
    }

    @JsonProperty("total_student")
    public String getTotalStudent() {
        return totalStudent;
    }

    public Exams setTotalStudent(String totalStudent) {
        this.totalStudent = totalStudent;
        return this;
    }

    @JsonProperty("topic_id")
    public String getTopicId() {
        return topicId;
    }

    public Exams setTopicId(String topicId) {
        this.topicId = topicId;
        return this;
    }

    @JsonProperty("book_id")
    public String getBookId() {
        return bookId;
    }

    public Exams setBookId(String bookId) {
        this.bookId = bookId;
        return this;
    }

    @JsonProperty("total_assessed")
    public String getTotalAssessed() {
        return totalAssessed;
    }

    public Exams setTotalAssessed(String totalAssessed) {
        this.totalAssessed = totalAssessed;
        return this;
    }

    @JsonProperty("total_unassessed")
    public String getTotalUnassessed() {
        return totalUnassessed;
    }

    public Exams setTotalUnassessed(String totalUnassessed) {
        this.totalUnassessed = totalUnassessed;
        return this;
    }

    @JsonProperty("exam_created_date")
    public String getExamCreatedDate() {
        return examCreatedDate;
    }

    public void setExamCreatedDate(String examCreatedDate) {
        this.examCreatedDate = examCreatedDate;
    }

    @JsonProperty("total_question")
    public String getTotalQuestion() {
        return this.totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    @JsonProperty("classroom_name")
    public String getClassroomName() {
        return this.classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    @JsonProperty("exam_mode")
    public String getExamMode() {
        return this.examMode;
    }

    public void setExamMode(String examMode) {
        this.examMode = examMode;
    }

    @JsonProperty("exam_id")
    public String getExamId() {
        return this.examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    @JsonProperty("classroom_id")
    public String getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    @JsonProperty("average_score")
    public String getAverageScore() {
        return this.averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    @JsonProperty("exam_name")
    public String getExamName() {
        return this.examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    @JsonProperty("subject_id")
    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @JsonProperty("subject_name")
    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @JsonProperty("duration")
    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty("evaluation_status")
    public String getEvaluationStatus() {
        return this.evaluationStatus;
    }

    public void setEvaluationStatus(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    @JsonProperty("pass_percentage")
    public String getPassPercentage() {
        return this.passPercentage;
    }

    public void setPassPercentage(String passPercentage) {
        this.passPercentage = passPercentage;
    }

    @JsonProperty("exam_type")
    public String getExamType() {
        return this.examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    @JsonProperty("exam_category")
    public String getExamCategory() {
        return this.examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }


}
