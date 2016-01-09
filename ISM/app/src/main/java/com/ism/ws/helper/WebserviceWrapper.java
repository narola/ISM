package com.ism.ws.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ism.commonsource.utility.AESHelper;
import com.ism.constant.WebConstants;
import com.ism.utility.Debug;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;

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
    private Context context;

    private boolean isSecurityEnabled = true;
    private boolean isRefreshToken;

    public interface WebserviceResponse {
        public void onResponse(Object object, Exception error, int apiCode);
    }

    public WebserviceWrapper(Context context, Attribute attribute, WebserviceResponse webserviceResponse) {
        this.attribute = attribute;
        this.webserviceResponse = webserviceResponse;
        this.context = context;
    }

    public class WebserviceCaller extends AsyncTask<Integer, Void, Object> {

        private int currentApiCode;
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

//			    Check if we can get access from the network.
                URL url = new URL(WebConstants.HOST);
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(2000); // Timeout 2 seconds.
                urlc.connect();
                isNetworkConnected = urlc.getResponseCode() == 200; //Successful response.

                if (isNetworkConnected) {
                    if (isSecurityEnabled && attribute.getSecretKey() == null && currentApiCode != WebConstants.REFRESH_TOKEN) {
                        isRefreshToken = true;
                        String globalPassword = new StudentHelper(context).getGlobalPassword();
                        if (globalPassword != null) {
                            WebConstants.ACCESS_KEY = AESHelper.encrypt(globalPassword, PreferenceData.getStringPrefs(PreferenceData.USER_NAME, context));
                            responseObject = new WebserviceConnector(WebConstants.URL_REFRESH_TOKEN).execute(ResponseHandler.class, new Attribute(WebConstants.ACCESS_KEY));
                        }
                    } else {
                        switch (currentApiCode) {
                            case WebConstants.LOGIN:
                                responseObject = new WebserviceConnector(WebConstants.URL_LOGIN).execute(ResponseHandler.class, attribute);
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
                            case WebConstants.REGISTER_USER:
                                responseObject = new WebserviceConnector(WebConstants.URL_REGISTER_USER).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.REQUEST_SCHOOL_INFO:
                                responseObject = new WebserviceConnector(WebConstants.URL_REQUEST_SCHOOL_INFO).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.ALLOCATE_TUTORIAL_GROUP:
                                responseObject = new WebserviceConnector(WebConstants.URL_ALLOCATE_TUTORIAL_GROUP).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.ACCEPT_TUTORIAL_GROUP:
                                responseObject = new WebserviceConnector(WebConstants.URL_ACCEPT_TUTORIAL_GROUP).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ALL_FEEDS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_FEEDS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.ADD_COMMENT:
                                responseObject = new WebserviceConnector(WebConstants.URL_ADD_COMMENT).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.POSTFEED:
                                responseObject = new WebserviceConnector(WebConstants.URL_POSTFEED).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ALL_COMMENTS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_COMMENTS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ALL_STUDYMATES:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_STUDYMATES).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.TAG_STUDY_MATES:
                                responseObject = new WebserviceConnector(WebConstants.URL_TAG_STUDY_MATES).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ALL_NOTICES:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_NOTICES).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GENERAL_SETTING_PREFERENCES:
                                responseObject = new WebserviceConnector(WebConstants.URL_GENERAL_SETTING_PREFERENCES).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_USER_PREFERENCES:
                                responseObject = new WebserviceConnector(WebConstants.URL_USER_PREFERENCES).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.MANAGE_GENERAL_SETTINGS:
                                responseObject = new WebserviceConnector(WebConstants.URL_MANAGE_GENERAL_SETTING).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_NOTIFICATION:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_NOTIFICATION).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_MESSAGES:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_MESSAGES).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_STUDYMATE_REQUEST:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_STUDYMATE_REQUEST).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ALL_BADGES_COUNT:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_BADGES_COUNT).execute(ResponseHandler.class, attribute);
                                break;
