package com.ism.teacher.ws.helper;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ism.commonsource.utility.AESHelper;
import com.ism.teacher.Utility.PreferenceData;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.constants.WebConstants;

import java.net.HttpURLConnection;
import java.net.URL;

import realmhelper.StudentHelper;

/**
 * Created by c161 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Attribute attribute;
    private WebserviceResponse webserviceResponse;
    private Activity activity;
    private Context mContext;

    /**
     * Security
     */
    private boolean isSecurityEnabled = true;
    private boolean isRefreshToken;

    public interface WebserviceResponse {
        public void onResponse(int API_METHOD, Object object, Exception error);
    }

    public WebserviceWrapper(Context context, Attribute attribute, WebserviceResponse listener) {
        this.attribute = attribute;
        webserviceResponse = listener;
        activity = (Activity) context;
        mContext = context;
    }

    public WebserviceWrapper(Context context, Attribute attribute) {
        this.attribute = attribute;
        webserviceResponse = (WebserviceResponse) context;
    }


    public class WebserviceCaller extends AsyncTask<Integer, Void, Object> {

        int currentApiCode;
        private boolean isNetworkConnected = false;
        private Exception exception;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Integer... params) {
            Object responseObject = null;
            currentApiCode = params[0];
            try {

                URL url = new URL(WebConstants.URL_KINJAL_HOST);
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(2000); // Timeout 2 seconds.
                urlc.connect();
                isNetworkConnected = urlc.getResponseCode() == 200; //Successful response.

                if (isNetworkConnected) {
                    if (isSecurityEnabled && attribute.getSecretKey() == null && currentApiCode != WebConstants.REFRESH_TOKEN) {
                        isRefreshToken = true;
                        String globalPassword = new StudentHelper(mContext).getGlobalPassword();
                        if (globalPassword != null) {
                            WebConstants.ACCESS_KEY = AESHelper.encrypt(globalPassword, PreferenceData.getStringPrefs(PreferenceData.USER_NAME, mContext));
                            responseObject = new WebserviceConnector(WebConstants.URL_REFRESHTOKEN).execute(ResponseHandler.class, new Attribute(WebConstants.ACCESS_KEY));
                        }
                    } else {
                        switch (params[0]) {
                            case WebConstants.REFRESH_TOKEN:
                                responseObject = new WebserviceConnector(WebConstants.URL_REFRESHTOKEN).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GETADMINCONFIG:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETADMINCONFIG).execute(ResponseHandler.class, attribute);
                                break;

                            case WebConstants.REGISTERUSER:
                                responseObject = new WebserviceConnector(WebConstants.URL_REGISTERUSER).execute(ResponseHandler.class, attribute);
                                break;

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
                                         attribute);
                                break;
                            case WebConstants.SET_HASHTAG:
                                responseObject = new WebserviceConnector(WebConstants.URL_HASHTAG).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.TEMP_CREATE_QUESTION:
                                responseObject = new WebserviceConnector(WebConstants.URL_TEMP_CREATE_QUESTION).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.UPLOADMEDIAFORQUESTION:
                                responseObject = new WebserviceConnector(WebConstants.URL_UPLOADMEDIAFORQUESTION).uploadMedia(ResponseHandler.class,
                                        attribute);
                                break;
                            case WebConstants.UPLOADPROFILEIMAGES:
                                responseObject = new WebserviceConnector(WebConstants.URL_UPLOADPROFILEIMAGES).uploadMedia(ResponseHandler.class, attribute);
                                break;

                        }
                    }
                }


            } catch (Exception e) {
                Log.e(TAG, "WebserviceCaller Background Exception : " + e.toString());
                exception = e;
            }
            return responseObject;
        }

        @Override
        protected void onPostExecute(Object responseObject) {
//            if (activity != null) {
//                webserviceResponse.onResponse(currentApiCode, o, null);
//                super.onPostExecute(o);
//
//            }
            if (!isNetworkConnected) {
                Utility.alertServerNotConnected(mContext);
            }

            if (isSecurityEnabled && isRefreshToken) {
                isRefreshToken = false;
                try {
                    if (responseObject != null) {
                        ResponseHandler responseHandler = (ResponseHandler) responseObject;
                        if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                            PreferenceData.setStringPrefs(PreferenceData.SECRET_KEY, mContext, responseHandler.getToken().get(0).getTokenName());
                            WebConstants.SECRET_KEY = responseHandler.getToken().get(0).getTokenName();
                            attribute.setSecretKey(WebConstants.SECRET_KEY);
                            new WebserviceCaller().execute(currentApiCode);
                        } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                            Log.e(TAG, "onResponseRefreshToken failed");
                        }
                    } else if (exception != null) {
                        Log.e(TAG, "onResponseRefreshToken api Exception : " + exception.toString());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponseRefreshToken Exception : " + e.toString());
                }
            } else {
                webserviceResponse.onResponse(currentApiCode, responseObject, exception);
            }

            super.onPostExecute(responseObject);


        }
    }

}
