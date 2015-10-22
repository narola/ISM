package com.ism.author.interfaces;

import com.ism.author.asynctask.API_METHOD_NAME;

/**
 * Created by c166 on 22/10/15.
 */
public interface OnApiResponseListener {

    public void onResponseReceived(API_METHOD_NAME api_method_name, String response);


}
