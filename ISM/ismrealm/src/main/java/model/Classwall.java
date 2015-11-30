package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of classwall.
 * Relationship with {@link User}
 * Relationship with {@link Classrooms}
 */
public class Classwall extends RealmObject {

    @PrimaryKey
    private  int classwallId;
    private  String wallPost;
    private Date createdDate;
    private Date modifiedDate;
    private User postBy;
    private Classrooms classrooms;

    public int getClasswallId() {
        return classwallId;
    }

    public void setClasswallId(int classwallId) {
        this.classwallId = classwallId;
    }

    public String getWallPost() {
        return wallPost;
    }

    public void setWallPost(String wallPost) {
        this.wallPost = wallPost;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public User getPostBy() {
        return postBy;
    }

    public void setPostBy(User postBy) {
        this.postBy = postBy;
    }

    public Classrooms getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(Classrooms classrooms) {
        this.classrooms = classrooms;
    }
}
