package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class with details of categories related to banner.
 * Relationship with {@link ROBannerCategory}
 */
public class ROBannerCategory extends RealmObject {

    @PrimaryKey
    private  int bannerCategoryId;
    private String BannerCategoryName;
    private ROBannerCategory roBannerCategory;
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

    public ROBannerCategory getRoBannerCategory() {
        return roBannerCategory;
    }

    public void setRoBannerCategory(ROBannerCategory roBannerCategory) {
        this.roBannerCategory = roBannerCategory;
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
