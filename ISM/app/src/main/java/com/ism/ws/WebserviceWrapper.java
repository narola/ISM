package com.ism.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ism.login.AppConstant;

/**
 * Created by c161 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Object requestObject;
    private WebserviceResponse webserviceResponse;

    //	Webservice flags
    public static final int LOGIN = 1;
    public static final int FORGOT_PASSWORD = 2;
    public static final int REQUEST_CREDENTIALS = 3;
    public static final int GET_COUNTRIES = 4;
    public static final int GET_STATES = 5;
    public static final int GET_CITIES = 6;
    public static final int REGISTER_USER = 7;
    public static final int REQUEST_SCHOOL_INFO = 8;
    public static final int ALLOCATE_TUTORIAL_GROUP = 9;
    public static final int ACCEPT_TUTORIAL_GROUP = 10;
    public static final int GET_ALL_FEEDS = 11;
    public static final int ADD_COMMENT = 12;
    public static final int GET_ALL_COMMENTS = 13;
    public static final int GET_ALL_STUDY_MATES = 14;
    public static final int TAG_STUDY_MATES = 15;

    public interface WebserviceResponse {
        public void onResponse(Object object, Exception error, int apiCode);
    }

    public WebserviceWrapper(Context context, Object requestObject, WebserviceResponse webserviceResponse) {
        this.requestObject = requestObject;
        this.webserviceResponse = webserviceResponse;
    }

    public class WebserviceCaller extends AsyncTask<Integer, Void, Object> {

        private int currentApiCode;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Integer... params) {
            Object responseObject = null;
            try {
                currentApiCode = params[0];
                switch (currentApiCode) {
                    case LOGIN:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_LOGIN, ResponseObject.class, requestObject);
                        break;
                    case FORGOT_PASSWORD:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_FORGOT_PASSWORD, ResponseObject.class, requestObject);
                        break;
                    case REQUEST_CREDENTIALS:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_REQUEST_CREDENTIALS, ResponseObject.class, requestObject);
                        break;
                    case GET_COUNTRIES:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GET_COUNTRIES, ResponseObject.class, requestObject);
                        break;
                    case GET_STATES:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GET_STATES, ResponseObject.class, requestObject);
                        break;
                    case GET_CITIES:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GET_CITIES, ResponseObject.class, requestObject);
                        break;
                    case REGISTER_USER:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_REGISTER_USER, ResponseObject.class, requestObject);
                        break;
                    case REQUEST_SCHOOL_INFO:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_REQUEST_SCHOOL_INFO, ResponseObject.class, requestObject);
                        break;
                    case ALLOCATE_TUTORIAL_GROUP:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_ALLOCATE_TUTORIAL_GROUP, ResponseObject.class, requestObject);
                        break;
                    case ACCEPT_TUTORIAL_GROUP:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_ACCEPT_TUTORIAL_GROUP, ResponseObject.class, requestObject);
                        break;
                    case GET_ALL_FEEDS:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GET_ALL_FEEDS, ResponseObject.class, requestObject);
                        break;
                    case ADD_COMMENT:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_ADD_COMMENT, ResponseObject.class, requestObject);
                        break;
                    case GET_ALL_COMMENTS:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GET_ALL_COMMENTS, ResponseObject.class, requestObject);
                        break;
                    case GET_ALL_STUDY_MATES:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GET_ALL_STUDY_MATES, ResponseObject.class, requestObject);
                        break;
                    case TAG_STUDY_MATES:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_TAG_STUDY_MATES, ResponseObject.class, requestObject);
                        break;
                }
            } catch (Exception e) {
                Log.e(TAG, "WebserviceCaller Background Exception : " + e.toString());
            }
            return responseObject;
        }

        @Override
        protected void onPostExecute(Object o) {
            try {
                webserviceResponse.onResponse(o, null, currentApiCode);
            } catch (Exception e) {
                webserviceResponse.onResponse(null, e, currentApiCode);
            }
            super.onPostExecute(o);
        }
    }

}
