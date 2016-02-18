package com.ism.author.ws.helper;

/**
 * Created by c166 on 23/10/15.
 */

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ism.author.utility.Debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by c161 on 23/10/15.
 */
public class WebserviceConnector {

    private static final String LOG_TAG = "WebserviceConnector";
    private String TAG = WebserviceConnector.class.getSimpleName();
    private static final Lock lock = new ReentrantLock();
    private static ObjectMapper mapper = null;
    private String url;


    public WebserviceConnector(String url) {
        this.url = url;
    }

    public <Request, Response> Response execute(Class<Response> responseType, Request request) throws Exception {

        Response ret = null;
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "android");
            connection.setDoOutput(true); // Triggers POST.
//			connection.setRequestProperty("Accept-Charset", charset);
//			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            Log.i(TAG, "api : " + url + "");
            ObjectWriter writer = getMapper().writer();
            String jsonObject = "";
            if (request != null) {

                if (request != null) {
                    // writer.writeValueAsString( request );
                    jsonObject = writer.writeValueAsString(request);
                    Log.i(TAG, "REQUEST JSON OBJECT : " + jsonObject + "");
                }


            }
            try (OutputStream output = connection.getOutputStream()) {
                output.write(jsonObject.getBytes());
            } catch (Exception error) {
                Log.i(TAG, "JSON OBJECT  Write Exception: " + error.getLocalizedMessage() + "");
            }


            try {
                InputStream response = connection.getInputStream();
                String json;
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                response.close();
                json = sb.toString();

                Debug.e(TAG, "The Response is:::" + json);

                ret = getMapper().readValue(json, responseType);

            } catch (Exception e) {
                Log.e(TAG, "Error converting result " + e.getLocalizedMessage());
                return null;
            }

        } catch (Exception e) {

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
                mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES,
                        false);
            }
            lock.unlock();
        } catch (Exception ex) {
            if (ex != null)
                Log.e(LOG_TAG, "Mapper Initialization Failed. Exception :: "
                        + ex.getMessage());
        }

        return mapper;
    }

    public <Response> Response uploadMedia(Class<Response> responseType, Attribute mediaUploadAttribute) {
        String charset = "UTF-8";
        String requestURL = url;
        String responseString = null;
        Response ret = null;

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
            multipart.addHeaderField("User-Agent", "android");

//            multipart.addHeaderField("User-Agent", "CodeJava");
//            multipart.addHeaderField("Test-Header", "Header-Value");

            /*This is to add parameter values */
            for (int i = 0; i < mediaUploadAttribute.getArrListParam().size(); i++) {
                multipart.addFormField(mediaUploadAttribute.getArrListParam().get(i).getParamName(),
                        mediaUploadAttribute.getArrListParam().get(i).getParamValue());
            }

            /*This is to add file content*/
            for (int i = 0; i < mediaUploadAttribute.getArrListFile().size(); i++) {
                multipart.addFilePart(mediaUploadAttribute.getArrListFile().get(i).getParamName(),
                        new File(mediaUploadAttribute.getArrListFile().get(i).getFileName()));
            }


            List<String> response = multipart.finish();
            Debug.e(TAG, "SERVER REPLIED:");
            for (String line : response) {
                Debug.e(TAG, "Upload Files Response:::" + line);
                responseString = line;
            }

            ret = getMapper().readValue(responseString, responseType);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return ret;

    }


}
