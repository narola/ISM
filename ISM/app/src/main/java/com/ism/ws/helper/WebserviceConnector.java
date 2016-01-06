package com.ism.ws.helper;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ism.utility.Debug;

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

    private static final String TAG = WebserviceConnector.class.getSimpleName();

    private static final Lock lock = new ReentrantLock();
    private static ObjectMapper mapper = null;
    private String url;

    public WebserviceConnector(String url) {
        this.url = url;
    }

    public <Request, Response> Response execute(Class<Response> responseType, Request request) throws Exception {

        Response ret = null;
        try {
            Log.e(TAG, "api url : " + url);

            URLConnection connection = new URL(url).openConnection();
//			connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            connection.setRequestProperty("User-Agent", "android");
            connection.setDoOutput(true); // Triggers POST.
//			connection.setRequestProperty("Accept-Charset", charset);
//			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            ObjectWriter writer = getMapper().writer();
            String jsonObject = "";
            if (request != null) {

                if (request != null) {
                    // writer.writeValueAsString( request );
                    jsonObject = writer.writeValueAsString(request);
<<<<<<< HEAD
                    Log.e(TAG, "Request object : " + jsonObject);
=======
                    Log.e("Request object", jsonObject + "");
>>>>>>> 99da935ad0b1f5fcad2e18bc63fad04af58c34e1
                }

            }
            try (OutputStream output = connection.getOutputStream()) {
                output.write(jsonObject.getBytes());
            } catch (Exception error) {

            }

            InputStream response = connection.getInputStream();

            try {
                String json;
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                response.close();
                json = sb.toString();

                Log.e(TAG, "Response json : " + json);

                ret = getMapper().readValue(json, responseType);

            } catch (Exception e) {
                Log.e(TAG, "Error converting result : " + e.toString());
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
                mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            }
            lock.unlock();
        } catch (Exception ex) {
            if (ex != null)
                Log.e(TAG, "Mapper Initialization Failed. Exception : " + ex.getMessage());
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
            /*No header so no need to add it*/

//            multipart.addHeaderField("User-Agent", "CodeJava");
//            multipart.addHeaderField("Test-Header", "Header-Value");

            /*This is to add parameter values */
            for (int i = 0; i < mediaUploadAttribute.getArrListParam().size(); i++) {
                multipart.addFormField(mediaUploadAttribute.getArrListParam().get(i).getParamName(),
                        mediaUploadAttribute.getArrListParam().get(i).getParamValue());
                Debug.e(TAG, "Paramname:" + mediaUploadAttribute.getArrListParam().get(i).getParamName()
                        + "--Param value:" + mediaUploadAttribute.getArrListParam().get(i).getParamValue());
            }

            /*This is to add file content*/
            for (int i = 0; i < mediaUploadAttribute.getArrListFile().size(); i++) {
                Log.e(TAG, "File path : " + mediaUploadAttribute.getArrListFile().get(i).getFileName());
                multipart.addFilePart(mediaUploadAttribute.getArrListFile().get(i).getParamName(),
                        new File(mediaUploadAttribute.getArrListFile().get(i).getFileName()));
            }

            List<String> response = multipart.finish();
            Debug.e(TAG, "SERVER REPLIED:");
            for (String line : response) {
                Debug.e(TAG, "Upload Media Response:::" + line);
                responseString = line;
            }

            ret = getMapper().readValue(responseString, responseType);
        } catch (IOException ex) {
            Log.e(TAG, "uploadMedia  IOException : " + ex.getLocalizedMessage());
        }
        return ret;

    }


}
