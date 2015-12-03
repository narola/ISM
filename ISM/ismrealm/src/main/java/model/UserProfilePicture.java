package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of profile picture of user/author.
 * Relationship with {@link User}
 */
public class UserProfilePicture extends RealmObject {

    @PrimaryKey
    private int userProfilePictureId;
    private User user;
    private String profileLink;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserProfilePictureId() {
        return userProfilePictureId;
    }

    public void setUserProfilePictureId(int userProfilePictureId) {
        this.userProfilePictureId = userProfilePictureId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
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
}
