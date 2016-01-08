package com.socketdemo.c85.ismsocket;

import android.os.Handler;
import android.util.Log;



import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.EventCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import utils.SocketConstants;

/**
 * Created by c85 on 07/12/15.
 * Helper class to connect ,disconnect ,send events,handle events for socket.
 */
public class IOSocketHandler implements EventCallback{

    static SocketIOClient socketIOClient;

    private String socketUserId;

    public static SocketIOClient getSocketIOClient() {
        return socketIOClient;
    }

    public static void setSocketIOClient(SocketIOClient socketIOClient) {
        IOSocketHandler.socketIOClient = socketIOClient;
    }

    /**
     * connect to socket.
     */
    public void ConnectSocket(final String userId){
socketUserId = userId;
        SocketIOClient.connect( SocketConstants.SOCKET_URL, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, SocketIOClient client) {
                socketIOClient = client;


                if(ex == null){

                    JSONObject joinSocket = new JSONObject();
                    try {
                        joinSocket.put(SocketConstants.USER_ID, userId);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    socketIOClient.addListener(SocketConstants.JOIN_SOCKET,IOSocketHandler.this);
                    socketIOClient.emit(SocketConstants.JOIN_SOCKET, joinSocket);
                }
            }


        },new Handler());

    }

    /**
     * disconnect from socket
     */
    public static void disconnectSocket(){

        try {
            if(socketIOClient.isConnected()){
                socketIOClient.disconnect();
                socketIOClient = null;
                System.gc();
            }
        }
        catch (Exception error){

        }
    }

//    /**
//     * handle different event for socket.
//     */
//    private static void handleEvents(){
//
//        //socketIOClient.emit(SocketConstants.JOIN_SOCKET);
//        socketIOClient.on(SocketConstants.JOIN_SOCKET, new EventCallback() {
//            @Override
//            public void onEvent(JSONArray argument, Acknowledge acknowledge) {
//                System.out.println("args: " + argument.toString());
//
//                ArrayList<Integer> groupMembers = new ArrayList<Integer>();
//                groupMembers.add(425);
//                groupMembers.add(513);
//                groupMembers.add(524);
//                groupMembers.add(557);
//                groupMembers.add(573);
//
//                try {
//
//                    JSONObject jsonObject = argument.getJSONObject(0);
//                    int userId = jsonObject.getInt(SocketConstants.USER_ID);
//                    groupMembers.remove(userId);
//
//                    establishConnection(groupMembers);
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });
//    }


    private  void establishConnection(JSONArray groupMembers){

        JSONObject joinGroup = new JSONObject();

        try {
            joinGroup.put("friends", groupMembers);
            joinGroup.put(SocketConstants.USER_ID,socketUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socketIOClient.emit(SocketConstants.JOIN_GROUP, joinGroup);
    }


    @Override
    public void onEvent(String event, JSONArray argument, Acknowledge acknowledge) {

        switch (event){
            case SocketConstants.JOIN_SOCKET:
            {
                JSONArray jsonArray = new JSONArray();
                jsonArray.put("425");
                jsonArray.put("513");
                jsonArray.put("524");
                jsonArray.put("557");
                jsonArray.put("573");


                try {

                    JSONObject jsonObject = argument.getJSONObject(0);
                    String userId = jsonObject.getString(SocketConstants.USER_ID);
                   for (int i = 0;i<jsonArray.length();i++){
                       if(jsonArray.get(i).equals(userId)){
                           jsonArray.remove(i);
                           break;
                       }
                   }

                    establishConnection(jsonArray);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

        }
    }
}
