package model.authormodel;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import model.ROExam;
import model.ROStudentExamEvaluation;

/**
 * Created by c166 on 08/01/16.
 */
public class ROExamSubmittor extends RealmObject {

    @PrimaryKey
    private int studentId;
    private String studentName;
    private String studentProfilePic;
    private String studentSchoolName;
    private String studentClassName;
    private String evaluationScore;
    private String examStatus;
    private Date submissionDate;
    private String remarks;

    private RealmList<ROExam> roExams = new RealmList<ROExam>();
    private RealmList<ROStudentExamEvaluation> roStudentExamEvaluations = new RealmList<ROStudentExamEvaluation>();


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;

    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentProfilePic() {
        return studentProfilePic;
    }

    public void setStudentProfilePic(String studentProfilePic) {
        this.studentProfilePic = studentProfilePic;
    }

    public String getStudentSchoolName() {
        return studentSchoolName;
    }

    public void setStudentSchoolName(String studentSchoolName) {
        this.studentSchoolName = studentSchoolName;
    }

    public String getStudentClassName() {
        return studentClassName;
    }

    public void setStudentClassName(String studentClassName) {
        this.studentClassName = studentClassName;
    }

    public String getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(String evaluationScore) {
        this.evaluationScore = evaluationScore;

    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public RealmList<ROExam> getRoExams() {
        return roExams;
    }

    public void setRoExams(RealmList<ROExam> roExams) {
        this.roExams = roExams;
    }


    public RealmList<ROStudentExamEvaluation> getRoStudentExamEvaluations() {
        return roStudentExamEvaluations;
    }

    public void setRoStudentExamEvaluations(RealmList<ROStudentExamEvaluation> roStudentExamEvaluations) {
        this.roStudentExamEvaluations = roStudentExamEvaluations;
    }


}
