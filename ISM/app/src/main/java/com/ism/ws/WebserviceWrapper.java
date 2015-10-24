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
	public static final int LOGIN = 1;
	public static final int FORGOT_PASSWORD = 2;
	public static final int REQUEST_CREDENTIALS = 3;
	public static final int REGISTER_USER = 4;

	public interface WebserviceResponse {
		public void onResponse(Object object, Exception error, int apiCode);
	}

	public WebserviceWrapper(Context context, Object requestObject) {
		this.requestObject = requestObject;
		webserviceResponse = (WebserviceResponse) context;
	}

	public class WebserviceCaller extends AsyncTask<Integer, Void, Object> {

		private int currentApiCode;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Integer... params) {
			Object responseObject=null;
			try {
				currentApiCode = params[0];
				switch (currentApiCode) {
					case LOGIN:
						responseObject = new RequestWs().getRequest(AppConstant.URL_LOGIN, ResponseObject.class, requestObject);
						break;
					case FORGOT_PASSWORD:
						responseObject = new RequestWs().getRequest(AppConstant.URL_FORGOT_PASSWORD, ResponseObject.class, requestObject);
						break;
					case REGISTER_USER:
						responseObject = new RequestWs().getRequest(AppConstant.URL_REGISTER_USER, ResponseObject.class, requestObject);
						break;
				}
			} catch (Exception e) {
				Log.e(TAG, "WebserviceCaller Background Exception : " + e.toString());
			}
			return responseObject;
		}

		@Override
		protected void onPostExecute(Object o) {
			webserviceResponse.onResponse(o, null, currentApiCode);
			super.onPostExecute(o);
		}
	}

}
