package com.ism.author.model;

import android.net.Uri;

/**
 * Created by c162 on 16/10/15.
 */
public class PostFileModel {
    String strFileType;
    Uri strFilePath;
    String strVideoThumbNail;

    public PostFileModel(String strFileType, Uri strFilePath, String strVideoThumbNail) {
        this.strFileType = strFileType;
        this.strFilePath = strFilePath;
        this.strVideoThumbNail = strVideoThumbNail;
    }

    public String getStrVideoThumbNail() {
        return strVideoThumbNail;
    }

    public void setStrVideoThumbNail(String strVideoThumbNail) {
        this.strVideoThumbNail = strVideoThumbNail;
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
