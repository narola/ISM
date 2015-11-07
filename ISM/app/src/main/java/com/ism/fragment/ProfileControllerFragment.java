package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.activity.HostActivity;
import com.ism.R;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.views.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by c161 on --/10/15.
 */
public class ProfileControllerFragment extends Fragment {

    private static final String TAG = ProfileControllerFragment.class.getSimpleName();

    private View view;

	private CircleImageView imgDp;
    private TextView txtUserName, txtEditProfile, txtNotificationNo, txtMessageNo, txtFriendRequestNo,
		        txtGeneralSettings, txtMyFeeds, txtStudyMates, txtMyActivity, txtWallet;
	private ImageView imgNotification, imgMessage, imgFriendRequest;

	private TextView[] arrTxtLabel;
	private ImageView[] arrImgNotificationIcon;

	private HostActivity activityHost;
    private FragmentListener fragListener;
	private ImageLoader imageLoader;

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
	    txtFriendRequestNo = (TextView) view.findViewById(R.id.txt_friend_request);
	    txtGeneralSettings = (TextView) view.findViewById(R.id.txt_general_settings);
	    txtMyFeeds = (TextView) view.findViewById(R.id.txt_my_feeds);
	    txtStudyMates = (TextView) view.findViewById(R.id.txt_studymates);
	    txtMyActivity = (TextView) view.findViewById(R.id.txt_my_activity);
	    txtWallet = (TextView) view.findViewById(R.id.txt_my_wallet);
	    imgNotification = (ImageView) view.findViewById(R.id.img_notification);
	    imgMessage = (ImageView) view.findViewById(R.id.img_message);
	    imgFriendRequest = (ImageView) view.findViewById(R.id.img_friend_request);

	    arrTxtLabel = new TextView[]{txtGeneralSettings, txtMyFeeds, txtStudyMates, txtMyActivity, txtWallet};
	    arrImgNotificationIcon = new ImageView[]{imgNotification, imgMessage, imgFriendRequest};

	    imageLoader = ImageLoader.getInstance();
	    imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
	    imageLoader.displayImage(Global.strProfilePic, imgDp, ISMStudent.options);
	    txtNotificationNo.setText("10");
	    txtMessageNo.setText("26");
	    txtFriendRequestNo.setText("5");

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
					    break;
				    case R.id.img_message:
					    break;
				    case R.id.img_friend_request:
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

}
