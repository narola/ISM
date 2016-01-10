package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of pastimes.
 */
public class ROPastime extends RealmObject {

    @PrimaryKey
    private  int pastimeId;
    private String pastimeName;
    private String pastimeImage;
    private Date createdDate;
    private Date modifiedDate;

    public int getPastimeId() {
        return pastimeId;
    }

    public void setPastimeId(int pastimeId) {
        this.pastimeId = pastimeId;
    }

    public String getPastimeName() {
        return pastimeName;
    }

    public void setPastimeName(String pastimeName) {
        this.pastimeName = pastimeName;
    }

    public String getPastimeImage() {
        return pastimeImage;
    }

    public void setPastimeImage(String pastimeImage) {
        this.pastimeImage = pastimeImage;
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
