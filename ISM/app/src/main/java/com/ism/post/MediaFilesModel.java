package com.ism.post;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by c162 on 13/10/15.
 */
public class MediaFilesModel {
    String strFileType;
    Bitmap bitmap;
    File strFile;

    public File getUriFile() {
        return strFile;
    }

    public void setStrFile(File strFile) {
        this.strFile = strFile;
    }

    public MediaFilesModel(String strFileType, Bitmap bitmap, File strFile) {

        this.strFileType = strFileType;
        this.bitmap = bitmap;
        this.strFile = strFile;

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap Bitmap) {
        this.bitmap = Bitmap;
    }

    public MediaFilesModel(String strFileType, Bitmap Bitmap) {

        this.strFileType = strFileType;
        this.bitmap = Bitmap;
    }

    public String getStrFileType() {
        return strFileType;
    }

    public void setStrFileType(String strFileType) {
        this.strFileType = strFileType;
    }

}