//                        case WebConstants.UPLOAD_PROFILE_PIC:
//                            responseObject = new RequestWs().getImageRequest(WebConstants.URL_PROFILE_PIC).execute(ResponseObject.class, attribute);
//                            break;
                            case WebConstants.GET_ABOUT_ME:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ABOUT_ME).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.RESPOND_TO_REQUEST:
                                responseObject = new WebserviceConnector(WebConstants.URL_RESPOND_TO_REQUEST).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.UPDATE_READ_STATUS:
                                responseObject = new WebserviceConnector(WebConstants.URL_UPDATE_READ_STATUS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.EDIT_ABOUT_ME:
                                responseObject = new WebserviceConnector(WebConstants.URL_EDIT_ABOUT_ME).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_HIGH_SCORERS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_HIGH_SCORERS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_BOOKS_FOR_USER:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_BOOKS_FOR_USER).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_MOVIES_FOR_USER:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_MOVIES_FOR_USER).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_PASTTIME_FOR_USER:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_PASTTIME_FOR_USER).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ROLEMODEL_FOR_USER:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ROLEMODEL_FOR_USER).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.MANAGE_FAVOURITES:
                                responseObject = new WebserviceConnector(WebConstants.URL_MANAGE_FAVOURITES).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_WALLET_SUMMARY:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_WALLET_SUMMARY).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GENERATE_VOUCHER:
                                responseObject = new WebserviceConnector(WebConstants.URL_GENERATE_VOUCHER).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.BLOCK_USER:
                                responseObject = new WebserviceConnector(WebConstants.URL_BLOCK_USER).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.BLOCKED_USER:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_BLOCKED_USER).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_MY_FEEDS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_MY_FEEDS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.MANAGE_BOOK_LIBRARY:
                                responseObject = new WebserviceConnector(WebConstants.URL_MANAGE_BOOK_LIBRARY).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ALL_STUDYMATES_WITH_DETAILS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_STUDYMATES_WITH_DETAILS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ALL_RECOMMENDED_STUDYMATES:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_RECOMMENDED_STUDYMATES).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_MY_ACTIVITY:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_MY_ACTIVITY).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.UPLOAD_FEED_MEDIA:
                                responseObject = new WebserviceConnector(WebConstants.URL_UPLOAD_FEED_MEDIA).uploadMedia(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.REFRESH_TOKEN:
                                responseObject = new WebserviceConnector(WebConstants.URL_REFRESH_TOKEN).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ADMIN_CONFIG:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ADMIN_CONFIG).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.LIKE_FEED:
                                responseObject = new WebserviceConnector(WebConstants.URL_LIKE_FEED).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_GROUP_HISTORY:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_GROUP_HISTORY).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_GROUP_PROFILE:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_GROUP_PROFILE).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ALL_NOTES:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_NOTES).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.SUBMIT_QUESTION_FOR_FRIDAY:
                                responseObject = new WebserviceConnector(WebConstants.URL_SUBMIT_QUESTION_FOR_FRIDAY).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.CHECK_FRIDAY_EXAM_STATUS:
                                responseObject = new WebserviceConnector(WebConstants.URL_CHECK_FRIDAY_EXAM_STATUS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ALL_BOOKS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_BOOKS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_FRIDAY_EXAM_QUESTIONS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_FRIDAY_EXAM_QUESTIONS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.SUBMIT_STUDENT_OBJECTIVE_RESPONSE:
                                responseObject = new WebserviceConnector(WebConstants.URL_SUBMIT_STUDENT_OBJECTIVE_RESPONSE).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_AUTHOR_BOOK_ASSIGNMENT:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_AUTHOR_BOOK_ASSIGNMENT).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_BOOKS_BY_AUTHOR:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_BOOKS_BY_AUTHOR).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ASSIGNMENT_BY_BOOK:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ASSIGNMENT_BY_BOOK).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_FOLLOW_USER:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_FOLLOW_USER).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_RECOMMENDED_AUTHORS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_RECOMMENDED_AUTHORS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.ALLOCATE_TEACHER_TO_GROUP:
                                responseObject = new WebserviceConnector(WebConstants.URL_ALLOCATE_TEACHER_TO_GROUP).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_SUNDAY_EXAM_QUESTION:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_SUNDAY_EXAM_QUESTION).execute(ResponseHandler.class, attribute);
                                break;
                        }
                    }
                }
            } catch (Exception e) {
                Debug.e(TAG, "WebserviceCaller Background Exception : " + e.toString());
                exception = e;
            }
            return responseObject;
        }

        @Override
        protected void onPostExecute(Object responseObject) {
            if (!isNetworkConnected) {
                Utility.alertServerNotConnected(context);
            }
            if (isSecurityEnabled && isRefreshToken) {
                isRefreshToken = false;
                try {
                    if (responseObject != null) {
                        ResponseHandler responseHandler = (ResponseHandler) responseObject;
                        if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                            PreferenceData.setStringPrefs(PreferenceData.SECRET_KEY, context, responseHandler.getToken().get(0).getTokenName());
                            WebConstants.SECRET_KEY = responseHandler.getToken().get(0).getTokenName();
                            attribute.setSecretKey(WebConstants.SECRET_KEY);
                            new WebserviceCaller().execute(currentApiCode);
                        } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                            Log.e(TAG, "onResponseRefreshToken failed");
                        }
                    } else if (exception != null) {
                        Log.e(TAG, "onResponseRefreshToken api Exception : " + exception.toString());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponseRefreshToken Exception : " + e.toString());
                }
            } else {
                webserviceResponse.onResponse(responseObject, exception, currentApiCode);
            }
            super.onPostExecute(responseObject);
        }
    }

}
