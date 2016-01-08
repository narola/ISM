package model.teachermodel;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by c75 on 08/01/16.
 */
public class StudentsScoreRealmModel extends RealmObject{

    private String headMistressComment;
    private String classMistressComment;
    private String studentPic;
    private String grade;
    private String rank;
    private String studentScore;
    private String studentName;
    private String studentId;
    private String percentage;
    private RealmList<SubjectWiseScoreRealmModel> subjectWiseScore;

    public String getHeadMistressComment() {
        return headMistressComment;
    }

    public void setHeadMistressComment(String headMistressComment) {
        this.headMistressComment = headMistressComment;

    }

    public String getClassMistressComment() {
        return classMistressComment;
    }

    public void setClassMistressComment(String classMistressComment) {
        this.classMistressComment = classMistressComment;

    }

    public String getStudentPic() {
        return studentPic;
    }

    public void setStudentPic(String studentPic) {
        this.studentPic = studentPic;

    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;

    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;

    }

    public String getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(String studentScore) {
        this.studentScore = studentScore;

    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;

    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;

    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;

    }

    public RealmList<SubjectWiseScoreRealmModel> getSubjectWiseScore() {
        return subjectWiseScore;
    }

    public void setSubjectWiseScore(RealmList<SubjectWiseScoreRealmModel> subjectWiseScore) {
        this.subjectWiseScore = subjectWiseScore;
    }



}
