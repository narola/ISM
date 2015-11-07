package com.ism.author.ws;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.constant.WebConstants;
import com.ism.author.model.ResponseObject;


/**
 * Created by c166 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Object requestObject;
    private WebserviceResponse webserviceResponse;
    private Context mContext;

    public interface WebserviceResponse {
        public void onResponse(int API_METHOD, Object object, Exception error);
    }

    public WebserviceWrapper(Context context, Object requestObject, WebserviceResponse listener) {
        this.requestObject = requestObject;
        webserviceResponse = listener;
        this.mContext = context;

    }

    public class WebserviceCaller extends AsyncTask<Integer, Void, Object> {

        public int API_METHOD_NAME;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            showProgressDialog();
        }

        @Override
        protected Object doInBackground(Integer... params) {
            Object responseObject = null;

            try {

                API_METHOD_NAME = params[0];
                switch (API_METHOD_NAME) {
                    case WebConstants.LOGIN:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_LOGIN, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.FORGOTPASSWORD:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_FORGOT_PASSWORD, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.REQUESTCREDENTIALS:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_REQUEST_CREDENTIALS, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETCOUNTRIES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_COUNTRIES, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETSTATES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_STATES, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETCITIES:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_GET_CITIES, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.REQUESTSCHOOLINFO:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_REQUEST_SCHOOL_INFO, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.REGISTERUSER:
                        responseObject = new RequestWs().getRequest(WebConstants.URL_REGISTER_USER, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETALLFEEDS:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETALLFEEDS, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.ADDCOMMENT:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_ADDCOMMENT, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETSTUDYMATES:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETSTUDYMATES, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.TAGFRIENDINFEED:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_TAGFRIENDINFEED, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETALLCOMMENTS:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETALLCOMMENTS, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.LIKEFEED:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_LIKEFEED, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETCLASSROOMS:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETCLASSROOMS, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETSUBJECT:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETSUBJECT, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETTOPICS:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETTOPICS, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.CREATEASSIGNMENT:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_CREATEASSIGNMENT, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.POSTFEED:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_POSTFEED, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETCOURSES:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GET_COURSES, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.CREATEEXAM:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_CREATE_EXAM, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETQUESTIONBANK:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETQUESTIONBANK, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETALLEXAM:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GET_ALL_EXAM, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GETEXAMEVALUATIONS:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GET_EXAM_EVALUATIONS, ResponseObject.class, requestObject);
                        break;
                    case WebConstants.GETEXAMQUESTIONS:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GET_EXAM_QUESTIONS, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.SETQUESTIONSFOREXAM:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_SET_QUESTIONS_FOR_EXAM, ResponseObject.class, requestObject);
                        break;

                    case WebConstants.GETEXAMSUBMISSION:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GET_EXAM_SUBMISSION, ResponseObject.class, requestObject);

                        Debug.i(TAG, "Response object :" + responseObject);
                        break;

                }
            } catch (Exception e) {
                Debug.i(TAG, "WebserviceCaller Background Exception : " + e.toString());
            }
            return responseObject;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Debug.i(TAG, "WebserviceCaller Response : " + o);
            dismissProgressDialog();
            webserviceResponse.onResponse(API_METHOD_NAME, o, null);
            try {
                Debug.i(TAG, "WebserviceCaller Response : " + o.toString());
//                dismissProgressDialog();
                webserviceResponse.onResponse(API_METHOD_NAME, o, null);
            } catch (Exception e) {
                Debug.i(TAG, "WebserviceCaller Response Exception : " + e.toString());
            }

        }
    }

    ProgressDialog pd;

    private void showProgressDialog() {

        pd = new ProgressDialog(mContext);
        pd.setMessage(mContext.getString(R.string.loading));
        pd.show();

    }

    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
        }
    }

}
