package com.socketdemo.c85.ismsocket;

import android.os.Handler;
import android.util.Log;



import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.DisconnectCallback;
import com.koushikdutta.async.http.socketio.EventCallback;
import com.koushikdutta.async.http.socketio.ReconnectCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import isminterface.OnSocketResponse;
import model.TutorialGroupDiscussion;
import utils.SocketConstants;

/**
 * Created by c85 on 07/12/15.
 * Helper class to connect ,disconnect ,send events,handle events for socket.
 */
public class IOSocketHandler implements EventCallback,DisconnectCallback{

    private final String LOG_TAG = IOSocketHandler.class.getName();
    static SocketIOClient socketIOClient;

    private String socketUserId;

    private static OnSocketResponse onSocketResponse;

    private Handler handler ;


    public static SocketIOClient getSocketIOClient() {
        return socketIOClient;
    }

    public static void setSocketIOClient(SocketIOClient socketIOClient) {
        IOSocketHandler.socketIOClient = socketIOClient;
    }


    public static OnSocketResponse getOnSocketResponse() {
        return onSocketResponse;
    }

    public static void setOnSocketResponse(OnSocketResponse onSocketResponse) {
        IOSocketHandler.onSocketResponse = onSocketResponse;
    }

    public IOSocketHandler(OnSocketResponse onSocketResponse){
        this.onSocketResponse = onSocketResponse;
    }

    public IOSocketHandler(){

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

                    socketIOClient.setDisconnectCallback(IOSocketHandler.this);
                    socketIOClient.addListener(SocketConstants.JOIN_SOCKET,IOSocketHandler.this);
                    socketIOClient.addListener(SocketConstants.NEW_MESSAGE, IOSocketHandler.this);
                    socketIOClient.emit(SocketConstants.JOIN_SOCKET, joinSocket);

                    //
                    //socketIOClient.setReconnectCallback(IOSocketHandler.this);

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


    /**
     * send tutorial discussion message in group
     * @param tutorialGroupDiscussion
     */
    public void sendMessage(TutorialGroupDiscussion tutorialGroupDiscussion){

        JSONObject sendDiscussionMessage = new JSONObject();

        try {
            sendDiscussionMessage.put(SocketConstants.GROUP_ID, "135");
            sendDiscussionMessage.put(SocketConstants.SENDER_ID,tutorialGroupDiscussion.getSender().getUserId());
            sendDiscussionMessage.put(SocketConstants.TUTORIAL_TOPIC_ID,tutorialGroupDiscussion.getTopic().getTutorialTopicId());
            sendDiscussionMessage.put(SocketConstants.COMMENT_SCORE,"2");
            sendDiscussionMessage.put(SocketConstants.MESSAGE,tutorialGroupDiscussion.getMessage());
            sendDiscussionMessage.put(SocketConstants.IN_ACTIVE_HOURS,"1");
            sendDiscussionMessage.put(SocketConstants.FULL_NAME,tutorialGroupDiscussion.getSender().getFullName());
            sendDiscussionMessage.put(SocketConstants.PROFILE_PICTURE,tutorialGroupDiscussion.getSender().getProfilePicture());



        } catch (JSONException e) {
            e.printStackTrace();
        }
        socketIOClient.emit(SocketConstants.NEW_MESSAGE, sendDiscussionMessage, new Acknowledge() {
            @Override
            public void acknowledge(JSONArray arguments) {
                Log.e(LOG_TAG,""+arguments);

            }
        });
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

            case SocketConstants.NEW_MESSAGE:
            {

                if(argument != null){
                    try {
                        onSocketResponse.onNewMessage( argument.getJSONObject(0));

                    } catch (JSONException e) {
                        Log.e(LOG_TAG,  e.getLocalizedMessage());
                    }
                }
            }
                break;

        }
    }

    @Override
    public void onDisconnect(Exception e) {

        handler = new Handler();
        handler.postDelayed(runnableTimer,5000);

    }


    private Runnable runnableTimer = new Runnable() {
        public void run() {

            if(!socketIOClient.isConnected()) {
                ConnectSocket(socketUserId);
                handler.postDelayed(runnableTimer, 5000);
            }
            else{
                handler.removeCallbacks(runnableTimer);
                handler = null;
            }
        }
    };


}
