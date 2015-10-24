package com.ism.teacher.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.model.ResponseObject;

/**
 * Created by c161 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Object requestObject;
    private WebserviceResponse webserviceResponse;


    //	Webservice flags
    public static final int LOGIN = 0;
    public static final int GET_ALL_FEEDS = 1;
    public static int API_METHOD_NAME ;


    public interface WebserviceResponse {
        public void onResponse(int API_METHOD, Object object, Exception error);
    }

    public WebserviceWrapper(Context context, Object requestObject, WebserviceResponse listener) {
        this.requestObject = requestObject;
        webserviceResponse = listener;
//        this.webserviceResponse = listener;

    }

    public WebserviceWrapper(Context context, Object requestObject) {
        this.requestObject = requestObject;
        webserviceResponse = (WebserviceResponse) context;
    }


    public class WebserviceCaller extends AsyncTask<Integer, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Integer... params) {
            Object responseObject = null;
            try {
                API_METHOD_NAME = params[0];
                switch (params[0]) {
                    case LOGIN:
//						res = new RequestWs().getRequest(AppConstant.URL_LOGIN, ResponseObj.class, requestObject);
                        responseObject = new RequestWs().getRequest(AppConstant.URL_LOGIN, ResponseObject.class, requestObject);
                        break;

                    case GET_ALL_FEEDS:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GET_ALL_FEEDS, ResponseObject.class, requestObject);
                        break;
                }
            } catch (Exception e) {
                Log.e(TAG, "WebserviceCaller Background Exception : " + e.toString());
            }
            return responseObject;
        }

        @Override
        protected void onPostExecute(Object o) {
            webserviceResponse.onResponse(API_METHOD_NAME, o, null);
            super.onPostExecute(o);
        }
    }

}
