package com.ism.teacher.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.model.ResponseObject;

/**
 * Created by c161 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Object requestObject;
    private WebserviceResponse webserviceResponse;




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
                    case WebConstants.LOGIN:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_LOGIN, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GET_ALL_FEEDS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_ALL_FEEDS, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.ADD_COMMENTS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_ADD_COMMENT, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GET_ALL_COMMENTS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_ALL_COMMENTS, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GET_STUDYMATES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_STUDYMATES, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.TAG_FRIEND_IN_FEED:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_TAG_FRIEND_IN_FEED, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.LIKE_FEED:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_LIKE_FEED, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETCLASSROOMS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GETCLASSROOMS, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETSUBJECT:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GETSUBJECT, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETTOPICS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GETTOPICS, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.CREATEASSIGNMENT:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_CREATEASSIGNMENT, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETCOURSES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_COURSES, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.CREATEEXAM:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_CREATE_EXAM, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GETQUESTIONBANK:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GETQUESTIONBANK, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.POSTFEED:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_POSTFEED, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.SET_QUESTIONS_FOR_EXAM:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_SET_QUESTIONS_FOR_EXAM, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.FORGOT_PASSWORD:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_FORGOT_PASSWORD, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.REQUEST_CREDENTIALS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_REQUEST_CREDENTIALS, ResponseObject.class, requestObject);
                        break;


                    case WebConstants.GET_COUNTRIES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_COUNTRIES, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_STATES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_STATES, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_CITIES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_CITIES, ResponseObject.class, requestObject);
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
