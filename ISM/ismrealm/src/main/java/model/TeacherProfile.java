package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of teachers.
 * Relationship with {@link User}
 */
public class TeacherProfile extends RealmObject {

    @PrimaryKey
    private  int teacherId;
    private User user;
    private int totalAssignment;
    private int totalSubmissionReceived;
    private int totalPost;
    private int totalBadges;
    private Date createdDate;
    private Date modifiedDate;
}
