package model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c166 on 12/01/16.
 */
public class ROEvaluation extends RealmObject {


    @PrimaryKey
    private int evaluationId;
    private String studentResposne;
    private String evaluationScore;
    private Boolean isRight;
    private String answerStatus;
    private String questionScore;
    private ROStudentExamEvaluation roStudentExamEvaluation;


    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;

    }

    public String getStudentResposne() {
        return studentResposne;
    }

    public void setStudentResposne(String studentResposne) {
        this.studentResposne = studentResposne;
    }

    public String getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(String evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public Boolean getIsRight() {
        return isRight;
    }

    public void setIsRight(Boolean isRight) {
        this.isRight = isRight;
    }

    public String getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        this.answerStatus = answerStatus;
    }

    public String getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(String questionScore) {
        this.questionScore = questionScore;
    }

    public ROStudentExamEvaluation getRoStudentExamEvaluation() {
        return roStudentExamEvaluation;
    }

    public void setRoStudentExamEvaluation(ROStudentExamEvaluation roStudentExamEvaluation) {
        this.roStudentExamEvaluation = roStudentExamEvaluation;
    }
}
