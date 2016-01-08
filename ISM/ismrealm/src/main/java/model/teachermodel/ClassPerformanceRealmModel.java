package model.teachermodel;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by c75 on 08/01/16.
 */
public class ClassPerformanceRealmModel extends RealmObject {

    private String examScore;
    private String examName;
    private String internalMarks;

    public RealmList<StudentsScoreRealmModel> getStudentsScore() {
        return studentsScore;
    }

    public void setStudentsScore(RealmList<StudentsScoreRealmModel> studentsScore) {
        this.studentsScore = studentsScore;

    }

    private RealmList<StudentsScoreRealmModel> studentsScore = new RealmList<StudentsScoreRealmModel>();

    public String getExamScore() {
        return examScore;
    }

    public void setExamScore(String examScore) {
        this.examScore = examScore;

    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(String internalMarks) {
        this.internalMarks = internalMarks;
    }
}
