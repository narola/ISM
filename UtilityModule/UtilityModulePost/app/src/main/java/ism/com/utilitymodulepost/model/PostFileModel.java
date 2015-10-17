package ism.com.utilitymodulepost.model;

import android.net.Uri;

/**
 * Created by c162 on 16/10/15.
 */
public class PostFileModel  {
    String strFileType;
    Uri strFilePath;

    public PostFileModel(String strFileType, Uri strFilePath) {
        this.strFileType = strFileType;
        this.strFilePath = strFilePath;
    }

    public String getStrFileType() {
        return strFileType;
    }

    public void setStrFileType(String strFileType) {
        this.strFileType = strFileType;
    }

    public Uri getStrFilePath() {
        return strFilePath;
    }

    public void setStrFilePath(Uri strFilePath) {
        this.strFilePath = strFilePath;
    }
}
