package model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by c85 on 19/11/15.
 * {@link RealmObject} class - detail of system module.
 */
public class ROSystemModule extends RealmObject {

    @PrimaryKey
    private  int systemModuleId;
    private String moduleName;
    private Date createdDate;
    private Date modifiedDate;

    public int getSystemModuleId() {
        return systemModuleId;
    }

    public void setSystemModuleId(int systemModuleId) {
        this.systemModuleId = systemModuleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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
