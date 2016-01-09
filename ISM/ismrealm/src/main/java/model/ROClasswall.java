package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of classwall.
 * Relationship with {@link ROUser}
 * Relationship with {@link ROClassrooms}
 */
public class ROClasswall extends RealmObject {

    @PrimaryKey
    private  int classwallId;
    private  String wallPost;
    private Date createdDate;
    private Date modifiedDate;
    private ROUser postBy;
    private ROClassrooms roClassrooms;

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

    public ROUser getPostBy() {
        return postBy;
    }

    public void setPostBy(ROUser postBy) {
        this.postBy = postBy;
    }

    public ROClassrooms getRoClassrooms() {
        return roClassrooms;
    }

    public void setRoClassrooms(ROClassrooms roClassrooms) {
        this.roClassrooms = roClassrooms;
    }
}
