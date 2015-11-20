package com.ism.author.ws;

import android.content.Context;
import android.os.AsyncTask;

import com.ism.author.Utility.Debug;
import com.ism.author.constant.WebConstants;
import com.ism.author.ws.model.ResponseHandler;


/**
 * Created by c166 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Object attribute;
    private WebserviceResponse webserviceResponse;
    private Context mContext;

    public interface WebserviceResponse {
        public void onResponse(int API_METHOD, Object object, Exception error);
    }

    public WebserviceWrapper(Context context, Object attribute, WebserviceResponse listener) {
        this.attribute = attribute;
        webserviceResponse = listener;
        this.mContext = context;

    }

    public class WebserviceCaller extends AsyncTask<Integer, Void, Object> {

        public int API_METHOD_NAME;

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
                    case WebConstants.LOGIN:
                        responseObject = new WebserviceConnector(WebConstants.URL_LOGIN).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.FORGOTPASSWORD:
//                        responseObject = new RequestWs().getRequest(WebConstants.URL_FORGOTPASSWORD, ResponseHandler.class, attribute);
                        responseObject = new WebserviceConnector(WebConstants.URL_FORGOTPASSWORD).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.REQUESTCREDENTIALS:
                        responseObject = new WebserviceConnector(WebConstants.URL_REQUESTCREDENTIALS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETCOUNTRIES:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETCOUNTRIES).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETSTATES:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETSTATES).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETCITIES:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETCITIES).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.REQUESTSCHOOLINFO:
                        responseObject = new WebserviceConnector(WebConstants.URL_REQUESTSCHOOLINFO).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.REGISTERUSER:
                        responseObject = new WebserviceConnector(WebConstants.URL_REGISTERUSER).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETALLFEEDS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETALLFEEDS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.ADDCOMMENT:
                        responseObject = new WebserviceConnector(WebConstants.URL_ADDCOMMENT).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETSTUDYMATES:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETSTUDYMATES).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.TAGFRIENDINFEED:
                        responseObject = new WebserviceConnector(WebConstants.URL_TAGFRIENDINFEED).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETALLCOMMENTS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETALLCOMMENTS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.LIKEFEED:
                        responseObject = new WebserviceConnector(WebConstants.URL_LIKEFEED).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETCLASSROOMS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETCLASSROOMS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETSUBJECT:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETSUBJECT).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETTOPICS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETTOPICS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.CREATEASSIGNMENT:
                        responseObject = new WebserviceConnector(WebConstants.URL_CREATEASSIGNMENT).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.POSTFEED:
                        responseObject = new WebserviceConnector(WebConstants.URL_POSTFEED).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETCOURSES:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETCOURSES).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.CREATEEXAM:
                        responseObject = new WebserviceConnector(WebConstants.URL_CREATEEXAM).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETQUESTIONBANK:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETQUESTIONBANK).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETALLEXAM:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETALLEXAM).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETEXAMEVALUATIONS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETEXAMEVALUATIONS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETEXAMQUESTIONS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETEXAMQUESTIONS).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.SETQUESTIONSFOREXAM:
                        responseObject = new WebserviceConnector(WebConstants.URL_SETQUESTIONSFOREXAM).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETEXAMSUBMISSION:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETEXAMSUBMISSION).execute(ResponseHandler.class, attribute);
                        break;

                    case WebConstants.GETALLASSIGNMENTS:
                        responseObject = new WebserviceConnector(WebConstants.URL_GETALLASSIGNMENTS).execute(ResponseHandler.class, attribute);
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

            try {
                webserviceResponse.onResponse(API_METHOD_NAME, o, null);
                Debug.i(TAG, "WebserviceCaller Response : " + o.toString());
            } catch (Exception e) {
                Debug.i(TAG, "WebserviceCaller Response Exception : " + e.toString());
            }

        }
    }


}
