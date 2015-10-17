package ism.com.utilitymodulepost.model;

/**
 * Created by c162 on 16/10/15.
 */
public class PostFileModel  {
    String strFileType;
    String strFilePath;

    public PostFileModel(String strFileType, String strFilePath) {
        this.strFileType = strFileType;
        this.strFilePath = strFilePath;
    }

    public String getStrFileType() {
        return strFileType;
    }

    public void setStrFileType(String strFileType) {
        this.strFileType = strFileType;
    }

    public String getStrFilePath() {
        return strFilePath;
    }

    public void setStrFilePath(String strFilePath) {
        this.strFilePath = strFilePath;
    }
}
