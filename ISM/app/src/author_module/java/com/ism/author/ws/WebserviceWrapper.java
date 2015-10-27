package com.ism.author.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ism.author.login.Urls;
import com.ism.author.model.ResponseObject;


/**
 * Created by c166 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Object requestObject;
    private WebserviceResponse webserviceResponse;

    //	Webservice flags
    public static final int LOGIN = 0;
    public static final int GETALLFEEDS = 1;
    public static final int ADDCOMMENT = 2;
    public static final int GETSTUDYMATES = 3;
    public static final int TAGFRIENDINFEED = 4;
    public static final int GETALLCOMMENTS = 5;

    public static int API_METHOD_NAME;


    public interface WebserviceResponse {
        public void onResponse(int API_METHOD, Object object, Exception error);
    }

    public WebserviceWrapper(Context context, Object requestObject, WebserviceResponse listener) {

        this.requestObject = requestObject;
        webserviceResponse = listener;

    }

//    public WebserviceWrapper(Context context, Object requestObject) {
//        this.requestObject = requestObject;
//        webserviceResponse = (WebserviceResponse) context;
//
//    }


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
                switch (API_METHOD_NAME) {
                    case LOGIN:
                        responseObject = new com.ism.ws.RequestWs().getRequest(Urls.URL_LOGIN, ResponseObject.class, requestObject);
                        break;

                    case GETALLFEEDS:
                        responseObject = new com.ism.ws.RequestWs().getRequest(Urls.URL_GETALLFEEDS, ResponseObject.class, requestObject);
                        break;

                    case ADDCOMMENT:
                        responseObject = new com.ism.ws.RequestWs().getRequest(Urls.URL_ADDCOMMENT, ResponseObject.class, requestObject);
                        break;

                    case GETSTUDYMATES:
                        responseObject = new com.ism.ws.RequestWs().getRequest(Urls.URL_GETSTUDYMATES, ResponseObject.class, requestObject);
                        break;

                    case TAGFRIENDINFEED:
                        responseObject = new com.ism.ws.RequestWs().getRequest(Urls.URL_TAGFRIENDINFEED, ResponseObject.class, requestObject);
                        break;

                    case GETALLCOMMENTS:
                        responseObject = new com.ism.ws.RequestWs().getRequest(Urls.URL_GETALLCOMMENTS, ResponseObject.class, requestObject);
                        break;

                }
            } catch (Exception e) {
                Log.e(TAG, "WebserviceCaller Background Exception : " + e.toString());
            }
            return responseObject;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            webserviceResponse.onResponse(API_METHOD_NAME, o, null);


        }
    }

}
