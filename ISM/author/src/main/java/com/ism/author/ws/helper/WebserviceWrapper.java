package com.ism.author.ws.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ism.author.Utility.Debug;
import com.ism.author.Utility.PreferenceData;
import com.ism.author.Utility.Utility;
import com.ism.author.constant.WebConstants;
import com.ism.commonsource.utility.AESHelper;

import java.net.HttpURLConnection;
import java.net.URL;

import realmhelper.StudentHelper;


/**
 * Created by c166 on 23/10/15.
 */
public class WebserviceWrapper {

    private static final String TAG = WebserviceWrapper.class.getSimpleName();

    private Attribute attribute;
    private WebserviceResponse webserviceResponse;
    private Context mContext;

    private boolean isSecurityEnabled = true;
    private boolean isRefreshToken;

    public interface WebserviceResponse {
        public void onResponse(int apiCode, Object object, Exception error);
    }

    public WebserviceWrapper(Context context, Attribute attribute, WebserviceResponse listener) {
        this.attribute = attribute;
        this.webserviceResponse = listener;
        this.mContext = context;

    }

    public class WebserviceCaller extends AsyncTask<Integer, Void, Object> {

        public int currentApiCode;
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

////            Check if we can get access from the network.
////	            URL_HOST_202 url = new URL_HOST_202("http://192.168.1.147/");
//                URL_HOST_202 url = new URL_HOST_202("http://google.com/");
                URL url = new URL(WebConstants.URL_KINJAL_HOST);
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(2000); // Timeout 2 seconds.
                urlc.connect();
                isNetworkConnected = urlc.getResponseCode() == 200; //Successful response.

                if (isNetworkConnected) {

                    if (isSecurityEnabled && attribute.getSecretKey() == null && currentApiCode != WebConstants.REFRESH_TOKEN) {
                        isRefreshToken = true;
                        String globalPassword = new StudentHelper(mContext).getGlobalPassword();
                        if (globalPassword != null) {
                            WebConstants.ACCESS_KEY = AESHelper.encrypt(globalPassword, PreferenceData.getStringPrefs(PreferenceData.USER_NAME, mContext));
                            responseObject = new WebserviceConnector(WebConstants.URL_REFRESHTOKEN).execute(ResponseHandler.class, new Attribute(WebConstants.ACCESS_KEY));
                        }
                    } else {
                        switch (currentApiCode) {
                            case WebConstants.LOGIN:
                                responseObject = new WebserviceConnector(WebConstants.URL_LOGIN).execute(ResponseHandler.class, attribute);
                                break;

                            case WebConstants.FORGOTPASSWORD:
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

                            case WebConstants.GET_HIGH_SCORERS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_HIGH_SCORERS).execute(ResponseHandler.class, attribute);
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

                            case WebConstants.GETALLCLASSROOMS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETALLCLASSROOMS).execute(ResponseHandler.class, attribute);
                                break;

                            case WebConstants.GETALLSUBJECT:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETALLSUBJECT).execute(ResponseHandler.class, attribute);
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

                            case WebConstants.GETALLCOURSES:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETALLCOURSES).execute(ResponseHandler.class, attribute);
                                break;

                            case WebConstants.CREATEEXAM:
                                responseObject = new WebserviceConnector(WebConstants.URL_CREATEEXAM).execute(ResponseHandler.class, attribute);
                                break;

