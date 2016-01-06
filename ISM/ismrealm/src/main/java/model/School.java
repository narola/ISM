package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class to handle detail of schools.
 * Relationship with {@link District},{@link City},{@link State},{@link Country}
 */
public class School extends RealmObject {

    @PrimaryKey
    private  int localSchoolId;
    private  int serverSchoolId;
    private String schoolName;
    private String schoolNickName;
    private String pricipalName;
    private String schoolEmailId;
    private String schoolContactNo1;
    private String schoolContactNo2;
    private String schoolGrade;
    private String address;
    private int schoolCode;
    private int schoolType;
    private int schoolMode;
    private District district;
    private City city;
    private State state;
    private Country country;
    private Date createdDate;
    private Date modifiedDate;

    public int getLocalSchoolId() {
        return localSchoolId;
    }

    public void setLocalSchoolId(int localSchoolId) {
        this.localSchoolId = localSchoolId;
    }

    public int getServerSchoolId() {
        return serverSchoolId;
    }

    public void setServerSchoolId(int serverSchoolId) {
        this.serverSchoolId = serverSchoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolNickName() {
        return schoolNickName;
    }

    public void setSchoolNickName(String schoolNickName) {
        this.schoolNickName = schoolNickName;
    }

    public String getPricipalName() {
        return pricipalName;
    }

    public void setPricipalName(String pricipalName) {
        this.pricipalName = pricipalName;
    }

    public String getSchoolEmailId() {
        return schoolEmailId;
    }

    public void setSchoolEmailId(String schoolEmailId) {
        this.schoolEmailId = schoolEmailId;
    }

    public String getSchoolContactNo1() {
        return schoolContactNo1;
    }

    public void setSchoolContactNo1(String schoolContactNo1) {
        this.schoolContactNo1 = schoolContactNo1;
    }

    public String getSchoolContactNo2() {
        return schoolContactNo2;
    }

    public void setSchoolContactNo2(String schoolContactNo2) {
        this.schoolContactNo2 = schoolContactNo2;
    }

    public String getSchoolGrade() {
        return schoolGrade;
    }

    public void setSchoolGrade(String schoolGrade) {
        this.schoolGrade = schoolGrade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(int schoolCode) {
        this.schoolCode = schoolCode;
    }

    public int getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(int schoolType) {
        this.schoolType = schoolType;
    }

    public int getSchoolMode() {
        return schoolMode;
    }

    public void setSchoolMode(int schoolMode) {
        this.schoolMode = schoolMode;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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
