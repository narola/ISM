package com.ism.author.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.author.R;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.MessageAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.object.MyTypeFace;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Message;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c162 on 27/11/15.
 */
public class AllMessageFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, AuthorHostActivity.HostListenerAllMessage {

    private static final String TAG = AllMessageFragment.class.getSimpleName();

    private View view;
    private ListView lvAllMessage;
    private RelativeLayout rlMessageDetails;
    private CircleImageView imgDp;
    private TextView txtName, txtMessage, txtReply, txtHeader;

    private FragmentListener fragListener;
    private AuthorHostActivity activityHost;
    private ArrayList<Message> arrListMessage;
    private MessageAdapter adpMessage;
    private ImageLoader imageLoader;

    public static String ARG_ARR_LIST_MESSAGE = "arrListMessage";
    public static String ARG_MESSAGE_POSITION = "notificationPosition";
    private int positionMessage;
    private boolean isReadStatusUpdated = false;

    public static AllMessageFragment newInstance() {
        AllMessageFragment fragment = new AllMessageFragment();
        return fragment;
    }

    public AllMessageFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            arrListMessage = getArguments().getParcelableArrayList(ARG_ARR_LIST_MESSAGE);
            positionMessage = getArguments().getInt(ARG_MESSAGE_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_message, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        txtHeader = (TextView) view.findViewById(R.id.txt_header_white);
        lvAllMessage = (ListView) view.findViewById(R.id.lv_all_message);

        MyTypeFace myTypeFace = new MyTypeFace(activityHost);
        txtHeader.setTypeface(myTypeFace.getRalewayRegular());

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        if (positionMessage >= 0) {
            showMessageDetails(positionMessage);
        } else {
            if (Utility.isConnected(activityHost)) {
                callApiUpdateReadStatus();
            } else {
                Utility.alertOffline(activityHost);
            }
        }

        if (arrListMessage != null) {
            adpMessage = new MessageAdapter(getActivity(), arrListMessage);
            lvAllMessage.setAdapter(adpMessage);
        }

        lvAllMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                showMessageDetails(position);
            }
        });
    }

    private void showMessageList() {
        if (rlMessageDetails != null) {
            rlMessageDetails.setVisibility(View.GONE);
        }
        lvAllMessage.setVisibility(View.VISIBLE);
        txtHeader.setVisibility(View.VISIBLE);
        if (Utility.isConnected(activityHost)) {
            callApiUpdateReadStatus();
        } else {
            Utility.alertOffline(activityHost);
        }
    }

    private void showMessageDetails(int position) {
        activityHost.showControllerTopBackButton();
        lvAllMessage.setVisibility(View.GONE);
        txtHeader.setVisibility(View.GONE);
        if (rlMessageDetails == null) {
            rlMessageDetails = (RelativeLayout) ((ViewStub) view.findViewById(R.id.vs_message_details)).inflate();
            initViews();
        } else {
            rlMessageDetails.setVisibility(View.VISIBLE);
        }

//		imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", imgDp, ISMStudent.options);
        txtName.setText(arrListMessage.get(position).getSenderName());
        txtMessage.setText(arrListMessage.get(position).getMessageText());
    }

    private void initViews() {
        imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
        txtName = (TextView) view.findViewById(R.id.txt_name);
        txtMessage = (TextView) view.findViewById(R.id.txt_message);
        txtReply = (TextView) view.findViewById(R.id.txt_reply);

        txtReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Reply message.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            activityHost = (AuthorHostActivity) activity;
            activityHost.setListenerHostAllMessage(this);
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_ALL_MESSAGE);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_ALL_MESSAGE);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private void callApiUpdateReadStatus() {
        try {
            if (!isReadStatusUpdated && arrListMessage != null && arrListMessage.size() > 0) {
                ArrayList<String> recordIds = new ArrayList<String>();
                for (int i = 0; i < arrListMessage.size(); i++) {
                    recordIds.add(arrListMessage.get(i).getRecordId());
                }
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setReadCategory(WebConstants.MESSAGES);
                attribute.setRecordIds(recordIds);

                new WebserviceWrapper(activityHost, attribute, this).new WebserviceCaller().
                        execute(WebConstants.UPDATE_READ_STATUS);
            }
        } catch (Exception e) {
            Log.e(TAG, "callApiUpdateReadStatus Exception : " + e.toString());
        }
    }

    @Override
    public void onControllerTopBackClick() {
        showMessageList();
    }

    private void onResponseUpdateReadStatus(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    isReadStatusUpdated = true;
                    Log.e(TAG, "Read status updated");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "Read status update failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseUpdateReadStatus api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseUpdateReadStatus Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.UPDATE_READ_STATUS:
                    onResponseUpdateReadStatus(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }
}
