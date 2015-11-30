package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class with details of badges.
 * Relationship with {@link BadgeCategory}
 */
public class Badges extends RealmObject {
    @PrimaryKey
    private  int badgeId;
    private String badgeName;
    private String badgeDescription;
    private String badgeAward;
    private BadgeCategory badgeCategory;
    private Date createdDate;
    private Date modifiedDate;

    public int getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(int badgeId) {
        this.badgeId = badgeId;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public String getBadgeDescription() {
        return badgeDescription;
    }

    public void setBadgeDescription(String badgeDescription) {
        this.badgeDescription = badgeDescription;
    }

    public String getBadgeAward() {
        return badgeAward;
    }

    public void setBadgeAward(String badgeAward) {
        this.badgeAward = badgeAward;
    }

    public BadgeCategory getBadgeCategory() {
        return badgeCategory;
    }

    public void setBadgeCategory(BadgeCategory badgeCategory) {
        this.badgeCategory = badgeCategory;
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
