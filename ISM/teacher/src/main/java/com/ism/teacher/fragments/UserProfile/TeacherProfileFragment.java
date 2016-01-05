package com.ism.teacher.fragments.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.PreferenceData;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.userprofile.MessageAdapter;
import com.ism.teacher.adapters.userprofile.NotificationAdapter;
import com.ism.teacher.adapters.userprofile.StudymateRequestAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.object.Global;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Message;
import com.ism.teacher.ws.model.Notification;
import com.ism.teacher.ws.model.StudymateRequest;

import java.util.ArrayList;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherProfileFragment extends Fragment implements TeacherHostActivity.HostListenerProfileController, WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TeacherProfileFragment.class.getSimpleName();

    //Views
    TextView tvUserName, tvViewProfile, tvNotification, tvMessageNo, tvstudymatesRequest,
            tvGeneralSetting, tvMyfeeds, tvMystudents, tvMyactivity;
    CircleImageView imgUserDp;
    ImageView imgNotification, imgMessage, imgFriendRequest;
    RelativeLayout rlViewProfile;
    private ImageView[] arrImgNotificationIcon;

    //Section 2

    private ListView lvNotifications;
    private Button btnViewAll;
    private TextView txtEmpty;
    private ListView lvMessages, lvStudymates;
    private ArrayList<Message> arrListMessage;
    private PopupWindow popupFriendRequest;


    private FragmentListener fragListener;
    TeacherHostActivity activityHost;

    //ArrayList
    private ArrayList<Notification> arrListNotification;
    private ArrayList<StudymateRequest> arrListStudyMateRequest;


    //Adapters
    private NotificationAdapter adpNotification;
    private MessageAdapter adpMessage;
    private StudymateRequestAdapter adpStudymate;

    public static TeacherProfileFragment newInstance() {
        TeacherProfileFragment teacherProfileFragment = new TeacherProfileFragment();
        return teacherProfileFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (TeacherHostActivity) activity;
            fragListener = (FragmentListener) activity;
            activityHost.setListenerHostProfileController(this);
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_PROFILE_CONTROLLER);
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
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_PROFILE_CONTROLLER);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

    public TeacherProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_teacher_profile, container, false);

        initGlobal(rootview);
        Log.e(TAG, "called");
        return rootview;

    }

    private void initGlobal(View rootview) {
        rlViewProfile = (RelativeLayout) rootview.findViewById(R.id.rl_view_profile);

        tvUserName = (TextView) rootview.findViewById(R.id.tv_user_name);
        tvViewProfile = (TextView) rootview.findViewById(R.id.tv_view_profile);
        tvNotification = (TextView) rootview.findViewById(R.id.tv_notification);
        tvMessageNo = (TextView) rootview.findViewById(R.id.tv_message);
        tvstudymatesRequest = (TextView) rootview.findViewById(R.id.tv_request);
        tvGeneralSetting = (TextView) rootview.findViewById(R.id.tv_general_setting);
        tvMyfeeds = (TextView) rootview.findViewById(R.id.tv_myfeeds);
        tvMystudents = (TextView) rootview.findViewById(R.id.tv_mystudents);
        tvMyactivity = (TextView) rootview.findViewById(R.id.tv_myactivity);

        imgUserDp = (CircleImageView) rootview.findViewById(R.id.img_user_dp);

        imgNotification = (ImageView) rootview.findViewById(R.id.img_notification);
        imgMessage = (ImageView) rootview.findViewById(R.id.img_message);
        imgFriendRequest = (ImageView) rootview.findViewById(R.id.img_friend_request);

        arrImgNotificationIcon = new ImageView[]{imgNotification, imgMessage, imgFriendRequest};

        applyFonts();

        Global.imageLoader.displayImage(Global.strProfilePic, imgUserDp, ISMTeacher.options);
        tvUserName.setText(Global.strFullName);


        /**
         * Load frags of settings,myfeeds in main container
         */
        View.OnClickListener onClickLabel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.tv_general_setting:
                        activityHost.loadFragmentInMainContainer(TeacherHostActivity.FRAGMENT_GENERAL_SETTING);
                        break;

                    case R.id.tv_myfeeds:
                        activityHost.loadFragmentInMainContainer(TeacherHostActivity.FRAGMENT_MY_FEEDS);
                        break;
                    case R.id.tv_mystudents:
                        activityHost.loadFragmentInMainContainer(TeacherHostActivity.FRAGMENT_MY_STUDENTS);
                        break;
                    case R.id.tv_myactivity:
                        activityHost.loadFragmentInMainContainer(TeacherHostActivity.FRAGMENT_MY_ACTIVITY);
                        break;
                    case R.id.rl_view_profile:
                        break;
                }
            }
        };

        View.OnClickListener onClickNotificationItems = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.img_notification:
                        showNotification();
                        break;
                    case R.id.img_message:
                        showMessages();
                        break;
                    case R.id.img_friend_request:
                        showFriendRequests();
                        break;
                }
                highlightNotificationIcon(v.getId());
            }
        };


        tvGeneralSetting.setOnClickListener(onClickLabel);
        tvMyfeeds.setOnClickListener(onClickLabel);
        tvMystudents.setOnClickListener(onClickLabel);
        tvMyactivity.setOnClickListener(onClickLabel);
        rlViewProfile.setOnClickListener(onClickLabel);

        imgNotification.setOnClickListener(onClickNotificationItems);
        imgMessage.setOnClickListener(onClickNotificationItems);
        imgFriendRequest.setOnClickListener(onClickNotificationItems);

        showBadges();

    }

    private void showFriendRequests() {
        tvstudymatesRequest.setVisibility(View.GONE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_studymates, null);
        lvStudymates = (ListView) view.findViewById(R.id.lv_studymates);
        btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
        txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        btnViewAll.setTypeface(Global.myTypeFace.getRalewayRegular());
        lvStudymates.setEmptyView(txtEmpty);
        txtEmpty.setText(R.string.no_request_available);
        callApiGetStudymateRequests();

        popupFriendRequest = new PopupWindow(view, 250, 350, true);
        popupFriendRequest.setOutsideTouchable(true);
        popupFriendRequest.setBackgroundDrawable(new BitmapDrawable());

        popupFriendRequest.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        popupFriendRequest.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                imgFriendRequest.setActivated(false);
            }
        });


        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupFriendRequest.dismiss();
                loadFragmentAllStudymateRequest(-1);
            }
        });

        popupFriendRequest.showAtLocation(imgFriendRequest, Gravity.END, 10, 90);
    }

    private void loadFragmentAllStudymateRequest(int position) {
//        Bundle bundleAllStudymateRequest = new Bundle();
//        bundleAllStudymateRequest.putParcelableArrayList(AllStudymateRequestFragment.ARG_ARR_LIST_STUDYMATE_REQUEST, arrListStudyMateRequest);

        activityHost.getBundle().putParcelableArrayList(AllStudymateRequestFragment.ARG_ARR_LIST_STUDYMATE_REQUEST, arrListStudyMateRequest);
        activityHost.loadFragmentInMainContainer(TeacherHostActivity.FRAGMENT_ALL_STUDYMATE_REQUEST);
    }

    public void onclickStudymateRequestView(int position) {
        if (popupFriendRequest != null) {
            popupFriendRequest.dismiss();
            loadFragmentAllStudymateRequest(position);
        }
    }

    private void callApiGetStudymateRequests() {
        try {
            if (Utility.isConnected(activityHost)) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_STUDYMATE_REQUEST);
            } else {
                Utility.alertOffline(activityHost);
            }

        } catch (Exception e) {
            Debug.e(TAG, "callApiGetStudymateRequests Exception : " + e.toString());
        }
    }

    private void highlightNotificationIcon(int imgId) {
        for (int i = 0; i < arrImgNotificationIcon.length; i++) {
            arrImgNotificationIcon[i].setActivated(arrImgNotificationIcon[i].getId() == imgId);
        }
    }


    private void showNotification() {
        tvNotification.setVisibility(View.GONE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_notification, null);

        lvNotifications = (ListView) view.findViewById(R.id.lv_notification);
        btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
        txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        lvNotifications.setEmptyView(txtEmpty);
        txtEmpty.setText(R.string.no_notification_available);
        btnViewAll.setTypeface(Global.myTypeFace.getRalewayRegular());

        callApiGetNotifications();

        final PopupWindow popupNotification = new PopupWindow(view, 250, 350, true);
        popupNotification.setOutsideTouchable(true);
        popupNotification.setBackgroundDrawable(new BitmapDrawable());

        popupNotification.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        popupNotification.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                imgNotification.setActivated(false);
            }
        });

        lvNotifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupNotification.dismiss();
                loadFragmentAllNotification(position);
            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupNotification.dismiss();
                loadFragmentAllNotification(-1);
            }
        });

        popupNotification.showAtLocation(imgNotification, Gravity.END, 10, 90);
    }


    private void showMessages() {
        tvMessageNo.setVisibility(View.GONE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_message, null);

        lvMessages = (ListView) view.findViewById(R.id.lv_message);
        btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
        btnViewAll.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEmpty.setText(R.string.no_message_available);
        lvMessages.setEmptyView(txtEmpty);
        callApiGetMessages();

        final PopupWindow popupMessage = new PopupWindow(view, 250, 350, true);
        popupMessage.setOutsideTouchable(true);
        popupMessage.setBackgroundDrawable(new BitmapDrawable());

        popupMessage.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        popupMessage.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                imgMessage.setActivated(false);
            }
        });

        lvMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupMessage.dismiss();
                loadFragmentAllMessage(position);
            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMessage.dismiss();
                loadFragmentAllMessage(-1);
            }
        });

        popupMessage.showAtLocation(imgMessage, Gravity.END, 10, 90);
    }

    private void loadFragmentAllMessage(int position) {
//        Bundle bundleAllMessage = new Bundle();
//        bundleAllMessage.putParcelableArrayList(AllMessageFragment.ARG_ARR_LIST_MESSAGE, arrListMessage);
//        bundleAllMessage.putInt(AllMessageFragment.ARG_MESSAGE_POSITION, position);
        activityHost.getBundle().putParcelableArrayList(AllMessageFragment.ARG_ARR_LIST_MESSAGE, arrListMessage);
        activityHost.getBundle().putInt(AllMessageFragment.ARG_MESSAGE_POSITION, position);
        activityHost.loadFragmentInMainContainer(TeacherHostActivity.FRAGMENT_ALL_MESSAGE);
    }

    private void callApiGetMessages() {
        try {
            if (Utility.isConnected(activityHost)) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_MESSAGES);
            } else {
                Utility.alertOffline(activityHost);
            }
        } catch (Exception e) {
            Debug.e(TAG, "callApiGetMessages Exception : " + e.toString());
        }
    }


    private void loadFragmentAllNotification(int position) {
//        Bundle bundleAllNotification = new Bundle();
//        bundleAllNotification.putParcelableArrayList(AllNotificationFragment.ARG_ARR_LIST_NOTIFICATION, arrListNotification);
//        bundleAllNotification.putInt(AllNotificationFragment.ARG_NOTIFICATION_POSITION, position);

        activityHost.getBundle().putParcelableArrayList(AllNotificationFragment.ARG_ARR_LIST_NOTIFICATION, arrListNotification);
        activityHost.getBundle().putInt(AllNotificationFragment.ARG_NOTIFICATION_POSITION, position);
        activityHost.loadFragmentInMainContainer(TeacherHostActivity.FRAGMENT_ALL_NOTIFICATION);
    }


    private void applyFonts() {
        tvUserName.setTypeface(Global.myTypeFace.getRalewayBold());
        tvViewProfile.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNotification.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvMessageNo.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvstudymatesRequest.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvGeneralSetting.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvMyfeeds.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvMystudents.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvMyactivity.setTypeface(Global.myTypeFace.getRalewayRegular());
    }

    private void callApiGetNotifications() {
        try {
            if (Utility.isConnected(activityHost)) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_NOTIFICATION);
            } else {
                Utility.alertOffline(activityHost);
            }

        } catch (Exception e) {
            Debug.e(TAG, "callApiGetNotifications Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GET_NOTIFICATION:
                    onResponseGetNotification(object, error);
                    break;
                case WebConstants.GET_MESSAGES:
                    onResponseGetMessages(object, error);
                    break;
                case WebConstants.GET_STUDYMATE_REQUEST:
                    onResponseGetStudymateRequest(object, error);
                    break;
                case WebConstants.UPDATE_READ_STATUS:
                    onResponseUpdateReadStatus(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }

    }

    private void onResponseGetNotification(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Debug.e(TAG, "onResponseGetNotification success");
                    arrListNotification = responseHandler.getNotification();
                    fillListNotification();
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseGetNotification Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetNotification api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetNotification Exception : " + e.toString());
        }
    }

    private void fillListNotification() {
        if (arrListNotification != null) {
            adpNotification = new NotificationAdapter(getActivity(), arrListNotification, 4);
            lvNotifications.setAdapter(adpNotification);
            ArrayList<String> recordIds = new ArrayList<String>();
            for (int i = 0; i < (arrListNotification.size() >= 4 ? 4 : arrListNotification.size()); i++) {
                recordIds.add(arrListNotification.get(i).getRecordId());
            }

            callApiUpdateReadStatus(WebConstants.NOTIFICATION, recordIds);
        }
        if (arrListNotification.size() == 0)
            btnViewAll.setVisibility(View.GONE);
        else
            btnViewAll.setVisibility(View.VISIBLE);
    }

    private void callApiUpdateReadStatus(String readCategory, ArrayList<String> recordId) {
        try {
            if (Utility.isConnected(activityHost)) {
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                attribute.setReadCategory(readCategory);
                attribute.setRecordIds(recordId);

                new WebserviceWrapper(activityHost, attribute, this).new WebserviceCaller().
                        execute(WebConstants.UPDATE_READ_STATUS);
            } else {
                Utility.alertOffline(activityHost);
            }
        } catch (Exception e) {
            Debug.e(TAG, "callApiUpdateReadStatus Exception : " + e.toString());
        }
    }

    private void onResponseUpdateReadStatus(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Debug.e(TAG, "onResponseUpdateReadStatus success");
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseUpdateReadStatus failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseUpdateReadStatus api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseUpdateReadStatus Exception : " + e.toString());
        }
    }

    private void onResponseGetMessages(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Debug.e(TAG, "onResponseGetMessages success");
                    arrListMessage = responseHandler.getMessages();
                    fillListMessage();
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseGetMessages Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetMessages api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetMessages Exception : " + e.toString());
        }
    }

    private void fillListMessage() {
        if (arrListMessage != null) {
            adpMessage = new MessageAdapter(getActivity(), arrListMessage, 4);
            lvMessages.setAdapter(adpMessage);
            ArrayList<String> recordIds = new ArrayList<String>();
            for (int i = 0; i < (arrListMessage.size() >= 4 ? 4 : arrListMessage.size()); i++) {
                recordIds.add(arrListMessage.get(i).getRecordId());
            }

            callApiUpdateReadStatus(WebConstants.MESSAGES, recordIds);
        }
        if (arrListMessage.size() == 0)
            btnViewAll.setVisibility(View.GONE);
        else
            btnViewAll.setVisibility(View.VISIBLE);
    }

    private void onResponseGetStudymateRequest(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Debug.e(TAG, "onResponseGetStudymateRequest success");
                    arrListStudyMateRequest = responseHandler.getStudymateRequest();
                    fillListStudymate();
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseGetStudymateRequest Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetStudymateRequest api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetStudymateRequest Exception : " + e.toString());
        }
    }

    private void fillListStudymate() {
        if (arrListStudyMateRequest != null) {
            adpStudymate = new StudymateRequestAdapter(getActivity(), arrListStudyMateRequest, 4, this);
            lvStudymates.setAdapter(adpStudymate);
            ArrayList<String> recordIds = new ArrayList<String>();
            for (int i = 0; i < (arrListStudyMateRequest.size() >= 4 ? 4 : arrListStudyMateRequest.size()); i++) {
                recordIds.add(arrListStudyMateRequest.get(i).getRecordId());
            }
            callApiUpdateReadStatus(WebConstants.STUDYMATE_REQUEST, recordIds);
        }
        if (arrListStudyMateRequest.size() == 0)
            btnViewAll.setVisibility(View.GONE);
        else
            btnViewAll.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBadgesFetched() {

        showBadges();
    }

    @Override
    public void onSubFragmentAttached(int fragmentId) {
        highlightLabel(fragmentId, true);
    }

    @Override
    public void onSubFragmentDetached(int fragmentId) {
        highlightLabel(fragmentId, false);
    }

    private void highlightLabel(int fragmentId, boolean attached) {
        int textColor = attached ? getActivity().getResources().getColor(R.color.color_blue) : Color.WHITE;
        switch (fragmentId) {
            case TeacherHostActivity.FRAGMENT_GENERAL_SETTING:
                tvGeneralSetting.setTextColor(textColor);
                tvGeneralSetting.setEnabled(!attached);
                break;

            case TeacherHostActivity.FRAGMENT_MY_FEEDS:
                tvMyfeeds.setTextColor(textColor);
                tvMyfeeds.setEnabled(!attached);
                break;

            case TeacherHostActivity.FRAGMENT_MY_STUDENTS:
                tvMystudents.setTextColor(textColor);
                tvMystudents.setEnabled(!attached);
                break;

            case TeacherHostActivity.FRAGMENT_MY_ACTIVITY:
                tvMyactivity.setTextColor(textColor);
                tvMyactivity.setEnabled(!attached);
                break;


        }
    }

    private void showBadges() {
        int count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_NOTIFICATION, getActivity());
        tvNotification.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        tvNotification.setText("" + count);

        count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_MESSAGE, getActivity());
        tvMessageNo.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        tvMessageNo.setText("" + count);

        count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_REQUEST, getActivity());
        tvstudymatesRequest.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        tvstudymatesRequest.setText("" + count);
    }

}