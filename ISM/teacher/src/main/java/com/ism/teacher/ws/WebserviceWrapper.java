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
    public static final int ADD_COMMENTS = 2;
    public static final int GET_ALL_COMMENTS = 3;
    public static final int GET_STUDYMATES = 4;
    public static final int TAG_FRIEND_IN_FEED = 5;
    public static final int LIKE_FEED = 6;

    public static final int GETCLASSROOMS = 7;
    public static final int GETSUBJECT = 8;
    public static final int GETTOPICS = 9;
    public static final int CREATEASSIGNMENT = 10;
    public static final int GETCOURSES = 11;
    public static final int CREATEEXAM = 12;
    public static final int GETQUESTIONBANK = 14;


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

        int API_METHOD_NAME;

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
                        responseObject = new RequestWs().getRequest(AppConstant.URL_LOGIN, ResponseObject.class, requestObject);
                        break;

                    case GET_ALL_FEEDS:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GET_ALL_FEEDS, ResponseObject.class, requestObject);
                        break;

                    case ADD_COMMENTS:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_ADD_COMMENT, ResponseObject.class, requestObject);
                        break;

                    case GET_ALL_COMMENTS:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GET_ALL_COMMENTS, ResponseObject.class, requestObject);
                        break;

                    case GET_STUDYMATES:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GET_STUDYMATES, ResponseObject.class, requestObject);
                        break;

                    case TAG_FRIEND_IN_FEED:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_TAG_FRIEND_IN_FEED, ResponseObject.class, requestObject);
                        break;

                    case LIKE_FEED:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_LIKE_FEED, ResponseObject.class, requestObject);
                        break;

                    case GETCLASSROOMS:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GETCLASSROOMS, ResponseObject.class, requestObject);
                        break;

                    case GETSUBJECT:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GETSUBJECT, ResponseObject.class, requestObject);
                        break;

                    case GETTOPICS:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GETTOPICS, ResponseObject.class, requestObject);
                        break;

                    case CREATEASSIGNMENT:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_CREATEASSIGNMENT, ResponseObject.class, requestObject);
                        break;

                    case GETCOURSES:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GET_COURSES, ResponseObject.class, requestObject);
                        break;

                    case CREATEEXAM:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_CREATE_EXAM, ResponseObject.class, requestObject);
                        break;
                    case GETQUESTIONBANK:
                        responseObject = new RequestWs().getRequest(AppConstant.URL_GETQUESTIONBANK, ResponseObject.class, requestObject);
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
