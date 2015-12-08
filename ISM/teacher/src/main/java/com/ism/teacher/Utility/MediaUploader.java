package com.ism.teacher.Utility;

import android.content.Context;
import android.os.AsyncTask;

import com.ism.teacher.constants.WebConstants;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by c75 on 04/12/15.
 */
public class MediaUploader {
    private static final String TAG = MediaUploader.class.getSimpleName();
    private Context mContext;
    String question_id = "";

    public MediaUploader(Context mContext, String question_id) {
        this.mContext = mContext;
        this.question_id = question_id;
    }

    public class MediaUploaderCaller extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String charset = "UTF-8";
            File questionImage = new File(params[0]);


            String requestURL = WebConstants.URL_UPLOADMEDIAFORQUESTION;

            try {
                MultipartUtility multipart = new MultipartUtility(requestURL, charset);
//            multipart.addHeaderField("User-Agent", "CodeJava");
//            multipart.addHeaderField("Test-Header", "Header-Value");

                multipart.addFormField("question_id", question_id);
                multipart.addFormField("mediaType", "image");

                multipart.addFilePart("mediaFile", questionImage);


                List<String> response = multipart.finish();

                System.out.println("SERVER REPLIED:");

                for (String line : response) {
                    Debug.e(TAG, "Upload Question Image Response:::" + line);
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
            return "SUCCESS";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
