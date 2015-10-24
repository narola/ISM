package com.ism.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ism.login.AppConstant;
import com.ism.model.ResponseObject;

/**
 * Created by c161 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Object requestObject;
    private WebserviceResponse webserviceResponse;

    //	Webservice flags
    public static final int LOGIN = 0;

    public interface WebserviceResponse {
        public void onResponse(Object object, Exception error);
    }

    public WebserviceWrapper(Context context, Object requestObject) {
        this.requestObject = requestObject;
        webserviceResponse = (WebserviceResponse) context;
    }

    public class WebserviceCaller extends AsyncTask<Integer, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Integer... params) {
            Object responseObject = null;
            try {
                switch (params[0]) {
                    case LOGIN:
//						res = new RequestWs().getRequest(AppConstant.URL_LOGIN, ResponseObj.class, requestObject);
                        responseObject = new RequestWs().getRequest(AppConstant.URL_LOGIN, ResponseObject.class, requestObject);
                        break;
                }
            } catch (Exception e) {
                Log.e(TAG, "WebserviceCaller Background Exception : " + e.toString());
            }
            return responseObject;
        }

        @Override
        protected void onPostExecute(Object o) {
            webserviceResponse.onResponse(o, null);
            super.onPostExecute(o);
        }
    }

}
