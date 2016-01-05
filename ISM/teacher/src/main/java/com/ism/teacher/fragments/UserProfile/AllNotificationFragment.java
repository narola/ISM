package com.ism.teacher.fragments.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.userprofile.NotificationAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.object.Global;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Notification;

import java.util.ArrayList;

/**
 * Created by c162 on 27/11/15.
 */
public class AllNotificationFragment extends Fragment implements TeacherHostActivity.HostListenerAllNotification, WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AllNotificationFragment.class.getSimpleName();

    private View view;
    private ListView lvAllNotification;
    private RelativeLayout rlNotificationDetails;
    private CircleImageView imgDp;
    private ImageView imgLike, imgTagStudyMates;
    private TextView txtName, txtPost, txtLikes, txtComments, txtViewAll, txtHeader;
    private EditText etComment;
    private Button btnComment;
    private LinearLayout llComments;

    private FragmentListener fragListener;
    private TeacherHostActivity activityHost;
    private ArrayList<Notification> arrListNotification;
    private NotificationAdapter adpNotification;

    public static String ARG_ARR_LIST_NOTIFICATION = "arrListNotification";
    public static String ARG_NOTIFICATION_POSITION = "notificationPosition";
    private int positionNotification;
    private boolean isReadStatusUpdated = false;

    public static AllNotificationFragment newInstance() {
        AllNotificationFragment fragment = new AllNotificationFragment();
        return fragment;
    }

    public AllNotificationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            arrListNotification = getArguments().getParcelableArrayList(ARG_ARR_LIST_NOTIFICATION);
            positionNotification = getArguments().getInt(ARG_NOTIFICATION_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_notification, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        txtHeader = (TextView) view.findViewById(R.id.txt_header_white);
        lvAllNotification = (ListView) view.findViewById(R.id.lv_all_notification);

        txtHeader.setTypeface(Global.myTypeFace.getRalewayRegular());


        if (positionNotification >= 0) {
            showNotificationDetails(positionNotification);
        } else {
            if (Utility.isConnected(activityHost)) {
                callApiUpdateReadStatus();
            } else {
                Utility.alertOffline(activityHost);
            }
        }

        if (arrListNotification != null) {

            adpNotification = new NotificationAdapter(getActivity(), arrListNotification);
            if (adpNotification.getCount() > 0) {
                Debug.i(TAG, "arrListNotification size : " + adpNotification.getCount());
            } else {
                Debug.i(TAG, "arrListNotification size : " + adpNotification.getCount());
            }
            lvAllNotification.setAdapter(adpNotification);
        }

        lvAllNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                showNotificationDetails(position);
            }
        });

    }

    private void showNotificationList() {
        if (rlNotificationDetails != null) {
            rlNotificationDetails.setVisibility(View.GONE);
        }
        lvAllNotification.setVisibility(View.VISIBLE);
        txtHeader.setVisibility(View.VISIBLE);
        if (Utility.isConnected(activityHost)) {
            callApiUpdateReadStatus();
        } else {
            Utility.alertOffline(activityHost);
        }
    }

    private void showNotificationDetails(int position) {
        activityHost.showControllerTopBackButton();
        lvAllNotification.setVisibility(View.GONE);
        txtHeader.setVisibility(View.GONE);
        if (rlNotificationDetails == null) {
            rlNotificationDetails = (RelativeLayout) ((ViewStub) view.findViewById(R.id.vs_notification_details)).inflate();
            initViews();
        } else {
            rlNotificationDetails.setVisibility(View.VISIBLE);
        }

        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrListNotification.get(position).getNotificationFromProfilePic(), imgDp, ISMTeacher.options);
        txtName.setText(arrListNotification.get(position).getNotificationFromName());
        txtPost.setText(arrListNotification.get(position).getNotificationText());
//		txtLikes.setText(arrListNotification.get(position).getTotalLike());
//		txtComments.setText(arrListNotification.get(position).getTotalComment());
    }

    private void initViews() {
        imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
        imgLike = (ImageView) view.findViewById(R.id.img_like);
        imgTagStudyMates = (ImageView) view.findViewById(R.id.img_tag_study_mates);
        txtName = (TextView) view.findViewById(R.id.txt_name);
        txtPost = (TextView) view.findViewById(R.id.txt_post);
        txtLikes = (TextView) view.findViewById(R.id.txt_likes);
        txtComments = (TextView) view.findViewById(R.id.txt_comments);
        txtViewAll = (TextView) view.findViewById(R.id.txt_view_all);
        etComment = (EditText) view.findViewById(R.id.et_comment);
        btnComment = (Button) view.findViewById(R.id.btn_comment);
        llComments = (LinearLayout) view.findViewById(R.id.ll_comments);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            activityHost = (TeacherHostActivity) activity;
            activityHost.setListenerHostAllNotification(this);
            if (fragListener != null) {
                /**
                 * Remove Comment
                 */
              //  fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_ALL_NOTIFICATION);
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
                /**
                 * Remove Comment
                 */
              //  fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_ALL_NOTIFICATION);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private void callApiUpdateReadStatus() {
        try {
            if (!isReadStatusUpdated && arrListNotification != null && arrListNotification.size() > 0) {
                ArrayList<String> recordIds = new ArrayList<String>();
                for (int i = 0; i < arrListNotification.size(); i++) {
                    recordIds.add(arrListNotification.get(i).getRecordId());
                }
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setReadCategory(WebConstants.NOTIFICATION);
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
        showNotificationList();
    }


    private void onResponseUpdateReadStatus(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    isReadStatusUpdated = true;
                    Log.e(TAG, "Read status updated");
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
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