package com.ism.ws.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.ism.constant.WebConstants;
import com.ism.utility.Debug;
import com.ism.utility.Utility;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by c161 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Attribute attribute;
    private WebserviceResponse webserviceResponse;
    private Context context;

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
                            responseObject = new WebserviceConnector(WebConstants.URL_UPLOAD_FEED_MEDIA).uploadMedia(ResponseHandler.class,
                                    attribute);
                            break;
                        case WebConstants.REFRESH_TOKEN:
                            responseObject = new WebserviceConnector(WebConstants.URL_REFRESH_TOKEN).execute(ResponseHandler.class, attribute);
                            break;
                        case WebConstants.GET_ADMIN_CONFIG:
                            responseObject = new WebserviceConnector(WebConstants.URL_GET_ADMIN_CONFIG).execute(ResponseHandler.class, attribute);
                            break;
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
            webserviceResponse.onResponse(responseObject, exception, currentApiCode);
            super.onPostExecute(responseObject);
        }
    }

}
