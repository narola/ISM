package com.ism.fragment;

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
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.activity.HostActivity;
import com.ism.R;
import com.ism.adapter.MessageAdapter;
import com.ism.adapter.NotificationAdapter;
import com.ism.adapter.StudymateAdapter;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.model.FragmentArgument;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.utility.PreferenceData;
import com.ism.views.CircleImageView;
import com.ism.ws.RequestObject;
import com.ism.ws.ResponseObject;
import com.ism.ws.WebserviceWrapper;
import com.ism.ws.model.Data;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on --/10/15.
 */
public class ProfileControllerFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, HostActivity.HostListenerProfileController {

    private static final String TAG = ProfileControllerFragment.class.getSimpleName();

    private View view;

	private CircleImageView imgDp;
    private TextView txtUserName, txtEditProfile, txtNotificationNo, txtMessageNo, txtRequestNo,
		        txtGeneralSettings, txtMyFeeds, txtStudyMates, txtMyActivity, txtWallet;
	private ImageView imgNotification, imgMessage, imgFriendRequest;
	private ListView lvNotifications, lvMessages, lvStudymates;
	private Button btnViewAll;

	private TextView[] arrTxtLabel;
	private ImageView[] arrImgNotificationIcon;

	private HostActivity activityHost;
    private FragmentListener fragListener;
	private ImageLoader imageLoader;
	private MyTypeFace myTypeFace;
	private ArrayList<Data> arrListNotification;
	private ArrayList<Data> arrListMessage;
	private ArrayList<Data> arrListStudyMateRequest;
	private NotificationAdapter adpNotification;
	private MessageAdapter adpMessage;
	private StudymateAdapter adpStudymate;

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

	    myTypeFace = new MyTypeFace(getActivity());

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
			    }
			    highlightLabel(v.getId());
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
					    showFriendRequests();
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

	    imgNotification.setOnClickListener(onClickNotificationIcon);
	    imgMessage.setOnClickListener(onClickNotificationIcon);
	    imgFriendRequest.setOnClickListener(onClickNotificationIcon);
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
		btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
		btnViewAll.setTypeface(myTypeFace.getRalewayRegular());

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

		popupNotification.showAtLocation(imgNotification, Gravity.END, 10, 60);
	}

	private void loadFragmentAllNotification(int position) {
		FragmentArgument fragmentArgument = new FragmentArgument(arrListNotification);
		fragmentArgument.setPosition(position);
		activityHost.loadFragment(HostActivity.FRAGMENT_ALL_NOTIFICATION, fragmentArgument);
	}

	private void callApiGetNotifications() {
		try {
			activityHost.showProgress();
			RequestObject requestObject = new RequestObject();
			requestObject.setUserId(Global.strUserId);

			new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller()
					.execute(WebConstants.GET_NOTIFICATION);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetNotifications Exception : " + e.toString());
		}
	}

	private void showMessages() {
		txtMessageNo.setVisibility(View.GONE);
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_message, null);

		lvMessages = (ListView) view.findViewById(R.id.lv_message);
		btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
		btnViewAll.setTypeface(myTypeFace.getRalewayRegular());

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

		popupMessage.showAtLocation(imgMessage, Gravity.END, 10, 60);
	}

	private void loadFragmentAllMessage(int position) {
		FragmentArgument fragmentArgument = new FragmentArgument(arrListMessage);
		fragmentArgument.setPosition(position);
		activityHost.loadFragment(HostActivity.FRAGMENT_ALL_MESSAGE, fragmentArgument);
	}

	private void callApiGetMessages() {
		try {
			activityHost.showProgress();
			RequestObject requestObject = new RequestObject();
//			requestObject.setUserId(Global.strUserId);
			requestObject.setUserId("109");

			new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller()
					.execute(WebConstants.GET_MESSAGES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetMessages Exception : " + e.toString());
		}
	}

	private void showFriendRequests() {
		txtRequestNo.setVisibility(View.GONE);
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_studymates, null);

		lvStudymates = (ListView) view.findViewById(R.id.lv_studymates);
		btnViewAll = (Button) view.findViewById(R.id.btn_view_all);
		btnViewAll.setTypeface(myTypeFace.getRalewayRegular());

		callApiGetStudymateRequests();

		final PopupWindow popupFriendRequest = new PopupWindow(view, 250, 350, true);
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

		lvStudymates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				popupFriendRequest.dismiss();
				loadFragmentAllStudymateRequest(position);
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
		FragmentArgument fragmentArgument = new FragmentArgument(arrListStudyMateRequest);
		fragmentArgument.setPosition(position);
		activityHost.loadFragment(HostActivity.FRAGMENT_ALL_STUDYMATE_REQUEST, fragmentArgument);
	}

	private void callApiGetStudymateRequests() {
		try {
			activityHost.showProgress();
			RequestObject requestObject = new RequestObject();
			requestObject.setUserId(Global.strUserId);

			new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller()
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

	private void highlightLabel(int txtId) {
		for (int i = 0; i < arrTxtLabel.length; i++) {
			if (arrTxtLabel[i].getId() == txtId) {
				arrTxtLabel[i].setTextColor(getActivity().getResources().getColor(R.color.color_green));
				arrTxtLabel[i].setEnabled(false);
			} else {
				arrTxtLabel[i].setTextColor(Color.WHITE);
				arrTxtLabel[i].setEnabled(true);
			}
		}
	}

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
	        activityHost = (HostActivity) activity;
            fragListener = (FragmentListener) activity;
	        activityHost.setListnerHostProfileController(this);
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
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetStudymateRequest(Object object, Exception error) {
		try {
			activityHost.hideProgress();
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					Log.e(TAG, "onResponseGetStudymateRequest success");
					arrListStudyMateRequest = responseObj.getData();
					fillListStudymate();
					btnViewAll.setVisibility(View.VISIBLE);
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
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
			adpStudymate = new StudymateAdapter(getActivity(), arrListStudyMateRequest, 4);
			lvStudymates.setAdapter(adpStudymate);
		}
	}

	private void onResponseGetMessages(Object object, Exception error) {
		try {
			activityHost.hideProgress();
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					Log.e(TAG, "onResponseGetMessages success");
					arrListMessage = responseObj.getData();
					fillListMessage();
					btnViewAll.setVisibility(View.VISIBLE);
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
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
		}
	}

	private void onResponseGetNotification(Object object, Exception error) {
		try {
			activityHost.hideProgress();
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					Log.e(TAG, "onResponseGetNotification success");
					arrListNotification = responseObj.getData();
					fillListNotification();
					btnViewAll.setVisibility(View.VISIBLE);
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
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
		}
	}

	@Override
	public void onBadgesFetched() {
		showBadges();
	}
}
