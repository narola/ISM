package com.ism.author.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.ism.author.interfaces.OnApiResponseListener;

/**
 * Created by c166 on 22/10/15.
 */
public class GetAllFeeds extends
        AsyncTask<String, Void, String> {


    Context mContext;
    OnApiResponseListener onApiResponseListener;


    public GetAllFeeds(Context mContext,
                       OnApiResponseListener onApiResponseListener) {
        this.mContext = mContext;
        this.onApiResponseListener = onApiResponseListener;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {

        String data = "";
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        onApiResponseListener.onResponseReceived(API_METHOD_NAME.get_all_feeds, result);

    }


}
