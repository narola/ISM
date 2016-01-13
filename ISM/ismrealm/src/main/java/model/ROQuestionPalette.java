package model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import model.authormodel.ROExamSubmittor;

/**
 * Created by c166 on 12/01/16.
 */
public class ROQuestionPalette extends RealmObject {


    @PrimaryKey
    private int questionPaletteId;
    private String value;
    private ROQuestions roQuestion;
    private ROExamSubmittor roExamSubmittor;
    private ROStudentExamEvaluation roStudentExamEvaluation;


    public int getQuestionPaletteId() {
        return questionPaletteId;
    }

    public void setQuestionPaletteId(int questionPaletteId) {
        this.questionPaletteId = questionPaletteId;
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

    public ROQuestions getRoQuestion() {
        return roQuestion;
    }

    public void setRoQuestion(ROQuestions roQuestion) {
        this.roQuestion = roQuestion;
    }


    public ROExamSubmittor getRoExamSubmittor() {
        return roExamSubmittor;
    }

    public void setRoExamSubmittor(ROExamSubmittor roExamSubmittor) {
        this.roExamSubmittor = roExamSubmittor;
    }
}
