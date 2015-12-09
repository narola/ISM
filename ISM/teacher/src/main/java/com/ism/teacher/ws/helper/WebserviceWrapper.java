package com.ism.teacher.ws.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ism.teacher.constants.WebConstants;

/**
 * Created by c161 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Object attribute;
    private WebserviceResponse webserviceResponse;


    public interface WebserviceResponse {
        public void onResponse(int API_METHOD, Object object, Exception error);
    }

    public WebserviceWrapper(Context context, Object attribute, WebserviceResponse listener) {
        this.attribute = attribute;
        webserviceResponse = listener;
//        this.webserviceResponse = listener;

    }

    public WebserviceWrapper(Context context, Object attribute) {
        this.attribute = attribute;
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
                        responseObject = new WebserviceConnector(WebConstants.URL_LOGIN).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GET_ALL_FEEDS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_FEEDS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.ADD_COMMENTS:
                        responseObject = new WebserviceConnector(WebConstants.URL_ADD_COMMENT).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GET_ALL_COMMENTS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_COMMENTS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GET_STUDYMATES:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_STUDYMATES).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.TAG_FRIEND_IN_FEED:
                        responseObject = new WebserviceConnector(WebConstants.URL_TAG_FRIEND_IN_FEED).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.LIKE_FEED:
                        responseObject = new WebserviceConnector(WebConstants.URL_LIKE_FEED).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GET_CLASSROOMS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETCLASSROOMS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GET_SUBJECT:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETSUBJECT).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GET_TOPICS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETTOPICS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.CREATE_ASSIGNMENT:
                        responseObject = new WebserviceConnector(WebConstants.URL_CREATEASSIGNMENT).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GET_COURSES:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_COURSES).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.CREATE_EXAM:
                        responseObject = new WebserviceConnector(WebConstants.URL_CREATE_EXAM).execute(ResponseHandler.class, attribute);
                        break;
                    case WebConstants.GET_QUESTION_BANK:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_QUESTION_BANK).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.POSTFEED:
                        responseObject = new WebserviceConnector(WebConstants.URL_POSTFEED).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.SET_QUESTIONS_FOR_EXAM:
                        responseObject = new WebserviceConnector(WebConstants.URL_SET_QUESTIONS_FOR_EXAM).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.FORGOT_PASSWORD:
                        responseObject = new WebserviceConnector(WebConstants.URL_FORGOT_PASSWORD).execute(ResponseHandler.class, attribute);
                        break;
                    case WebConstants.REQUEST_CREDENTIALS:
                        responseObject = new WebserviceConnector(WebConstants.URL_REQUEST_CREDENTIALS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GET_COUNTRIES:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_COUNTRIES).execute(ResponseHandler.class, attribute);
                        break;
                    case WebConstants.GET_STATES:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_STATES).execute(ResponseHandler.class, attribute);
                        break;
                    case WebConstants.GET_CITIES:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_CITIES).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GET_ALL_ASSIGNMENTS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_ASSIGNMENTS).execute(ResponseHandler.class, attribute);
                        break;
                    case WebConstants.GET_ALL_EXAM_SUBMISSION:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_EXAM_SUBMISSION).execute(ResponseHandler.class, attribute);
                        break;
                    case WebConstants.GET_EXAM_QUESTIONS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_EXAM_QUESTIONS).execute(ResponseHandler.class, attribute);
                        break;
                    case WebConstants.GET_EXAM_EVALUATIONS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_EXAM_EVALUATIONS).execute(ResponseHandler.class, attribute);
                        break;
                    case WebConstants.GET_MY_STUDENTS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_MY_STUDENTS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.CREATEQUESTION:
                        responseObject = new WebserviceConnector(WebConstants.URL_CREATEQUESTION).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GET_ALL_HASHTAG:
                        responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_HASHTAG).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.UPLOAD_FEED_MEDIA:
                        responseObject = new WebserviceConnector(WebConstants.URL_UPLOAD_FEED_MEDIA).uploadMedia(ResponseHandler.class,
                                (Attribute) attribute);
                        break;
                    case WebConstants.SET_HASHTAG:
                        responseObject = new WebserviceConnector(WebConstants.URL_HASHTAG).execute(ResponseHandler.class, attribute);
                        break;
                    case WebConstants.TEMP_CREATE_QUESTION:
                        responseObject = new WebserviceConnector(WebConstants.URL_TEMP_CREATE_QUESTION).execute(ResponseHandler.class, attribute);
                        break;
                    case WebConstants.UPLOADMEDIAFORQUESTION:
                        responseObject = new WebserviceConnector(WebConstants.URL_UPLOADMEDIAFORQUESTION).uploadMedia(ResponseHandler.class,
                                (Attribute) attribute);
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
