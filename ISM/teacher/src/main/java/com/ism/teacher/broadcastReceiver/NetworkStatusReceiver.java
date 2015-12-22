package com.ism.teacher.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



/**
 * Created by c161 on 10/12/15.
 */
public class NetworkStatusReceiver extends BroadcastReceiver {

    public static NetworkStateListener listenerNetworkState;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listenerNetworkState != null) {
            listenerNetworkState.onNetworkStateChange();
        }
    }

    public static void setNetworkStateListener(NetworkStateListener networkStateListener) {
        listenerNetworkState = networkStateListener;
    }

}
