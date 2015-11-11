package com.ism.ws;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by c161 on 23/10/15.
 */
public class WSRequestPost {

    private static final String TAG = WSRequestPost.class.getSimpleName();

    private static final Lock lock = new ReentrantLock();
    private static ObjectMapper mapper = null;
    private static final String LOG_TAG = "WSRequestPost";
    private String url;

    public WSRequestPost(String url) {
        this.url = url;
    }

    public <Request, Response> Response execute(Class<Response> responseType, Request request) throws Exception {

        Response ret = null;
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true); // Triggers POST.
//			connection.setRequestProperty("Accept-Charset", charset);
//			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

            ObjectWriter writer = getMapper().writer();
            String jsonObject = "";
            if (request != null) {

                if (request != null) {
                    // writer.writeValueAsString( request );
                    jsonObject = writer.writeValueAsString(request);
                    Log.i("main", jsonObject + "");
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
                Log.e(LOG_TAG, "Error converting result " + e.toString());
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

}
