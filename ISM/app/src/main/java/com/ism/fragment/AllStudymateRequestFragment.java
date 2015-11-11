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
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.StudymateAdapter;
import com.ism.interfaces.FragmentListener;
import com.ism.ws.model.Data;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 09/11/15.
 */
public class AllStudymateRequestFragment extends Fragment implements HostActivity.HostListenerAllStudyMateRequest {

	private static final String TAG = AllStudymateRequestFragment.class.getSimpleName();

	private View view;
	private ListView lvAllStudyMate;
	private RelativeLayout rlStudyMateRequestDetails;

	private FragmentListener fragListener;
	private HostActivity activityHost;
	private ArrayList<Data> arrListStudymateRequest;
	private StudymateAdapter adpStudymate;
	private ImageLoader imageLoader;

	private static String STUDY_MATE_REQUEST_POSITION = "notificationPosition";
	private int positionStudyMateRequest;

	public static AllStudymateRequestFragment newInstance(ArrayList<Data> arrListStudymateRequest, int position) {
		AllStudymateRequestFragment fragment = new AllStudymateRequestFragment();
		Bundle args = new Bundle();
		args.putInt(STUDY_MATE_REQUEST_POSITION, position);
		fragment.setArguments(args);
		fragment.setArrListStudymateRequest(arrListStudymateRequest);
		return fragment;
	}

	public void setArrListStudymateRequest(ArrayList<Data> arrListStudymateRequest) {
		this.arrListStudymateRequest = arrListStudymateRequest;
	}

	public AllStudymateRequestFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		positionStudyMateRequest = getArguments().getInt(STUDY_MATE_REQUEST_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_all_studymate_request, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		lvAllStudyMate = (ListView) view.findViewById(R.id.lv_all_studymate);

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

		if (positionStudyMateRequest >= 0) {
			showStudyMateRequestDetails(positionStudyMateRequest);
		}

		if (arrListStudymateRequest != null) {
			adpStudymate = new StudymateAdapter(getActivity(), arrListStudymateRequest);
			lvAllStudyMate.setAdapter(adpStudymate);
		}

		lvAllStudyMate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				showStudyMateRequestDetails(position);
			}
		});
	}
	private void showStudyMateRequestList() {
		if (rlStudyMateRequestDetails != null) {
			rlStudyMateRequestDetails.setVisibility(View.GONE);
		}
		lvAllStudyMate.setVisibility(View.VISIBLE);
	}

	private void showStudyMateRequestDetails(int position) {
		activityHost.showControllerTopBackButton();
		lvAllStudyMate.setVisibility(View.GONE);
		if (rlStudyMateRequestDetails == null) {
			rlStudyMateRequestDetails = (RelativeLayout)((ViewStub) view.findViewById(R.id.vs_studymate_request_details)).inflate();
			initViews();
		} else {
			rlStudyMateRequestDetails.setVisibility(View.VISIBLE);
		}

//		imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", imgDp, ISMStudent.options);
//		txtName.setText(arrListNotification.get(position).getNotificationFromName());
//		txtPost.setText(arrListNotification.get(position).getNotificationText());
	}

	private void initViews() {

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			fragListener = (FragmentListener) activity;
			activityHost = (HostActivity) activity;
			activityHost.setListenerHostAllStudyMateRequest(this);
			if (fragListener != null) {
				fragListener.onFragmentAttached(HostActivity.FRAGMENT_ALL_STUDYMATE_REQUEST);
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
				fragListener.onFragmentDetached(HostActivity.FRAGMENT_ALL_STUDYMATE_REQUEST);
			}
		} catch (ClassCastException e) {
			Log.e(TAG, "onDetach Exception : " + e.toString());
		}
		fragListener = null;
	}

	@Override
	public void onControllerTopBackClick() {
		showStudyMateRequestList();
	}
}
