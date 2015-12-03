package com.ism.fragment.userprofile;

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

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.activity.HostActivity.HostListenerProfileController;
import com.ism.adapter.MessageAdapter;
import com.ism.adapter.NotificationAdapter;
import com.ism.adapter.StudymateRequestAdapter;
import com.ism.constant.WebConstants;
import com.ism.fragment.AllStudymateRequestFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.views.CircleImageView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Message;
import com.ism.ws.model.Notification;
import com.ism.ws.model.StudymateRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on --/10/15.
 */
public class ProfileControllerFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, HostListenerProfileController {

    private static final String TAG = ProfileControllerFragment.class.getSimpleName();

    private View view;

    private CircleImageView imgDp;
    private TextView txtUserName, txtNotificationNo, txtMessageNo, txtRequestNo,
            txtGeneralSettings, txtMyFeeds, txtStudyMates, txtMyActivity, txtWallet, txtEditProfile, txtEmptyListMessage;
    private ImageView imgNotification, imgMessage, imgFriendRequest;
    private ListView lvNotifications, lvMessages, lvStudymates;
    private Button btnViewAll;

    private TextView[] arrTxtLabel;
    private ImageView[] arrImgNotificationIcon;

    private HostActivity activityHost;
    private FragmentListener fragListener;
	private ImageLoader imageLoader;
	private ArrayList<Notification> arrListNotification;
	private ArrayList<Message> arrListMessage;
	private ArrayList<StudymateRequest> arrListStudyMateRequest;
	private NotificationAdapter adpNotification;
	private MessageAdapter adpMessage;
	private StudymateRequestAdapter adpStudymate;
	private PopupWindow popupFriendRequest;

    public static ProfileControllerFragment newInstance() {
        ProfileControllerFragment fragStudyMates = new ProfileControllerFragment();
        return fragStudyMates;
    }

