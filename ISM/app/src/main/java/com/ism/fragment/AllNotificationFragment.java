package com.ism.fragment;

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

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.NotificationAdapter;
import com.ism.interfaces.FragmentListener;
import com.ism.views.CircleImageView;
import com.ism.ws.model.Data;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 09/11/15.
 */
public class AllNotificationFragment extends Fragment implements HostActivity.HostListenerAllNotification {

	private static final String TAG = AllNotificationFragment.class.getSimpleName();

	private View view;
	private ListView lvAllNotification;
	private RelativeLayout rlNotificationDetails;
	private CircleImageView imgDp;
	private ImageView imgLike, imgTagStudyMates;
	private TextView txtName, txtPost, txtLikes, txtComments, txtViewAll;
	private EditText etComment;
	private Button btnComment;
	private LinearLayout llComments;

	private FragmentListener fragListener;
	private HostActivity activityHost;
	private ArrayList<Data> arrListNotification;
	private NotificationAdapter adpNotification;
	private ImageLoader imageLoader;

	private static String NOTIFICATION_POSITION = "notificationPosition";
	private int positionNotification;

	public static AllNotificationFragment newInstance(ArrayList<Data> arrListNotification, int position) {
		AllNotificationFragment fragment = new AllNotificationFragment();
		Bundle args = new Bundle();
		args.putInt(NOTIFICATION_POSITION, position);
		fragment.setArguments(args);
		fragment.setArrListNotification(arrListNotification);
		return fragment;
	}

	public void setArrListNotification(ArrayList<Data> arrListNotification) {
		this.arrListNotification = arrListNotification;
	}

	public AllNotificationFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		positionNotification = getArguments().getInt(NOTIFICATION_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_all_notification, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		lvAllNotification = (ListView) view.findViewById(R.id.lv_all_notification);

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

		if (positionNotification >= 0) {
			showNotificationDetails(positionNotification);
		}

		if (arrListNotification != null) {
			adpNotification = new NotificationAdapter(getActivity(), arrListNotification);
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
	}

	private void showNotificationDetails(int position) {
		activityHost.showControllerTopBackButton();
		lvAllNotification.setVisibility(View.GONE);
		if (rlNotificationDetails == null) {
			rlNotificationDetails = (RelativeLayout)((ViewStub) view.findViewById(R.id.vs_notification_details)).inflate();
			initViews();
		} else {
			rlNotificationDetails.setVisibility(View.VISIBLE);
		}

		imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", imgDp, ISMStudent.options);
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
			activityHost = (HostActivity) activity;
			activityHost.setListenerHostAllNotification(this);
			if (fragListener != null) {
				fragListener.onFragmentAttached(HostActivity.FRAGMENT_ALL_NOTIFICATION);
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
				fragListener.onFragmentDetached(HostActivity.FRAGMENT_ALL_NOTIFICATION);
			}
		} catch (ClassCastException e) {
			Log.e(TAG, "onDetach Exception : " + e.toString());
		}
		fragListener = null;
	}

	@Override
	public void onControllerTopBackClick() {
		showNotificationList();
	}
}