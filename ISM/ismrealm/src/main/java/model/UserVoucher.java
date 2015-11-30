package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of vouchers of users.
 * Relationship with {@link User}
 */
public class UserVoucher extends RealmObject {

    @PrimaryKey
    private  int userVoucherId;
    private User user;
    private String voucherCode;
    private int voucherAmount;
    private int voucherStatus;
    private Date voucherValidity;
    private Date createdDate;
    private Date modifiedDate;

    public int getUserVoucherId() {
        return userVoucherId;
    }

    public void setUserVoucherId(int userVoucherId) {
        this.userVoucherId = userVoucherId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public int getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(int voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public int getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(int voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public Date getVoucherValidity() {
        return voucherValidity;
    }

    public void setVoucherValidity(Date voucherValidity) {
        this.voucherValidity = voucherValidity;
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
