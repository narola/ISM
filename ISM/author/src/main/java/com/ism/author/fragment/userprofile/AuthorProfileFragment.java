package com.ism.author.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
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
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.PreferenceData;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.MessageAdapter;
import com.ism.author.adapter.NotificationAdapter;
import com.ism.author.adapter.StudymateRequestAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Message;
import com.ism.author.ws.model.Notification;
import com.ism.author.ws.model.StudymateRequest;

import java.util.ArrayList;


/**
 * Created by c162 on 21/10/15.
 */
public class AuthorProfileFragment extends Fragment implements AuthorHostActivity.HostListenerProfileController, WebserviceWrapper.WebserviceResponse {


    private static final String TAG = AuthorProfileFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private CircleImageView imgUser;
    private TextView txtViewProfile;
    private TextView txtUserName;
    private AuthorHostActivity activityHost;
    private TextView txtMessageNo, txtNotification, txtstudymatesRequest;
    private TextView txtMyFeeds, txtMyActivity, txtMyBooks, txtFollowers;
    private ListView lvNotifications;
    private Button btnViewAll;
    private ImageView imgNotification;
    private ImageView[] arrImgNotificationIcon;
    private ImageView imgMessage;
    private ImageView imgStudymatesRequest;
    private ListView lvMessages;
    private ListView lvStudymates;
    private ArrayList<StudymateRequest> arrListStudyMateRequest;
    private StudymateRequestAdapter adpStudymate;
    private ArrayList<Notification> arrListNotification;
    private NotificationAdapter adpNotification;
    private ArrayList<Message> arrListMessage;
    private MessageAdapter adpMessage;
    private TextView txtEmpty;
    private PopupWindow popupFriendRequest;

    public static AuthorProfileFragment newInstance() {
        AuthorProfileFragment fragAuthorProfile = new AuthorProfileFragment();
        return fragAuthorProfile;
    }

    public AuthorProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_author_profile, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        imgUser = (CircleImageView) view.findViewById(R.id.img_user);
        txtViewProfile = (TextView) view.findViewById(R.id.txt_view_profile);
        txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
        txtNotification = (TextView) view.findViewById(R.id.txt_notification);
        txtMessageNo = (TextView) view.findViewById(R.id.txt_message);
        txtstudymatesRequest = (TextView) view.findViewById(R.id.txt_request);
        txtMyActivity = (TextView) view.findViewById(R.id.txt_myactivity);
        txtMyBooks = (TextView) view.findViewById(R.id.txt_mybooks);
        txtFollowers = (TextView) view.findViewById(R.id.txt_followers);
        txtMyFeeds = (TextView) view.findViewById(R.id.txt_myfeeds);
        imgNotification = (ImageView) view.findViewById(R.id.img_notification);
        imgMessage = (ImageView) view.findViewById(R.id.img_message);
        imgStudymatesRequest = (ImageView) view.findViewById(R.id.img_friend_request);

        txtUserName.setTypeface(Global.myTypeFace.getRalewayBold());
        txtstudymatesRequest.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtNotification.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtMessageNo.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtViewProfile.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtMyBooks.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtMyFeeds.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtMyActivity.setTypeface(Global.myTypeFace.getRalewayRegular());

        arrImgNotificationIcon = new ImageView[]{imgNotification, imgMessage, imgStudymatesRequest};


