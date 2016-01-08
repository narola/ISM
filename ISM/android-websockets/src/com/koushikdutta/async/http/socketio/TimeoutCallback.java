package com.koushikdutta.async.http.socketio;

/**
 * Created by hitesh on 9/6/15.
 */
public interface TimeoutCallback {
     void onTimeOutException(String UniqueID,Exception e);
}
