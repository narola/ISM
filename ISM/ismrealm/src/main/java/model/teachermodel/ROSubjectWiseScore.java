package model.teachermodel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c75 on 08/01/16.
 */
public class ROSubjectWiseScore extends RealmObject {

    @PrimaryKey
    private String subjectId;
    private String internalScore;
    private String subjectGrade;
    private String remarks;
    private String marksObtained;
    private String subjectRank;
    private String subjectName;
    private String percentage;

    public String getInternalScore() {
        return internalScore;
    }

    public void setInternalScore(String internalScore) {
        this.internalScore = internalScore;

    }

    public String getSubjectGrade() {
        return subjectGrade;
    }

    public void setSubjectGrade(String subjectGrade) {
        this.subjectGrade = subjectGrade;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(String marksObtained) {
        this.marksObtained = marksObtained;
    }

    public String getSubjectRank() {
        return subjectRank;
    }

    public void setSubjectRank(String subjectRank) {
        this.subjectRank = subjectRank;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;

    }
}
