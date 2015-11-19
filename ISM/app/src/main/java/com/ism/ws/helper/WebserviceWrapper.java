package com.ism.ws.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ism.constant.WebConstants;
import com.ism.ws.RequestWs;
import com.ism.ws.model.ResponseGetCountries;
import com.ism.ws.model.ResponseLogin;
import com.ism.ws.model.ResponseObject;
import com.ism.ws.model.ResponseStatus;

/**
 * Created by c161 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Object attribute;
    private WebserviceResponse webserviceResponse;

    public interface WebserviceResponse {
        public void onResponse(Object object, Exception error, int apiCode);
    }

    public WebserviceWrapper(Context context, Object attribute, WebserviceResponse webserviceResponse) {
        this.attribute = attribute;
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
//                        responseObject = new WSRequestPost(WebConstants.URL_LOGIN, ResponseLogin.class, attribute);
                        responseObject = new WSRequestPost(WebConstants.URL_LOGIN).execute(ResponseLogin.class, attribute);
                        break;
                    case WebConstants.FORGOT_PASSWORD:
                        responseObject = new WSRequestPost(WebConstants.URL_FORGOT_PASSWORD).execute(ResponseStatus.class, attribute);
                        break;
                    case WebConstants.REQUEST_CREDENTIALS:
                        responseObject = new WSRequestPost(WebConstants.URL_REQUEST_CREDENTIALS).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_COUNTRIES:
	                    responseObject = new WSRequestPost(WebConstants.URL_GET_COUNTRIES).execute(ResponseGetCountries.class, attribute);
                        break;
                    case WebConstants.GET_STATES:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_STATES).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_CITIES:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_CITIES).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.REGISTER_USER:
                        responseObject = new WSRequestPost(WebConstants.URL_REGISTER_USER).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.REQUEST_SCHOOL_INFO:
                        responseObject = new WSRequestPost(WebConstants.URL_REQUEST_SCHOOL_INFO).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.ALLOCATE_TUTORIAL_GROUP:
                        responseObject = new WSRequestPost(WebConstants.URL_ALLOCATE_TUTORIAL_GROUP).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.ACCEPT_TUTORIAL_GROUP:
                        responseObject = new WSRequestPost(WebConstants.URL_ACCEPT_TUTORIAL_GROUP).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_ALL_FEEDS:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_ALL_FEEDS).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.ADD_COMMENT:
                        responseObject = new WSRequestPost(WebConstants.URL_ADD_COMMENT).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_ALL_COMMENTS:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_ALL_COMMENTS).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_ALL_STUDY_MATES:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_ALL_STUDY_MATES).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.TAG_STUDY_MATES:
                        responseObject = new WSRequestPost(WebConstants.URL_TAG_STUDY_MATES).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_ALL_NOTICES:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_ALL_NOTICES).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GENERAL_SETTING_PREFERENCES:
                        responseObject = new WSRequestPost(WebConstants.URL_GENERAL_SETTING_PREFERENCES).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GENERAL_SETTINGS:
                        responseObject = new WSRequestPost(WebConstants.URL_GENERAL_SETTING).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_USER_PREFERENCES:
                        responseObject = new WSRequestPost(WebConstants.URL_USER_PREFERENCES).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_NOTIFICATION:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_NOTIFICATION).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_MESSAGES:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_MESSAGES).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_STUDYMATE_REQUEST:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_STUDYMATE_REQUEST).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_ALL_BADGES_COUNT:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_ALL_BADGES_COUNT).execute(ResponseObject.class, attribute);
                        break;
//                    case WebConstants.UPLOAD_PROFILE_PIC:
//                        responseObject = new RequestWs().getImageRequest(WebConstants.URL_PROFILE_PIC).execute(ResponseObject.class, attribute);
//                        break;
                    case WebConstants.GET_ABOUT_ME:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_ABOUT_ME).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.RESPOND_TO_REQUEST:
                        responseObject = new WSRequestPost(WebConstants.URL_RESPOND_TO_REQUEST).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.UPDATE_READ_STATUS:
                        responseObject = new WSRequestPost(WebConstants.URL_UPDATE_READ_STATUS).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.EDIT_ABOUT_ME:
                        responseObject = new WSRequestPost(WebConstants.URL_EDIT_ABOUT_ME).execute(ResponseObject.class, attribute);
                        break;
                    case WebConstants.GET_HIGH_SCORERS:
                        responseObject = new WSRequestPost(WebConstants.URL_GET_HIGH_SCORERS).execute(ResponseObject.class, attribute);
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
