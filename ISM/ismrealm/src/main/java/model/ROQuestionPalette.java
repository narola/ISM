package model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c166 on 12/01/16.
 */
public class ROQuestionPalette extends RealmObject {


    @PrimaryKey
    private int evaluationId;
    private String value;
    private ROStudentExamEvaluation roStudentExamEvaluation;


    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ROStudentExamEvaluation getRoStudentExamEvaluation() {
        return roStudentExamEvaluation;
    }

    public void setRoStudentExamEvaluation(ROStudentExamEvaluation roStudentExamEvaluation) {
        this.roStudentExamEvaluation = roStudentExamEvaluation;

    }


}