    public ProfileControllerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    view = inflater.inflate(R.layout.fragment_profile_controller, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
        txtUserName = (TextView) view.findViewById(R.id.txt_name);
        txtEditProfile = (TextView) view.findViewById(R.id.txt_edit_profile);
        txtNotificationNo = (TextView) view.findViewById(R.id.txt_notification);
        txtMessageNo = (TextView) view.findViewById(R.id.txt_message);
        txtRequestNo = (TextView) view.findViewById(R.id.txt_friend_request);
        txtGeneralSettings = (TextView) view.findViewById(R.id.txt_general_settings);
        txtMyFeeds = (TextView) view.findViewById(R.id.txt_my_feeds);
        txtStudyMates = (TextView) view.findViewById(R.id.txt_studymates);
        txtMyActivity = (TextView) view.findViewById(R.id.txt_my_activity);
        txtWallet = (TextView) view.findViewById(R.id.txt_my_wallet);
        imgNotification = (ImageView) view.findViewById(R.id.img_notification);
        imgMessage = (ImageView) view.findViewById(R.id.img_message);
        imgFriendRequest = (ImageView) view.findViewById(R.id.img_friend_request);

	    highlightLabel(activityHost.getCurrentMainFragment(), true);

	    txtUserName.setText(Global.strFullName);

        arrTxtLabel = new TextView[]{txtGeneralSettings, txtMyFeeds, txtStudyMates, txtMyActivity, txtWallet};
        arrImgNotificationIcon = new ImageView[]{imgNotification, imgMessage, imgFriendRequest};

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        imageLoader.displayImage(Global.strProfilePic, imgDp, ISMStudent.options);

        showBadges();

        View.OnClickListener onClickLabel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.txt_general_settings:
                        activityHost.loadFragment(HostActivity.FRAGMENT_GENERAL_SETTINGS, null);
                        break;
                    case R.id.txt_my_feeds:
                        activityHost.loadFragment(HostActivity.FRAGMENT_MY_FEEDS, null);
                        break;
                    case R.id.txt_studymates:
                        activityHost.loadFragment(HostActivity.FRAGMENT_STUDYMATES, null);
                        break;
                    case R.id.txt_my_activity:
                        activityHost.loadFragment(HostActivity.FRAGMENT_MY_ACTIVITY, null);
                        break;
                    case R.id.txt_my_wallet:
                        activityHost.loadFragment(HostActivity.FRAGMENT_MY_WALLET, null);
                        break;
                    case R.id.txt_edit_profile:
                        activityHost.loadFragment(HostActivity.FRAGMENT_EDIT_PROFILE, null);
                        break;
                }
            }
        };

        View.OnClickListener onClickNotificationIcon = new View.OnClickListener() {
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
                        showStudymateRequests();
                        break;
                }
                highlightNotificationIcon(v.getId());
            }
        };

        txtGeneralSettings.setOnClickListener(onClickLabel);
        txtMyFeeds.setOnClickListener(onClickLabel);
        txtStudyMates.setOnClickListener(onClickLabel);
        txtMyActivity.setOnClickListener(onClickLabel);
        txtWallet.setOnClickListener(onClickLabel);
        txtEditProfile.setOnClickListener(onClickLabel);

        imgNotification.setOnClickListener(onClickNotificationIcon);
        imgMessage.setOnClickListener(onClickNotificationIcon);
        imgFriendRequest.setOnClickListener(onClickNotificationIcon);
    }

    public void showAllStudymateRequests(int position) {
	    if (popupFriendRequest != null) {
		    popupFriendRequest.dismiss();
		    loadFragmentAllStudymateRequest(position);
	    }
    }

    private void showBadges() {
        int count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_NOTIFICATION, getActivity());
        txtNotificationNo.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        txtNotificationNo.setText("" + count);

        count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_MESSAGE, getActivity());
        txtMessageNo.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        txtMessageNo.setText("" + count);

        count = PreferenceData.getIntPrefs(PreferenceData.BADGE_COUNT_REQUEST, getActivity());
        txtRequestNo.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        txtRequestNo.setText("" + count);
    }

    private void showNotification() {
        txtNotificationNo.setVisibility(View.GONE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_notification, null);

        lvNotifications = (ListView) view.findViewById(R.id.lv_notification);
	    txtEmptyListMessage = (TextView) view.findViewById(R.id.txt_emptylist_message);
        btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
        btnViewAll.setTypeface(Global.myTypeFace.getRalewayRegular());
	    txtEmptyListMessage.setTypeface(Global.myTypeFace.getRalewayRegular());

	    lvNotifications.setEmptyView(txtEmptyListMessage);

	    if (Utility.isConnected(activityHost)) {
		    callApiGetNotifications();
	    } else {
		    Utility.alertOffline(activityHost);
	    }

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

        popupNotification.showAtLocation(imgNotification, Gravity.END, 10, 60);
    }

	private void loadFragmentAllNotification(int position) {
		Bundle bundleAllNotification = new Bundle();
		bundleAllNotification.putParcelableArrayList(AllNotificationFragment.ARG_ARR_LIST_NOTIFICATION, arrListNotification);
		bundleAllNotification.putInt(AllNotificationFragment.ARG_NOTIFICATION_POSITION, position);
		activityHost.loadFragment(HostActivity.FRAGMENT_ALL_NOTIFICATION, bundleAllNotification);
	}

    private void callApiGetNotifications() {
        try {
            activityHost.showProgress();
            Attribute attribute = new Attribute();
            attribute.setUserId(Global.strUserId);

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_NOTIFICATION);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetNotifications Exception : " + e.toString());
        }
    }

    private void showMessages() {
        txtMessageNo.setVisibility(View.GONE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_message, null);

        lvMessages = (ListView) view.findViewById(R.id.lv_message);
	    txtEmptyListMessage = (TextView) view.findViewById(R.id.txt_emptylist_message);
        btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
        btnViewAll.setTypeface(Global.myTypeFace.getRalewayRegular());
	    txtEmptyListMessage.setTypeface(Global.myTypeFace.getRalewayRegular());

	    lvMessages.setEmptyView(txtEmptyListMessage);

	    if (Utility.isConnected(activityHost)) {
			callApiGetMessages();
		} else {
			Utility.alertOffline(activityHost);
		}

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

        popupMessage.showAtLocation(imgMessage, Gravity.END, 10, 60);
    }

    private void loadFragmentAllMessage(int position) {
        Bundle bundleAllMessage = new Bundle();
        bundleAllMessage.putParcelableArrayList(AllMessageFragment.ARG_ARR_LIST_MESSAGE, arrListMessage);
        bundleAllMessage.putInt(AllMessageFragment.ARG_MESSAGE_POSITION, position);
        activityHost.loadFragment(HostActivity.FRAGMENT_ALL_MESSAGE, bundleAllMessage);
    }

    private void callApiGetMessages() {
        try {
            activityHost.showProgress();
            Attribute attribute = new Attribute();
            attribute.setUserId(Global.strUserId);

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_MESSAGES);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetMessages Exception : " + e.toString());
        }
    }

    private void showStudymateRequests() {
        txtRequestNo.setVisibility(View.GONE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_studymates, null);

        lvStudymates = (ListView) view.findViewById(R.id.lv_studymates);
	    txtEmptyListMessage = (TextView) view.findViewById(R.id.txt_emptylist_message);
        btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
        btnViewAll.setTypeface(Global.myTypeFace.getRalewayRegular());
	    txtEmptyListMessage.setTypeface(Global.myTypeFace.getRalewayRegular());

	    lvStudymates.setEmptyView(txtEmptyListMessage);

	    if (Utility.isConnected(activityHost)) {
			callApiGetStudymateRequests();
		} else {
			Utility.alertOffline(activityHost);
		}

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

        popupFriendRequest.showAtLocation(imgFriendRequest, Gravity.END, 10, 60);
    }

    private void loadFragmentAllStudymateRequest(int position) {
        Bundle bundleAllStudymateRequest = new Bundle();
        bundleAllStudymateRequest.putParcelableArrayList(AllStudymateRequestFragment.ARG_ARR_LIST_STUDYMATE_REQUEST, arrListStudyMateRequest);
        activityHost.loadFragment(HostActivity.FRAGMENT_ALL_STUDYMATE_REQUEST, bundleAllStudymateRequest);
    }

    private void callApiGetStudymateRequests() {
        try {
            activityHost.showProgress();
            Attribute attribute = new Attribute();
            attribute.setUserId(Global.strUserId);

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_STUDYMATE_REQUEST);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetStudymateRequests Exception : " + e.toString());
        }
    }

    private void highlightNotificationIcon(int imgId) {
        for (int i = 0; i < arrImgNotificationIcon.length; i++) {
            arrImgNotificationIcon[i].setActivated(arrImgNotificationIcon[i].getId() == imgId);
        }
    }

    private void highlightLabel(int fragmentId, boolean attached) {
	    int textColor = attached ? getActivity().getResources().getColor(R.color.color_green) : Color.WHITE;
	    switch (fragmentId) {
		    case HostActivity.FRAGMENT_GENERAL_SETTINGS:
			    txtGeneralSettings.setTextColor(textColor);
			    txtGeneralSettings.setEnabled(!attached);
			    break;
		    case HostActivity.FRAGMENT_MY_FEEDS:
			    txtMyFeeds.setTextColor(textColor);
			    txtMyFeeds.setEnabled(!attached);
			    break;
		    case HostActivity.FRAGMENT_STUDYMATES:
			    txtStudyMates.setTextColor(textColor);
			    txtStudyMates.setEnabled(!attached);
			    break;
		    case HostActivity.FRAGMENT_MY_ACTIVITY:
			    txtMyActivity.setTextColor(textColor);
			    txtMyActivity.setEnabled(!attached);
			    break;
		    case HostActivity.FRAGMENT_MY_WALLET:
			    txtWallet.setTextColor(textColor);
			    txtWallet.setEnabled(!attached);
			    break;
		    case HostActivity.FRAGMENT_EDIT_PROFILE:
			    txtEditProfile.setText(attached ? Html.fromHtml("<u>" + activityHost.getString(R.string.edit_profile) + "</u>") : activityHost.getString(R.string.edit_profile));
			    txtEditProfile.setEnabled(!attached);
			    break;
	    }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            fragListener = (FragmentListener) activity;
            activityHost.setListenerHostProfileController(this);
            if (fragListener != null) {
                fragListener.onFragmentAttached(HostActivity.FRAGMENT_PROFILE_CONTROLLER);
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
                fragListener.onFragmentDetached(HostActivity.FRAGMENT_PROFILE_CONTROLLER);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
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
//					Log.e(TAG, "onResponseUpdateReadStatus success");
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
                    arrListStudyMateRequest = responseHandler.getStudymateRequest();
                    fillListStudymate();
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
			btnViewAll.setVisibility(arrListStudyMateRequest.size() > 0 ? View.VISIBLE : View.GONE);
			adpStudymate = new StudymateRequestAdapter(getActivity(), this, arrListStudyMateRequest, 4);
			lvStudymates.setAdapter(adpStudymate);
			ArrayList<String> recordIds = new ArrayList<>();
			for (int i = 0; i < (arrListStudyMateRequest.size() >= 4 ? 4 : arrListStudyMateRequest.size()); i++) {
				recordIds.add(arrListStudyMateRequest.get(i).getRecordId());
			}
			if (Utility.isConnected(activityHost)) {
				callApiUpdateReadStatus(WebConstants.STUDYMATE_REQUEST, recordIds);
			} else {
				Utility.alertOffline(activityHost);
			}
		}
	}

    private void callApiUpdateReadStatus(String readCategory, ArrayList<String> recordId) {
        try {
            Attribute attribute = new Attribute();
            attribute.setUserId(Global.strUserId);
            attribute.setReadCategory(readCategory);
            attribute.setRecordIds(recordId);

            new WebserviceWrapper(activityHost, attribute, this).new WebserviceCaller().
                    execute(WebConstants.UPDATE_READ_STATUS);
        } catch (Exception e) {
            Log.e(TAG, "callApiUpdateReadStatus Exception : " + e.toString());
        }
    }

    private void onResponseGetMessages(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrListMessage = responseHandler.getMessages();
                    fillListMessage();
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
			btnViewAll.setVisibility(arrListMessage.size() > 0 ? View.VISIBLE : View.GONE);
			adpMessage = new MessageAdapter(getActivity(), arrListMessage, 4);
			lvMessages.setAdapter(adpMessage);
			ArrayList<String> recordIds = new ArrayList<>();
			for (int i = 0; i < (arrListMessage.size() >= 4 ? 4 : arrListMessage.size()); i++) {
				recordIds.add(arrListMessage.get(i).getRecordId());
			}
			if (Utility.isConnected(activityHost)) {
				callApiUpdateReadStatus(WebConstants.MESSAGES, recordIds);
			} else {
				Utility.alertOffline(activityHost);
			}
		}
	}

    private void onResponseGetNotification(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrListNotification = responseHandler.getNotification();
                    fillListNotification();
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
			btnViewAll.setVisibility(arrListNotification.size() > 0 ? View.VISIBLE : View.GONE);
			adpNotification = new NotificationAdapter(getActivity(), arrListNotification, 4);
			lvNotifications.setAdapter(adpNotification);
			ArrayList<String> recordIds = new ArrayList<>();
			for (int i = 0; i < (arrListNotification.size() >= 4 ? 4 : arrListNotification.size()); i++) {
				recordIds.add(arrListNotification.get(i).getRecordId());
			}
			if (Utility.isConnected(activityHost)) {
				callApiUpdateReadStatus(WebConstants.NOTIFICATION, recordIds);
			} else {
				Utility.alertOffline(activityHost);
			}
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

}
