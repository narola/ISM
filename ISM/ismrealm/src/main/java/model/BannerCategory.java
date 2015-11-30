package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class with details of categories related to banner.
 * Relationship with {@link BannerCategory}
 */
public class BannerCategory extends RealmObject {

    @PrimaryKey
    private  int bannerCategoryId;
    private String BannerCategoryName;
    private BannerCategory bannerCategory;
    private Date createdDate;
    private Date modifiedDate;

    public int getBannerCategoryId() {
        return bannerCategoryId;
    }

    public void setBannerCategoryId(int bannerCategoryId) {
        this.bannerCategoryId = bannerCategoryId;
    }

    public String getBannerCategoryName() {
        return BannerCategoryName;
    }

    public void setBannerCategoryName(String bannerCategoryName) {
        BannerCategoryName = bannerCategoryName;
    }

    public BannerCategory getBannerCategory() {
        return bannerCategory;
    }

    public void setBannerCategory(BannerCategory bannerCategory) {
        this.bannerCategory = bannerCategory;
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