        Global.imageLoader.displayImage(Global.strProfilePic, imgUser, ISMAuthor.options);// for change place holder of image =>use Utility getDisplayImageOption method
        txtUserName.setText(Global.strFullName);
        View.OnClickListener onClickLabel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.txt_myfeeds:
                        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MY_FEEDS, null);
                        break;
                    case R.id.txt_mybooks:
                        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MY_BOOKS, null);
                        break;
                    case R.id.txt_followers:
                        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_FOLLOWERS, null);
                        break;
                    case R.id.txt_view_profile:
                        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_VIEW_PROFILE, null);
                        break;
                    case R.id.txt_myactivity:
                        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MY_ACTIVITY, null);
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


        txtFollowers.setOnClickListener(onClickLabel);
        txtMyBooks.setOnClickListener(onClickLabel);
        txtMyFeeds.setOnClickListener(onClickLabel);
        txtMyActivity.setOnClickListener(onClickLabel);
        imgNotification.setOnClickListener(onClickNotificationItems);
        imgMessage.setOnClickListener(onClickNotificationItems);
        imgStudymatesRequest.setOnClickListener(onClickNotificationItems);
        showBadges();


    }

    private void highlightNotificationIcon(int imgId) {
        for (int i = 0; i < arrImgNotificationIcon.length; i++) {
            arrImgNotificationIcon[i].setActivated(arrImgNotificationIcon[i].getId() == imgId);
        }
    }

    private void showBadges() {
        int count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_NOTIFICATION, getActivity());
        txtNotification.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        txtNotification.setText("" + count);

        count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_MESSAGE, getActivity());
        txtMessageNo.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        txtMessageNo.setText("" + count);

        count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_REQUEST, getActivity());
        txtstudymatesRequest.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        txtstudymatesRequest.setText("" + count);
    }

    private void showMessages() {
        txtMessageNo.setVisibility(View.GONE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_message, null);

        lvMessages = (ListView) view.findViewById(R.id.lv_message);
        btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
        btnViewAll.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEmpty=(TextView)view.findViewById(R.id.txt_empty);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());

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

        popupMessage.showAtLocation(imgMessage, Gravity.END, 10, 80);
    }

    private void loadFragmentAllMessage(int position) {
        Bundle bundleAllMessage = new Bundle();
        bundleAllMessage.putParcelableArrayList(AllMessageFragment.ARG_ARR_LIST_MESSAGE, arrListMessage);
        bundleAllMessage.putInt(AllMessageFragment.ARG_MESSAGE_POSITION, position);
        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ALL_MESSAGE, bundleAllMessage);
    }

    private void showNotification() {
        txtNotification.setVisibility(View.GONE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_notification, null);

        lvNotifications = (ListView) view.findViewById(R.id.lv_notification);
        btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
        txtEmpty=(TextView)view.findViewById(R.id.txt_empty);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());

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

        popupNotification.showAtLocation(imgNotification, Gravity.END, 10, 80);
    }

    private void loadFragmentAllNotification(int position) {
        Bundle bundleAllNotification = new Bundle();
        bundleAllNotification.putParcelableArrayList(AllNotificationFragment.ARG_ARR_LIST_NOTIFICATION, arrListNotification);
        bundleAllNotification.putInt(AllNotificationFragment.ARG_NOTIFICATION_POSITION, position);
        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ALL_NOTIFICATION, bundleAllNotification);
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
            Log.e(TAG, "callApiGetNotifications Exception : " + e.toString());
        }
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
            Log.e(TAG, "callApiGetMessages Exception : " + e.toString());
        }
    }

    private void showFriendRequests() {
        txtstudymatesRequest.setVisibility(View.GONE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_studymates, null);
        lvStudymates = (ListView) view.findViewById(R.id.lv_studymates);
        btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
        txtEmpty=(TextView)view.findViewById(R.id.txt_empty);
        txtEmpty.setTypeface(Global.myTypeFace.getRalewayRegular());
        btnViewAll.setTypeface(Global.myTypeFace.getRalewayRegular());

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
                imgStudymatesRequest.setActivated(false);
            }
        });


        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupFriendRequest.dismiss();
                loadFragmentAllStudymateRequest(-1);
            }
        });

        popupFriendRequest.showAtLocation(imgStudymatesRequest, Gravity.END, 10, 80);
    }

    private void loadFragmentAllStudymateRequest(int position) {
        Bundle bundleAllStudymateRequest = new Bundle();
        bundleAllStudymateRequest.putParcelableArrayList(AllStudymateRequestFragment.ARG_ARR_LIST_STUDYMATE_REQUEST, arrListStudyMateRequest);
        activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ALL_STUDYMATE_REQUEST, bundleAllStudymateRequest);
    }

    public void onclickStudymateRequestView(int position)
    {
        if(popupFriendRequest!=null) {
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
            Log.e(TAG, "callApiGetStudymateRequests Exception : " + e.toString());
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (AuthorHostActivity) activity;
            fragListener = (FragmentListener) activity;
            activityHost.setListenerHostProfileController(this);
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_PROFILE_CONTROLLER);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_PROFILE_CONTROLLER);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    private void highlightLabel(int fragmentId, boolean attached) {
        int textColor = attached ? getActivity().getResources().getColor(R.color.color_blue) : Color.WHITE;
        switch (fragmentId) {
            case AuthorHostActivity.FRAGMENT_MY_FEEDS:
                txtMyFeeds.setTextColor(textColor);
                txtMyFeeds.setEnabled(!attached);
                break;
            case AuthorHostActivity.FRAGMENT_MY_BOOKS:
                txtMyBooks.setTextColor(textColor);
                txtMyBooks.setEnabled(!attached);
                break;
            case AuthorHostActivity.FRAGMENT_MY_ACTIVITY:
                txtMyActivity.setTextColor(textColor);
                txtMyActivity.setEnabled(!attached);
                break;
            case AuthorHostActivity.FRAGMENT_FOLLOWERS:
                txtFollowers.setTextColor(textColor);
                txtFollowers.setEnabled(!attached);
                break;
            case AuthorHostActivity.FRAGMENT_VIEW_PROFILE:
                txtViewProfile.setText(attached ? Html.fromHtml("<u>" + activityHost.getString(R.string.strviewprofile) + "</u>") : activityHost.getString(R.string.strviewprofile));
                txtViewProfile.setEnabled(!attached);
                break;
        }
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
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseUpdateReadStatus(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					Log.e(TAG, "onResponseUpdateReadStatus success");
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseUpdateReadStatus failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseUpdateReadStatus api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseUpdateReadStatus Exception : " + e.toString());
        }
    }

    private void onResponseGetStudymateRequest(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Log.e(TAG, "onResponseGetStudymateRequest success");
                    arrListStudyMateRequest = responseHandler.getStudymateRequest();
                    fillListStudymate();
                    if(arrListStudyMateRequest.size()==0){
                        txtEmpty.setVisibility(View.VISIBLE);
                        btnViewAll.setVisibility(View.GONE);
                        lvStudymates.setVisibility(View.GONE);
                    }
                    else{
                        txtEmpty.setVisibility(View.GONE);
                        btnViewAll.setVisibility(View.VISIBLE);
                        lvStudymates.setVisibility(View.VISIBLE);
                    }
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseGetStudymateRequest Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetStudymateRequest api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetStudymateRequest Exception : " + e.toString());
        }
    }

    private void fillListStudymate() {
        if (arrListStudyMateRequest != null) {
            adpStudymate = new StudymateRequestAdapter(getActivity(), arrListStudyMateRequest, 4,this);
            lvStudymates.setAdapter(adpStudymate);
            ArrayList<String> recordIds = new ArrayList<String>();
            for (int i = 0; i < (arrListStudyMateRequest.size() >= 4 ? 4 : arrListStudyMateRequest.size()); i++) {
                recordIds.add(arrListStudyMateRequest.get(i).getRecordId());
            }
            callApiUpdateReadStatus(WebConstants.STUDYMATE_REQUEST, recordIds);
        }
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
            Log.e(TAG, "callApiUpdateReadStatus Exception : " + e.toString());
        }
    }

    private void onResponseGetNotification(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Log.e(TAG, "onResponseGetNotification success");
                    arrListNotification = responseHandler.getNotification();
                    fillListNotification();
                    if(arrListNotification.size()==0){
                        txtEmpty.setVisibility(View.VISIBLE);
                        btnViewAll.setVisibility(View.GONE);
                        lvNotifications.setVisibility(View.GONE);
                    }
                    else{
                        txtEmpty.setVisibility(View.GONE);
                        btnViewAll.setVisibility(View.VISIBLE);
                        lvNotifications.setVisibility(View.VISIBLE);
                    }
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseGetNotification Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetNotification api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetNotification Exception : " + e.toString());
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
    }

    private void onResponseGetMessages(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Log.e(TAG, "onResponseGetMessages success");
                    arrListMessage = responseHandler.getMessages();
                    fillListMessage();
                    if(arrListMessage.size()==0){
                        txtEmpty.setVisibility(View.VISIBLE);
                        btnViewAll.setVisibility(View.GONE);
                        lvMessages.setVisibility(View.GONE);
                    }
                    else{
                        txtEmpty.setVisibility(View.GONE);
                        btnViewAll.setVisibility(View.VISIBLE);
                        lvMessages.setVisibility(View.VISIBLE);

                    }
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseGetMessages Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetMessages api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetMessages Exception : " + e.toString());
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
    }

}

