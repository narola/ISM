package model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - contain all the questions.
 * Relationship with {@link ROQuestions}
 */
public class ROQuestionBank extends RealmObject {

    private RealmList<ROQuestions> roQuestions = new RealmList<ROQuestions>();


    public RealmList<ROQuestions> getRoQuestions() {
        return roQuestions;
    }

    public void setRoQuestions(RealmList<ROQuestions> roQuestions) {
        this.roQuestions = roQuestions;
    }


}
