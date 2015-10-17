package ism.com.utilitymodulepost;

import android.os.Environment;

import java.io.File;

/**
 * Created by c162 on 17/10/15.
 */
public class AppConstant  {
    public static final String imageCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Images";
    public static final String videoCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Videos";
    public static final String audioCapturePath = Environment.getExternalStorageDirectory().toString() + File.separator + "ISM" + File.separator + "Audios";

}
