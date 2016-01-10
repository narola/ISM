package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class with details of categories related to badges.
 * Relationship with {@link ROBadgeCategory}
 */
public class ROBadgeCategory extends RealmObject {

    @PrimaryKey
    private  int badgeCategoryId;
    private String badgeCategoryName;
    private ROBadgeCategory roBadgeCategory;
    private Date createdDate;
    private Date modifiedDate;

    public int getBadgeCategoryId() {
        return badgeCategoryId;
    }

    public void setBadgeCategoryId(int badgeCategoryId) {
        this.badgeCategoryId = badgeCategoryId;
    }

    public String getBadgeCategoryName() {
        return badgeCategoryName;
    }

    public void setBadgeCategoryName(String badgeCategoryName) {
        this.badgeCategoryName = badgeCategoryName;
    }

    public ROBadgeCategory getRoBadgeCategory() {
        return roBadgeCategory;
    }

    public void setRoBadgeCategory(ROBadgeCategory roBadgeCategory) {
        this.roBadgeCategory = roBadgeCategory;
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
