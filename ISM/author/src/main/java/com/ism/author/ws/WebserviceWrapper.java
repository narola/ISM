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

    //	Webservice flags
    public static final int LOGIN = 0;
    public static final int GETALLFEEDS = 1;
    public static final int GETALLCOMMENTS = 2;
    public static final int ADDCOMMENT = 3;
    public static final int GETSTUDYMATES = 4;
    public static final int TAGFRIENDINFEED = 5;
    public static final int LIKEFEED = 6;
    public static final int GETCLASSROOMS = 7;
    public static final int GETSUBJECT = 8;
    public static final int GETTOPICS = 9;
    public static final int CREATEASSIGNMENT = 10;
    public static final int POSTFEED = 11;
    public static final int GETCOURSES = 12;
    public static final int CREATEEXAM = 13;
    public static final int GETQUESTIONBANK = 14;
    public static final int GETALLEXAM = 15;
    public static final int GETEXAMSUBMISSION = 16;
    public static final int SETQUESTIONSFOREXAM = 17;


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
            showProgressDialog();
        }

        @Override
        protected Object doInBackground(Integer... params) {
            Object responseObject = null;

            try {

                API_METHOD_NAME = params[0];
                switch (API_METHOD_NAME) {
                    case LOGIN:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_LOGIN, ResponseObject.class, requestObject);
                        break;

                    case GETALLFEEDS:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETALLFEEDS, ResponseObject.class, requestObject);
                        break;

                    case ADDCOMMENT:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_ADDCOMMENT, ResponseObject.class, requestObject);
                        break;

                    case GETSTUDYMATES:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETSTUDYMATES, ResponseObject.class, requestObject);
                        break;

                    case TAGFRIENDINFEED:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_TAGFRIENDINFEED, ResponseObject.class, requestObject);
                        break;

                    case GETALLCOMMENTS:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETALLCOMMENTS, ResponseObject.class, requestObject);
                        break;

                    case LIKEFEED:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_LIKEFEED, ResponseObject.class, requestObject);
                        break;

                    case GETCLASSROOMS:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETCLASSROOMS, ResponseObject.class, requestObject);
                        break;

                    case GETSUBJECT:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETSUBJECT, ResponseObject.class, requestObject);
                        break;

                    case GETTOPICS:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETTOPICS, ResponseObject.class, requestObject);
                        break;

                    case CREATEASSIGNMENT:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_CREATEASSIGNMENT, ResponseObject.class, requestObject);
                        break;
                    case POSTFEED:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_POSTFEED, ResponseObject.class, requestObject);
                        break;

                    case GETCOURSES:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GET_COURSES, ResponseObject.class, requestObject);
                        break;

                    case CREATEEXAM:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_CREATE_EXAM, ResponseObject.class, requestObject);
                        break;

                    case GETQUESTIONBANK:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GETQUESTIONBANK, ResponseObject.class, requestObject);
                        break;

                    case GETALLEXAM:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GET_ALL_EXAM, ResponseObject.class, requestObject);
                        break;

                    case SETQUESTIONSFOREXAM:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_SET_QUESTIONS_FOR_EXAM, ResponseObject.class, requestObject);
                        break;
                    case GETEXAMSUBMISSION:
                        responseObject = new com.ism.author.ws.RequestWs().getRequest(WebConstants.URL_GET_EXAM_SUBMISSION, ResponseObject.class, requestObject);

                        Debug.i(TAG,"Response object :" + responseObject);break;

                }
            } catch (Exception e) {
                Debug.i(TAG, "WebserviceCaller Background Exception : " + e.toString());
            }
            return responseObject;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Debug.i(TAG, "WebserviceCaller Response : " + o.toString());
            dismissProgressDialog();
            webserviceResponse.onResponse(API_METHOD_NAME, o, null);

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
