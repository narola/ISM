package com.socketdemo.c85.ismsocket;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.EventCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.SocketConstants;

/**
 * Created by c85 on 07/12/15.
 * Helper class to connect ,disconnect ,send events,handle events for socket.
 */
public class IOSocketHandler {

    static SocketIOClient socketIOClient;

    public static SocketIOClient getSocketIOClient() {
        return socketIOClient;
    }

    public static void setSocketIOClient(SocketIOClient socketIOClient) {
        IOSocketHandler.socketIOClient = socketIOClient;
    }

    /**
     * connect to socket.
     */
    public static void ConnectSocket() {

        SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), SocketConstants.SOCKET_URL, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, SocketIOClient client) {
                socketIOClient = client;
                handleEvents();

                if (ex == null) {

                    JSONObject joinSocket = new JSONObject();
                    try {
                        joinSocket.put("UserID", "3");
                        joinSocket.put("friends", new android.R.array());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    socketIOClient.emit(SocketConstants.JOIN_SOCKET, joinSocket);
                }
            }


        });

    }

    /**
     * disconnect from socket
     */
    public static void disconnectSocket() {

        try {
            if (socketIOClient.isConnected()) {
                socketIOClient.disconnect();
                socketIOClient = null;
                System.gc();
            }
        } catch (Exception error) {

        }
    }

    /**
     * handle different event for socket.
     */
    private static void handleEvents() {

        socketIOClient.on(SocketConstants.JOIN_SOCKET, new EventCallback() {
            @Override
            public void onEvent(JSONArray argument, Acknowledge acknowledge) {
                System.out.println("args: " + argument.toString());
            }
        });
    }


}
