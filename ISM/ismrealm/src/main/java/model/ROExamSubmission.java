package model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import model.authormodel.ROExamSubmittor;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of submission for given Assignment.
 * Relationship with {@link ROAssignments}
 * Relationship with {@link ROUser}
 */
public class ROExamSubmission extends RealmObject {


    @PrimaryKey
    private int examId;
    private ROExam roExam;
    private RealmList<ROExamSubmittor> roExamSubmittors = new RealmList<ROExamSubmittor>();


    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;

    }

    public ROExam getRoExam() {
        return roExam;
    }

    public void setRoExam(ROExam roExam) {
        this.roExam = roExam;
    }


    public RealmList<ROExamSubmittor> getRoExamSubmittors() {
        return roExamSubmittors;
    }

    public void setRoExamSubmittors(RealmList<ROExamSubmittor> roExamSubmittors) {
        this.roExamSubmittors = roExamSubmittors;

    }


}

