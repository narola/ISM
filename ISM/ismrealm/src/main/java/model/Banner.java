package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class with details of banner.
 * Relationship with {@link BannerCategory}
 * Relationship with {@link User}
 */
public class Banner extends RealmObject {
    @PrimaryKey
    private  int bannerId;
    private String bannerTitle;
    private String bannerType;
    private String imageLink;
    private String bannerStatus;
    private String bannerDescription;
    private String ownerName;
    private String ownerEmailId;
    private String ownerContactNo;
    private int totalViews;
    private int totalClicks;
    private BannerCategory bannerCategory;
    private User user;
    private Date addedOn;
    private Date bannerValidity;
    private Date createdDate;
    private Date modifiedDate;

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getBannerStatus() {
        return bannerStatus;
    }

    public void setBannerStatus(String bannerStatus) {
        this.bannerStatus = bannerStatus;
    }

    public String getBannerDescription() {
        return bannerDescription;
    }

    public void setBannerDescription(String bannerDescription) {
        this.bannerDescription = bannerDescription;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmailId() {
        return ownerEmailId;
    }

    public void setOwnerEmailId(String ownerEmailId) {
        this.ownerEmailId = ownerEmailId;
    }

    public String getOwnerContactNo() {
        return ownerContactNo;
    }

    public void setOwnerContactNo(String ownerContactNo) {
        this.ownerContactNo = ownerContactNo;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }

    public int getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(int totalClicks) {
        this.totalClicks = totalClicks;
    }

    public BannerCategory getBannerCategory() {
        return bannerCategory;
    }

    public void setBannerCategory(BannerCategory bannerCategory) {
        this.bannerCategory = bannerCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    public Date getBannerValidity() {
        return bannerValidity;
    }

    public void setBannerValidity(Date bannerValidity) {
        this.bannerValidity = bannerValidity;
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
