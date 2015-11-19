package com.ism.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ism.constant.WebConstants;
import com.ism.ws.model.ResponseObject;

/**
 * Created by c161 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Object requestObject;
    private WebserviceResponse webserviceResponse;

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
                    case WebConstants.LOGIN:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_LOGIN, ResponseObject.class, requestObject);
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
                    case WebConstants.REGISTER_USER:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_REGISTER_USER, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.REQUEST_SCHOOL_INFO:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_REQUEST_SCHOOL_INFO, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.ALLOCATE_TUTORIAL_GROUP:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_ALLOCATE_TUTORIAL_GROUP, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.ACCEPT_TUTORIAL_GROUP:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_ACCEPT_TUTORIAL_GROUP, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_ALL_FEEDS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_ALL_FEEDS, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.ADD_COMMENT:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_ADD_COMMENT, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_ALL_COMMENTS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_ALL_COMMENTS, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_ALL_STUDY_MATES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_ALL_STUDY_MATES, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.TAG_STUDY_MATES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_TAG_STUDY_MATES, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_ALL_NOTICES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_ALL_NOTICES, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GENERAL_SETTING_PREFERENCES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GENERAL_SETTING_PREFERENCES, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GENERAL_SETTINGS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GENERAL_SETTING, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_USER_PREFERENCES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_USER_PREFERENCES, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_NOTIFICATION:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_NOTIFICATION, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_MESSAGES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_MESSAGES, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_STUDYMATE_REQUEST:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_STUDYMATE_REQUEST, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_ALL_BADGES_COUNT:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_ALL_BADGES_COUNT, ResponseObject.class, requestObject);
                        break;
//                    case WebConstants.UPLOAD_PROFILE_PIC:
//                        responseObject = new RequestWs().getImageRequest(WebConstants.URL_PROFILE_PIC, ResponseObject.class, requestObject);
//                        break;
                    case WebConstants.GET_ABOUT_ME:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_ABOUT_ME, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.RESPOND_TO_REQUEST:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_RESPOND_TO_REQUEST, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.UPDATE_READ_STATUS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_UPDATE_READ_STATUS, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.EDIT_ABOUT_ME:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_EDIT_ABOUT_ME, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GET_HIGH_SCORERS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_HIGH_SCORERS, ResponseObject.class, requestObject);
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
