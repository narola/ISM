package com.ism.fragment.userprofile;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by c162 on 25/11/15.
 */
public class EditProfileImageAsync extends AsyncTask{
    private static final String TAG = EditProfileImageAsync.class.getSimpleName();
    HttpURLConnection conn = null;
    DataOutputStream dos = null;
    DataInputStream inStream = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1 * 1024 * 1024;
    String responseFromServer = "", fileName = null;
    private int serverResponseCode;
    private ObjectMapper mapper=null;
    private static final Lock lock = new ReentrantLock();
    File file;

    public EditProfileImageAsync(File file) {
        this.file = file;
    }

    public <Response> Response execute(Class<Response> responseType) throws Exception {

        Response ret = null;
        try {
//            if (android.os.Build.VERSION.SDK_INT > 9) {
//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy);
//            }

            FileInputStream fileInputStream = new FileInputStream(file);
            // open a URL connection to the Servlet
            URL url = new URL("http://192.168.1.162/demo/testImageUpload.php");
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            //conn.setRequestProperty("mediaFile", fileName);
            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"mediaFile\";filename=\"" + file.getName() + "\"" + lineEnd);
            dos.writeBytes(lineEnd);



//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//            //dos.writeBytes(twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"mediaFile\";filename=\"" + file.getName() + "\"" + lineEnd);
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + lineEnd);

//            dos.writeBytes("Content-Disposition: form-questionData; name=\"feed_id\"" + lineEnd);
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(feed_id);
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//
//            dos.writeBytes("Content-Disposition: form-questionData; name=\"mediaType\"" + lineEnd);
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(type);
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-questionData; name=\"feed_by\"" + lineEnd);
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(feed_by);
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + lineEnd);

            bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size
            Log.i(TAG, "Initial .available : " + bytesAvailable);

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form questionData necesssary after file questionData...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i("Upload file to server", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            // close streams
            Log.i("Upload file to server", file.getName() + " File is written");
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //this block will give the response of upload link
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn
                    .getInputStream()));
            String line;
            String json;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                Log.i(TAG, "RES Message: " + line);
                stringBuilder.append(line);
            }
            rd.close();
            json = stringBuilder.toString();
            ret = getMapper().readValue(json, responseType);
        } catch (IOException ioex) {
            Log.e(TAG, "error: " + ioex.getMessage(), ioex);
        }
        return ret;
    }



    protected synchronized ObjectMapper getMapper() {
        if (mapper != null) {
            return mapper;
        }

        try {
            lock.lock();
            if (mapper == null) {
                mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            }
            lock.unlock();
        } catch (Exception ex) {
            if (ex != null)
                Log.e(TAG, "Mapper Initialization Failed. Exception : " + ex.getMessage());
        }

        return mapper;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            execute(AboutMeFragment.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