                            case WebConstants.GETQUESTIONBANK:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETQUESTIONBANK).execute(ResponseHandler.class, attribute);
                                break;

                            case WebConstants.GETALLASSIGNMENTS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETALLASSIGNMENTS).execute(ResponseHandler.class, attribute);
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

                            case WebConstants.GETALLEXAMS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETALLEXAMS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ALL_BADGES_COUNT:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ALL_BADGES_COUNT).execute(ResponseHandler.class, attribute);
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
                            case WebConstants.RESPOND_TO_REQUEST:
                                responseObject = new WebserviceConnector(WebConstants.URL_RESPOND_TO_REQUEST).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.UPDATE_READ_STATUS:
                                responseObject = new WebserviceConnector(WebConstants.URL_UPDATE_READ_STATUS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_MY_FEEDS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_MY_FEEDS).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.CREATEQUESTION:
                                responseObject = new WebserviceConnector(WebConstants.URL_CREATEQUESTION).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GETALLHASHTAG:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETALLHASHTAG).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.SETHASHTAG:
                                responseObject = new WebserviceConnector(WebConstants.URL_HASHTAG).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.TEMPCREATEQUESTION:
                                responseObject = new WebserviceConnector(WebConstants.URL_TEMPCREATEQUESTION).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_BOOKS_FOR_USER:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_BOOKS_FOR_USER).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_ABOUT_ME:
                                responseObject = new WebserviceConnector(WebConstants.URL_GET_ABOUT_ME).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.MANAGE_FAVOURITES:
                                responseObject = new WebserviceConnector(WebConstants.URL_MANAGE_FAVOURITES).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.MANAGE_BOOK_LIBRARY:
                                responseObject = new WebserviceConnector(WebConstants.URL_MANAGE_BOOK_LIBRARY).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GETBOOKSFORAUTHOR:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETBOOKSFORAUTHOR).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.UPLOADMEDIAFORQUESTION:
                                responseObject = new WebserviceConnector(WebConstants.URL_UPLOADMEDIAFORQUESTION).uploadMedia(ResponseHandler.class,
                                        attribute);
                                break;
                            case WebConstants.UPLOAD_FEED_MEDIA:
                                responseObject = new WebserviceConnector(WebConstants.URL_UPLOAD_FEED_MEDIA).uploadMedia(ResponseHandler.class,
                                        attribute);
                                break;
                            case WebConstants.UPLOADPROFILEIMAGES:
                                responseObject = new WebserviceConnector(WebConstants.URL_UPLOADPROFILEIMAGES).uploadMedia(ResponseHandler.class,
                                        attribute);
                                break;
                            case WebConstants.REFRESH_TOKEN:
                                responseObject = new WebserviceConnector(WebConstants.URL_REFRESHTOKEN).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GETADMINCONFIG:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETADMINCONFIG).execute(ResponseHandler.class, attribute);
                                break;
                            case WebConstants.GET_TRENDING_QUESTIONS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETTRENDINGQUESTIONS).execute(ResponseHandler.class,
                                        attribute);
                                break;
                            case WebConstants.GET_PAST_QUESTIONS:
                                responseObject = new WebserviceConnector(WebConstants.URL_GETPASTQUESTIONS).execute(ResponseHandler.class,
                                        attribute);
                                break;
                            case WebConstants.SUBMIT_TRENDING_ANSWER:
                                responseObject = new WebserviceConnector(WebConstants.URL_SUBMIT_TRENDING_ANSWER).execute(ResponseHandler.class,
                                        attribute);
                                break;

                            case WebConstants.UPLOADSUBQUESTIONIMAGES:
                                responseObject = new WebserviceConnector(WebConstants.URL_UPLOADSUBQUESTIONIMAGES).uploadMedia(ResponseHandler.class, attribute);
                                break;
                        }
                    }
                }
            } catch (Exception e) {
                Debug.i(TAG, "WebserviceCaller Background Exception : " + e.toString());
                exception = e;
            }
            return responseObject;
        }

        @Override
        protected void onPostExecute(Object responseObject) {

            if (!isNetworkConnected) {
                Utility.alertServerNotConnected(mContext);
            }

            if (isSecurityEnabled && isRefreshToken) {
                isRefreshToken = false;
                try {
                    if (responseObject != null) {
                        ResponseHandler responseHandler = (ResponseHandler) responseObject;
                        if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                            PreferenceData.setStringPrefs(PreferenceData.SECRET_KEY, mContext, responseHandler.getToken().get(0).getTokenName());
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
                webserviceResponse.onResponse(currentApiCode, responseObject, exception);
            }

            super.onPostExecute(responseObject);

        }
    }


}
